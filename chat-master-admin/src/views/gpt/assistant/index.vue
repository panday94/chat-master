<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="角色名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入角色名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主模型" prop="mainModel">
        <el-select v-model="queryParams.mainModel" placeholder="是否为主模型">
          <el-option
            v-for="item in dict.type.sys_yes_no"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
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
          v-hasPermi="['gpt:assistant:save']"
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
          v-hasPermi="['gpt:assistant:update']"
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
          v-hasPermi="['gpt:assistant:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assistantList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="分类名称" align="center" prop="typeName" />
      <el-table-column label="角色名称" align="center">
        <template slot-scope="scope">{{ parseEmoji(scope.row.icon) + ' ' + scope.row.title }}</template>
      </el-table-column>
      <el-table-column label="角色描述" align="center" prop="description">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.description" placement="top">
            <div class="table-cell">{{ scope.row.description }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="系统提示词" align="center" prop="systemPrompt">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.systemPrompt" placement="top">
            <div class="table-cell">{{ scope.row.systemPrompt }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dictTag :options="dict.type.sys_normal_disable" :value="scope.row.status"></dictTag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gpt:assistant}:update']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gpt:assistant:remove']"
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

    <!-- 添加或修改AI助理功能对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="助手分类" prop="typeId">
          <el-select v-model="form.typeId">
            <el-option
              v-for="item in typeList"
              :key="item.value"
              :label="item.label"
              :value="Number(item.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像">
          <imageUpload v-model="form.avatar" :limit="1" />
        </el-form-item>
        <el-form-item label="角色图标" prop="icon">
          <el-select v-model="form.icon" filterable placeholder="请选择角色图标">
            <el-option
              v-for="item in emojis"
              :key="item.name"
              :label="item.name + item.emoji"
              :value="':' + item.name + ':'"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 2 }"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="系统提示词" prop="systemPrompt">
          <el-input
            v-model="form.systemPrompt"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 3 }"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="AI打招呼" prop="firstMessage">
          <el-input
            v-model="form.firstMessage"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 3 }"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="是否主模型" prop="mainModel">
          <el-radio-group v-model="form.mainModel">
            <el-radio v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.value">
              {{ dict.label
              }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" placeholder="请输入排序" />
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
  pageAssistant,
  listAssistant,
  getAssistant,
  addAssistant,
  updateAssistant,
  delAssistant
} from "@/api/gpt/assistant";
import { selectAssyistantType } from "@/api/sys/common";
import * as emoji from "node-emoji";
export default {
  name: "Assistant",
  dicts: ["sys_yes_no", "sys_normal_disable"],
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
      // AI助理功能表格数据
      assistantList: [],
      typeList: [],
      emojis: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        title: null,
        status: null,
        mainModel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        typeId: [
          { required: true, message: "助手分类不能为空", trigger: "blur" }
        ],
        title: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        icon: [
          { required: true, message: "角色图标不能为空", trigger: "blur" }
        ],
        mainModel: [
          { required: true, message: "是否为主模型不能为空", trigger: "blur" }
        ],
        sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        status: [{ required: true, message: "状态不能为空", trigger: "blur" }],
        description: [
          { required: true, message: "角色描述不能为空", trigger: "blur" }
        ],
        firstMessage: [
          { required: true, message: "AI打招呼不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    selectAssyistantType().then(res => {
      this.typeList = res.data;
    });
    this.emojis = emoji.search("");
  },
  methods: {
    /** 查询AI助理功能列表 */
    getList() {
      this.loading = true;
      pageAssistant(this.queryParams).then(res => {
        this.assistantList = res.data.records;
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
        typeId: null,
        avatar: null,
        icon: null,
        title: null,
        tag: null,
        status: 0,
        mainModel: null,
        sort: 0,
        description: null,
        systemPrompt: null,
        firstMessage: null
      };
      this.resetForm("form");
    },
    parseEmoji(icon) {
      return emoji.emojify(icon);
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
      this.title = "添加AI助理功能";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getAssistant(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "修改AI助理功能";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAssistant(this.form).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAssistant(this.form).then(res => {
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
        .confirm('是否确认删除AI助理功能编号为"' + ids + '"的数据项？')
        .then(function() {
          return delAssistant(ids);
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
        "/gpt/assistant/export",
        {
          ...this.queryParams
        },
        `assistant_${new Date().getTime()}.xlsx`
      );
    }
  }
};
</script>
<style scoped>
.table-cell {
  height: 40px;
  line-height: 40px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>