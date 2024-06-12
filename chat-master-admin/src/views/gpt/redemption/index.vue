<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="兑换码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入兑换码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="兑换人" prop="memberId">
        <el-input v-model="queryParams.memberId" placeholder="请输入兑换人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="memberId">
        <el-input v-model="queryParams.memberId" placeholder="请选择兑换状态" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="兑换时间" prop="recieveTime">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['gpt:redemption:save']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['gpt:redemption:update']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['gpt:redemption:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['gpt:redemption:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="redemptionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="兑换码" align="center" prop="code" />
      <el-table-column label="可兑次数" align="center" prop="num" />
      <el-table-column label="兑换人" align="center" prop="memberName" />
      <el-table-column label="兑换时间" align="center" prop="recieveTime" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['gpt:redemption}:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['gpt:redemption:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />

    <!-- 添加或修改兑换码对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="兑换码" prop="code">
          <el-input v-model="form.code" placeholder="请输入兑换码" />
        </el-form-item>
        <el-form-item label="可兑次数" prop="num">
          <el-input v-model="form.num" placeholder="请输入可兑次数" />
        </el-form-item>
        <el-form-item label="兑换人" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入兑换人" />
        </el-form-item>
        <el-form-item label="兑换时间" prop="recieveTime">
          <el-input v-model="form.recieveTime" placeholder="请输入兑换时间" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  pageRedemption,
  listRedemption,
  getRedemption,
  addRedemption,
  updateRedemption,
  delRedemption,
} from "@/api/gpt/redemption";

export default {
  name: "Redemption",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 时间筛选
      dateRange: [],
      // 总条数
      total: 0,
      // 兑换码表格数据
      redemptionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        code: null,
        num: null,
        memberId: null,
        recieveTime: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createUser: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateUser: [
          { required: true, message: "更新人不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询兑换码列表 */
    getList() {
      this.loading = true;
      pageRedemption(this.addDateRange(this.queryParams, this.dateRange)).then(res => {
        this.redemptionList = res.data.records;
        this.total = res.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createUser: null,
        createTime: null,
        updateUser: null,
        updateTime: null,
        code: null,
        num: null,
        memberId: null,
        recieveTime: null,
        status: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加兑换码";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRedemption(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "修改兑换码";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRedemption(this.form).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRedemption(this.form).then(res => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除兑换码编号为"' + ids + '"的数据项？').then(function () {
        return delRedemption(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/gpt/redemption/export', {
        ...this.queryParams
      }, `redemption_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
