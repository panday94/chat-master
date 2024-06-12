<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="130px"
    >
      <el-form-item label="语言模型" prop="model">
        <el-select v-model="queryParams.model" clearable  placeholder="请选择语言模型">
          <el-option
            v-for="item in dict.type.gpt_model_type"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="应用Key(token)" prop="appKey">
        <el-input
          v-model="queryParams.appKey"
          placeholder="请输入appKey"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['gpt:openkey:save']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gpt:openkey:update']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gpt:openkey:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="openkeyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" width="100"/>
      <el-table-column label="模型" align="center" prop="model" width="150">
        <template slot-scope="scope">
          <dictTag :options="dict.type.gpt_model_type" :value="scope.row.model"></dictTag>
        </template>
      </el-table-column>
      <el-table-column label="应用Id" align="center" prop="appId" width="150"/>
      <el-table-column label="应用Key(token)" align="center" prop="appKey" width="220" />
      <el-table-column label="总额度" align="center" prop="totalTokens" />
      <el-table-column label="剩余额度" align="center" prop="surplusTokens" />
      <el-table-column label="是否可用" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gpt:openkey}:update']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gpt:openkey:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.current"
      :limit.sync="queryParams.size"
      @pagination="getList"
    />

    <!-- 添加或修改openai token对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="模型" prop="model">
          <el-select v-model="form.model" placeholder="请选择AI模型">
            <el-option
              v-for="item in dict.type.gpt_model_type"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.model == 'WENXIN' || form.model == 'SPARK' " label="应用id" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入appId" />
        </el-form-item>
        <el-form-item label="应用key(token)" prop="appKey">
          <el-input v-model="form.appKey" placeholder="请输入appkey" />
        </el-form-item>
        <el-form-item v-if="form.model == 'WENXIN' || form.model == 'SPARK' " label="应用Secret" prop="appSecret">
          <el-input v-model="form.appSecret" placeholder="请输入appSecret" />
        </el-form-item>
        <el-form-item label="总额度" prop="totalTokens">
          <el-input v-model="form.totalTokens" placeholder="请输入总额度" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0"></el-switch>
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
  pageOpenkey,
  getOpenkey,
  addOpenkey,
  updateOpenkey,
  delOpenkey
} from "@/api/gpt/openkey";

export default {
  name: "Openkey",
  dicts: ["sys_normal_disable", "gpt_model_type"],
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
      // 总条数
      total: 0,
      // openai token表格数据
      openkeyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        appKey: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        model: [{ required: true, message: "模型不能为空", trigger: "blur" }],
        appId: [{ required: true, message: "应用Id不能为空", trigger: "blur" }],
        appKey: [{ required: true, message: "应用Key不能为空", trigger: "blur" }],
        appSecret: [{ required: true, message: "应用Secret不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询openai token列表 */
    getList() {
      this.loading = true;
      pageOpenkey(this.queryParams).then(res => {
        this.openkeyList = res.data.records;
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
        model: null,
        appId: null,
        appKey: null,
        appSecret: null,
        totalTokens: null,
        remark: null,
        status: 0,
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加密钥";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getOpenkey(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "修改密钥";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOpenkey(this.form).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOpenkey(this.form).then(res => {
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
      this.$modal
        .confirm('是否确认删除openai token编号为"' + ids + '"的数据项？')
        .then(function() {
          return delOpenkey(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "/gpt/openkey/export",
        {
          ...this.queryParams
        },
        `openkey_${new Date().getTime()}.xlsx`
      );
    }
  }
};
</script>
