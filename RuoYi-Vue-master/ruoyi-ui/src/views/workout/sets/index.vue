<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="${comment}" prop="detailId">
        <el-input
          v-model="queryParams.detailId"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第几组" prop="setNumber">
        <el-input
          v-model="queryParams.setNumber"
          placeholder="请输入第几组"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用重量(kg)" prop="weightKg">
        <el-input
          v-model="queryParams.weightKg"
          placeholder="请输入使用重量(kg)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际完成次数" prop="completedReps">
        <el-input
          v-model="queryParams.completedReps"
          placeholder="请输入实际完成次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自觉强度系数(1-10)" prop="rpeLevel">
        <el-input
          v-model="queryParams.rpeLevel"
          placeholder="请输入自觉强度系数(1-10)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="本组耗时(秒)" prop="setDurationSeconds">
        <el-input
          v-model="queryParams.setDurationSeconds"
          placeholder="请输入本组耗时(秒)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="本组备注" prop="notes">
        <el-input
          v-model="queryParams.notes"
          placeholder="请输入本组备注"
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
      <el-form-item label="本组训练预估消耗的卡路里" prop="caloriesBurned">
        <el-input
          v-model="queryParams.caloriesBurned"
          placeholder="请输入本组训练预估消耗的卡路里"
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
          v-hasPermi="['workout:sets:add']"
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
          v-hasPermi="['workout:sets:edit']"
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
          v-hasPermi="['workout:sets:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workout:sets:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="setsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="setId" />
      <el-table-column label="${comment}" align="center" prop="detailId" />
      <el-table-column label="第几组" align="center" prop="setNumber" />
      <el-table-column label="使用重量(kg)" align="center" prop="weightKg" />
      <el-table-column label="实际完成次数" align="center" prop="completedReps" />
      <el-table-column label="自觉强度系数(1-10)" align="center" prop="rpeLevel" />
      <el-table-column label="本组耗时(秒)" align="center" prop="setDurationSeconds" />
      <el-table-column label="本组备注" align="center" prop="notes" />
      <el-table-column label="${comment}" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="本组训练预估消耗的卡路里" align="center" prop="caloriesBurned" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workout:sets:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workout:sets:remove']"
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

    <!-- 添加或修改项目组数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="${comment}" prop="detailId">
          <el-input v-model="form.detailId" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="第几组" prop="setNumber">
          <el-input v-model="form.setNumber" placeholder="请输入第几组" />
        </el-form-item>
        <el-form-item label="使用重量(kg)" prop="weightKg">
          <el-input v-model="form.weightKg" placeholder="请输入使用重量(kg)" />
        </el-form-item>
        <el-form-item label="实际完成次数" prop="completedReps">
          <el-input v-model="form.completedReps" placeholder="请输入实际完成次数" />
        </el-form-item>
        <el-form-item label="自觉强度系数(1-10)" prop="rpeLevel">
          <el-input v-model="form.rpeLevel" placeholder="请输入自觉强度系数(1-10)" />
        </el-form-item>
        <el-form-item label="本组耗时(秒)" prop="setDurationSeconds">
          <el-input v-model="form.setDurationSeconds" placeholder="请输入本组耗时(秒)" />
        </el-form-item>
        <el-form-item label="本组备注" prop="notes">
          <el-input v-model="form.notes" placeholder="请输入本组备注" />
        </el-form-item>
        <el-form-item label="${comment}" prop="createdAt">
          <el-date-picker clearable
            v-model="form.createdAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择${comment}">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="本组训练预估消耗的卡路里" prop="caloriesBurned">
          <el-input v-model="form.caloriesBurned" placeholder="请输入本组训练预估消耗的卡路里" />
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
import { listSets, getSets, delSets, addSets, updateSets } from "@/api/workout/sets"

export default {
  name: "Sets",
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
      // 项目组数表格数据
      setsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detailId: null,
        setNumber: null,
        weightKg: null,
        completedReps: null,
        rpeLevel: null,
        setDurationSeconds: null,
        notes: null,
        createdAt: null,
        caloriesBurned: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detailId: [
          { required: true, message: "$comment不能为空", trigger: "blur" }
        ],
        setNumber: [
          { required: true, message: "第几组不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询项目组数列表 */
    getList() {
      this.loading = true
      listSets(this.queryParams).then(response => {
        this.setsList = response.rows
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
        setId: null,
        detailId: null,
        setNumber: null,
        weightKg: null,
        completedReps: null,
        rpeLevel: null,
        setDurationSeconds: null,
        notes: null,
        createdAt: null,
        caloriesBurned: null
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
      this.ids = selection.map(item => item.setId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加项目组数"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const setId = row.setId || this.ids
      getSets(setId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改项目组数"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.setId != null) {
            updateSets(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSets(this.form).then(response => {
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
      const setIds = row.setId || this.ids
      this.$modal.confirm('是否确认删除项目组数编号为"' + setIds + '"的数据项？').then(function() {
        return delSets(setIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('workout/sets/export', {
        ...this.queryParams
      }, `sets_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
