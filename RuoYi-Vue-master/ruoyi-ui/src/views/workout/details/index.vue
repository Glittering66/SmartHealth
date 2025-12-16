<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="${comment}" prop="logId">
        <el-input
          v-model="queryParams.logId"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="训练项目名称" prop="exerciseName">
        <el-input
          v-model="queryParams.exerciseName"
          placeholder="请输入训练项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标肌群" prop="muscleGroup">
        <el-input
          v-model="queryParams.muscleGroup"
          placeholder="请输入目标肌群"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用器械" prop="equipmentUsed">
        <el-input
          v-model="queryParams.equipmentUsed"
          placeholder="请输入使用器械"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="难度等级" prop="difficultyLevel">
        <el-input
          v-model="queryParams.difficultyLevel"
          placeholder="请输入难度等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标组数" prop="targetSets">
        <el-input
          v-model="queryParams.targetSets"
          placeholder="请输入目标组数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标次数" prop="targetReps">
        <el-input
          v-model="queryParams.targetReps"
          placeholder="请输入目标次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="组间休息时间(秒)" prop="restSeconds">
        <el-input
          v-model="queryParams.restSeconds"
          placeholder="请输入组间休息时间(秒)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="${comment}" prop="createdAt">
        <el-date-picker clearable
          v-model="queryParams.createdAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
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
          v-hasPermi="['workout:details:add']"
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
          v-hasPermi="['workout:details:edit']"
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
          v-hasPermi="['workout:details:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workout:details:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="detailId" />
      <el-table-column label="${comment}" align="center" prop="logId" />
      <el-table-column label="训练项目名称" align="center" prop="exerciseName" />
      <el-table-column label="目标肌群" align="center" prop="muscleGroup" />
      <el-table-column label="使用器械" align="center" prop="equipmentUsed" />
      <el-table-column label="难度等级" align="center" prop="difficultyLevel" />
      <el-table-column label="目标组数" align="center" prop="targetSets" />
      <el-table-column label="目标次数" align="center" prop="targetReps" />
      <el-table-column label="组间休息时间(秒)" align="center" prop="restSeconds" />
      <el-table-column label="${comment}" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workout:details:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workout:details:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改训练项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="${comment}" prop="logId">
          <el-input v-model="form.logId" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="训练项目名称" prop="exerciseName">
          <el-input v-model="form.exerciseName" placeholder="请输入训练项目名称" />
        </el-form-item>
        <el-form-item label="目标肌群" prop="muscleGroup">
          <el-input v-model="form.muscleGroup" placeholder="请输入目标肌群" />
        </el-form-item>
        <el-form-item label="使用器械" prop="equipmentUsed">
          <el-input v-model="form.equipmentUsed" placeholder="请输入使用器械" />
        </el-form-item>
        <el-form-item label="难度等级" prop="difficultyLevel">
          <el-input v-model="form.difficultyLevel" placeholder="请输入难度等级" />
        </el-form-item>
        <el-form-item label="目标组数" prop="targetSets">
          <el-input v-model="form.targetSets" placeholder="请输入目标组数" />
        </el-form-item>
        <el-form-item label="目标次数" prop="targetReps">
          <el-input v-model="form.targetReps" placeholder="请输入目标次数" />
        </el-form-item>
        <el-form-item label="组间休息时间(秒)" prop="restSeconds">
          <el-input v-model="form.restSeconds" placeholder="请输入组间休息时间(秒)" />
        </el-form-item>
        <el-form-item label="${comment}" prop="createdAt">
          <el-date-picker clearable
            v-model="form.createdAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择${comment}">
          </el-date-picker>
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
import { listDetails, getDetails, delDetails, addDetails, updateDetails } from "@/api/workout/details"

export default {
  name: "Details",
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
      // 训练项目表格数据
      detailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        logId: null,
        exerciseName: null,
        muscleGroup: null,
        equipmentUsed: null,
        difficultyLevel: null,
        targetSets: null,
        targetReps: null,
        restSeconds: null,
        createdAt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        logId: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        exerciseName: [
          { required: true, message: "训练项目名称不能为空", trigger: "blur" }
        ],
        targetSets: [
          { required: true, message: "目标组数不能为空", trigger: "blur" }
        ],
        targetReps: [
          { required: true, message: "目标次数不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询训练项目列表 */
    getList() {
      this.loading = true
      listDetails(this.queryParams).then(response => {
        this.detailsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        detailId: null,
        logId: null,
        exerciseName: null,
        muscleGroup: null,
        equipmentUsed: null,
        difficultyLevel: null,
        targetSets: null,
        targetReps: null,
        restSeconds: null,
        createdAt: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.detailId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加训练项目"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const detailId = row.detailId || this.ids
      getDetails(detailId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改训练项目"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.detailId != null) {
            updateDetails(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDetails(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const detailIds = row.detailId || this.ids
      this.$modal.confirm('是否确认删除训练项目编号为"' + detailIds + '"的数据项？').then(function() {
        return delDetails(detailIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('workout/details/export', {
        ...this.queryParams
      }, `details_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
