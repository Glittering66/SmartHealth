<template>
  <div class="app-container">
    <!-- 顶部操作栏 -->
    <div class="header-bar">
      <h2>我的训练记录</h2>
      <el-button type="primary" icon="el-icon-plus" @click="openAddDialog">
        新增训练记录
      </el-button>
    </div>

    <!-- 筛选区域 -->
    <el-card class="filter-card">
      <el-form inline :model="filterForm">
        <el-form-item label="训练日期">
          <el-date-picker
            v-model="filterForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>
        <el-form-item label="训练名称">
          <el-input v-model="filterForm.logName" placeholder="请输入训练名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="filterLogs">查询</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 训练记录列表 -->
    <el-card class="log-list-card">
      <el-table :data="filteredLogs" border style="width: 100%">
        <el-table-column prop="logDate" label="训练日期" width="120" />
        <el-table-column prop="logName" label="训练名称" />
        <el-table-column prop="totalDuration" label="总时长(分钟)" width="120" />
        <el-table-column prop="totalCalories" label="消耗热量(kcal)" width="140" />
        <el-table-column prop="notes" label="备注" min-width="180" />
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="goToDetails(scope.row.logId)">
              查看动作
            </el-button>
            <el-button size="mini" type="success" @click="openEditDialog(scope.row)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="deleteLog(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增训练记录弹窗 -->
    <el-dialog
      title="新增训练记录"
      :visible.sync="addDialogVisible"
      width="600px"
      @close="resetAddForm"
    >
      <el-form
        :model="addForm"
        :rules="addFormRules"
        ref="addFormRef"
        label-width="120px"
      >
        <el-form-item label="训练日期" prop="logDate">
          <el-date-picker
            v-model="addForm.logDate"
            type="date"
            placeholder="请选择训练日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="训练名称" prop="logName">
          <el-input v-model="addForm.logName" placeholder="请输入训练名称" />
        </el-form-item>
        <el-form-item label="总时长(分钟)" prop="totalDuration">
          <el-input-number v-model="addForm.totalDuration" :min="1" placeholder="请输入总时长" />
        </el-form-item>
        <el-form-item label="消耗热量(kcal)" prop="totalCalories">
          <el-input-number v-model="addForm.totalCalories" :min="1" placeholder="请输入消耗热量" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="addForm.notes" type="textarea" placeholder="请输入备注（选填）" />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAddForm">提交</el-button>
      </template>
    </el-dialog>

    <!-- 编辑训练记录弹窗 -->
    <el-dialog
      title="编辑训练记录"
      :visible.sync="editDialogVisible"
      width="600px"
      @close="resetEditForm"
    >
      <el-form
        :model="editForm"
        :rules="editFormRules"
        ref="editFormRef"
        label-width="120px"
      >
        <el-input v-model="editForm.logId" type="hidden" />
        <el-form-item label="训练日期" prop="logDate">
          <el-date-picker
            v-model="editForm.logDate"
            type="date"
            placeholder="请选择训练日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="训练名称" prop="logName">
          <el-input v-model="editForm.logName" placeholder="请输入训练名称" />
        </el-form-item>
        <el-form-item label="总时长(分钟)" prop="totalDuration">
          <el-input-number v-model="editForm.totalDuration" :min="1" placeholder="请输入总时长" />
        </el-form-item>
        <el-form-item label="消耗热量(kcal)" prop="totalCalories">
          <el-input-number v-model="editForm.totalCalories" :min="1" placeholder="请输入消耗热量" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="editForm.notes" type="textarea" placeholder="请输入备注（选填）" />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEditForm">提交修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      filterForm: {
        dateRange: [],
        logName: ""
      },
      allLogs: [], // 所有训练记录（localStorage）
      filteredLogs: [], // 筛选后的记录
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: '',
        notes: ''
      },
      editForm: {
        logId: '',
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: '',
        notes: ''
      },
      addFormRules: {
        logDate: [{ required: true, message: '请选择训练日期', trigger: 'blur' }],
        logName: [{ required: true, message: '请输入训练名称', trigger: 'blur' }],
        totalDuration: [{ required: true, message: '请输入总时长', trigger: 'blur' }],
        totalCalories: [{ required: true, message: '请输入消耗热量', trigger: 'blur' }]
      },
      editFormRules: {
        logDate: [{ required: true, message: '请选择训练日期', trigger: 'blur' }],
        logName: [{ required: true, message: '请输入训练名称', trigger: 'blur' }],
        totalDuration: [{ required: true, message: '请输入总时长', trigger: 'blur' }],
        totalCalories: [{ required: true, message: '请输入消耗热量', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.loadLogs();
  },
  methods: {
    // 加载本地训练记录
    loadLogs() {
      const storedLogs = localStorage.getItem("workout_logs");
      this.allLogs = storedLogs ? JSON.parse(storedLogs) : [];
      this.filteredLogs = [...this.allLogs];
    },

    // 保存训练记录到本地
    saveLogs() {
      localStorage.setItem("workout_logs", JSON.stringify(this.allLogs));
    },

    // 筛选训练记录
    filterLogs() {
      let result = [...this.allLogs];
      if (this.filterForm.dateRange.length) {
        const start = new Date(this.filterForm.dateRange[0]);
        const end = new Date(this.filterForm.dateRange[1]);
        result = result.filter(item => {
          const logDate = new Date(item.logDate);
          return logDate >= start && logDate <= end;
        });
      }
      if (this.filterForm.logName) {
        result = result.filter(item =>
          item.logName.includes(this.filterForm.logName)
        );
      }
      this.filteredLogs = result;
    },

    // 重置筛选
    resetFilter() {
      this.filterForm = { dateRange: [], logName: "" };
      this.filteredLogs = [...this.allLogs];
    },

    // 跳转到新增训练记录页（保留但未使用）
    goToAddLog() {
      this.$router.push("/workout/logs/add");
    },

    // 跳转到训练动作详情页
    goToDetails(logId) {
      this.$router.push({
        path: "/workout/details",
        query: { logId }
      });
    },

    // 编辑训练记录（保留但未使用）
    editLog(row) {
      this.$router.push({
        path: "/workout/logs/edit",
        query: { logId: row.logId }
      });
    },

    // 删除训练记录+关联动作
    deleteLog(row) {
      this.$confirm("确定删除该记录及关联动作吗？", "提示", { type: "warning" })
        .then(() => {
          this.allLogs = this.allLogs.filter(item => item.logId !== row.logId);
          this.saveLogs();
          this.filteredLogs = [...this.allLogs];

          const storedExercises = localStorage.getItem("workout_exercise_details");
          const exercises = storedExercises ? JSON.parse(storedExercises) : [];
          const newExercises = exercises.filter(item => item.logId !== row.logId);
          localStorage.setItem("workout_exercise_details", JSON.stringify(newExercises));

          this.$message.success("删除成功");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    // 新增：获取当前最大ID（核心：自增逻辑）
    getMaxLogId() {
      if (this.allLogs.length === 0) return 0; // 无记录时，最大ID为0
      // 从现有记录中提取logId，转换为数字后取最大值
      const ids = this.allLogs.map(item => Number(item.logId));
      return Math.max(...ids);
    },

    // 新增弹窗：打开弹窗
    openAddDialog() {
      this.addDialogVisible = true;
      this.resetAddForm();
    },

    // 新增弹窗：重置表单
    resetAddForm() {
      this.$refs.addFormRef && this.$refs.addFormRef.resetFields();
      this.addForm = { logDate: '', logName: '', totalDuration: '', totalCalories: '', notes: '' };
    },

    // 新增弹窗：提交表单（使用自增ID）
    submitAddForm() {
      this.$refs.addFormRef.validate((valid) => {
        if (valid) {
          // 核心修改：自增ID = 最大ID + 1
          const maxId = this.getMaxLogId();
          const newLogId = maxId + 1; // 自增逻辑

          // 组装新记录（logId为数字类型自增ID）
          const newLog = {
            logId: newLogId, // 自增ID（数字类型，如1、2、3...）
            ...this.addForm
          };

          // 保存到本地并刷新列表
          this.allLogs.unshift(newLog);
          this.saveLogs();
          this.filteredLogs = [...this.allLogs];
          this.addDialogVisible = false;
          this.$message.success('新增成功！');
        }
      });
    },

    // 编辑弹窗：打开弹窗
    openEditDialog(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },

    // 编辑弹窗：重置表单
    resetEditForm() {
      this.$refs.editFormRef && this.$refs.editFormRef.resetFields();
      this.editForm = { logId: '', logDate: '', logName: '', totalDuration: '', totalCalories: '', notes: '' };
    },

    // 编辑弹窗：提交修改
    submitEditForm() {
      this.$refs.editFormRef.validate((valid) => {
        if (valid) {
          const index = this.allLogs.findIndex(item => item.logId === this.editForm.logId);
          if (index !== -1) {
            this.allLogs.splice(index, 1, { ...this.editForm });
            this.saveLogs();
            this.filteredLogs = [...this.allLogs];
            this.editDialogVisible = false;
            this.$message.success('修改成功！');
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.filter-card {
  margin-bottom: 20px;
}
</style>cd
