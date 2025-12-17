<template>
  <div class="app-container">
    <!-- 顶部操作栏 -->
    <div class="header-bar">
      <h2>我的运动记录</h2>
      <el-button type="primary" icon="el-icon-plus" @click="openAddDialog">
        新增运动记录
      </el-button>
    </div>

    <!-- 筛选区域 -->
    <el-card class="filter-card">
      <el-form inline :model="filterForm">
        <el-form-item label="运动日期时间">
          <!-- 修改1：筛选区域改为日期时间范围选择（datetimerange），添加格式配置 -->
          <el-date-picker
            v-model="filterForm.dateRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期时间"
            end-placeholder="结束日期时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="运动名称">
          <el-input v-model="filterForm.logName" placeholder="请输入运动名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="filterLogs">查询</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 运动记录列表 -->
    <el-card class="log-list-card">
      <el-table :data="filteredLogs" border style="width: 100%">
        <!-- 修改2：表格列名改为“运动日期时间”（可选，仅语义优化） -->
        <el-table-column prop="logDate" label="运动日期时间" width="240" />
        <el-table-column prop="logName" label="运动名称" />
        <el-table-column prop="totalDuration" label="总时长(分钟)" width="120" />
        <el-table-column prop="totalCalories" label="消耗热量(kcal)" width="140" />
        <el-table-column prop="notes" label="备注" min-width="150" />
        <el-table-column label="操作" width="300">
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

    <!-- 新增运动记录弹窗 -->
    <el-dialog
      title="新增运动记录"
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
        <el-form-item label="运动日期时间" prop="logDate">
          <!-- 修改3：新增弹窗改为日期时间选择（datetime），添加格式配置 -->
          <el-date-picker
            v-model="addForm.logDate"
            type="datetime"
            placeholder="请选择运动日期时间"
            style="width: 100%"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="运动名称" prop="logName">
          <el-input v-model="addForm.logName" placeholder="请输入运动名称" />
        </el-form-item>
        <el-form-item label="总时长(分钟)" prop="totalDuration">
          <el-input-number v-model="addForm.totalDuration" :min="1" placeholder="请输入总时长" />
        </el-form-item>
        <!-- 热量输入框：保持禁用状态 -->
        <el-form-item label="消耗热量(kcal)">
          <el-input-number
            v-model="addForm.totalCalories"
            :disabled="true"
            placeholder="自动计算，无需填写"
            style="width: 100%"
          />
          <div style="font-size: 12px; color: #999; margin-top: 5px">
            该字段将根据动作和组记录自动计算
          </div>
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

    <!-- 编辑运动记录弹窗 -->
    <el-dialog
      title="编辑运动记录"
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
        <el-form-item label="运动日期时间" prop="logDate">
          <!-- 修改4：编辑弹窗改为日期时间选择（datetime），添加格式配置 -->
          <el-date-picker
            v-model="editForm.logDate"
            type="datetime"
            placeholder="请选择运动日期时间"
            style="width: 100%"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="运动名称" prop="logName">
          <el-input v-model="editForm.logName" placeholder="请输入运动名称" />
        </el-form-item>
        <el-form-item label="总时长(分钟)" prop="totalDuration">
          <el-input-number v-model="editForm.totalDuration" :min="1" placeholder="请输入总时长" />
        </el-form-item>
        <!-- 热量输入框：保持禁用状态 -->
        <el-form-item label="消耗热量(kcal)">
          <el-input-number
            v-model="editForm.totalCalories"
            :disabled="true"
            placeholder="自动计算，无需填写"
            style="width: 100%"
          />
          <div style="font-size: 12px; color: #999; margin-top: 5px">
            该字段将根据动作和组记录自动计算
          </div>
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
      allLogs: [], // 所有运动记录（localStorage）
      filteredLogs: [], // 筛选后的记录
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      },
      editForm: {
        logId: '',
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      },
      addFormRules: {
        logDate: [{ required: true, message: '请选择运动日期时间', trigger: 'blur' }],
        logName: [{ required: true, message: '请输入运动名称', trigger: 'blur' }],
        totalDuration: [{ required: true, message: '请输入总时长', trigger: 'blur' }]
      },
      editFormRules: {
        logDate: [{ required: true, message: '请选择运动日期时间', trigger: 'blur' }],
        logName: [{ required: true, message: '请输入运动名称', trigger: 'blur' }],
        totalDuration: [{ required: true, message: '请输入总时长', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.loadLogs();
    // 监听事件总线的更新通知（Vue2）
    this.$bus.$on("logsDataUpdated", () => {
      this.loadLogs(); // 重新加载数据，刷新页面
    });
  },
  beforeDestroy() {
    // 销毁事件监听，防止内存泄漏
    this.$bus.$off("logsDataUpdated");
  },
  watch: {
    // 监听路由参数变化
    '$route.query': {
      handler(query) {
        // 如果存在actionName参数，自动打开新增弹窗并填充运动名称
        if (query.actionName) {
          this.openAddDialog(); // 打开新增弹窗
          // 等待弹窗渲染完成后再赋值（避免DOM未加载）
          this.$nextTick(() => {
            this.addForm.logName = query.actionName; // 填充运动名称
          });
        }
      },
      immediate: true // 页面初始化时就执行一次监听逻辑
    }
  },
  methods: {
    // 加载本地运动记录
    loadLogs() {
      const storedLogs = localStorage.getItem("workout_logs");
      this.allLogs = storedLogs ? JSON.parse(storedLogs) : [];
      // 确保logId等字段为数字类型，避免匹配失败
      this.allLogs = this.allLogs.map(log => ({
        ...log,
        logId: Number(log.logId),
        totalDuration: Number(log.totalDuration) || 0,
        totalCalories: Number(log.totalCalories) || 0
      }));
      this.filteredLogs = [...this.allLogs];
    },

    // 保存运动记录到本地
    saveLogs() {
      localStorage.setItem("workout_logs", JSON.stringify(this.allLogs));
    },

    // 筛选运动记录（逻辑不变，Date对象可处理带时分秒的字符串）
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

    // 跳转到新增运动记录页（保留但未使用）
    goToAddLog() {
      this.$router.push("/workout/logs/add");
    },

    // 跳转到运动动作详情页
    goToDetails(logId) {
      this.$router.push({
        path: "/workout/details",
        query: { logId }
      });
    },

    // 编辑运动记录（保留但未使用）
    editLog(row) {
      this.$router.push({
        path: "/workout/logs/edit",
        query: { logId: row.logId }
      });
    },

    // 删除运动记录+关联的details+关联的sets
    deleteLog(row) {
      this.$confirm("确定删除该记录及关联动作和组记录吗？", "提示", { type: "warning" })
        .then(() => {
          // 1. 删除当前日志
          this.allLogs = this.allLogs.filter(item => item.logId !== row.logId);
          this.saveLogs();
          this.filteredLogs = [...this.allLogs];

          // 2. 获取所有动作详情，筛选出该日志关联的详情（要删除的）
          const storedExercises = localStorage.getItem("workout_exercise_details");
          const exercises = storedExercises ? JSON.parse(storedExercises) : [];
          // 统一logId类型为数字，避免匹配失败
          const deletedDetails = exercises.filter(item => Number(item.logId) === Number(row.logId));
          const newExercises = exercises.filter(item => Number(item.logId) !== Number(row.logId));
          // 保存删除后的详情
          localStorage.setItem("workout_exercise_details", JSON.stringify(newExercises));

          // 3. 获取要删除的详情的detailId（用于删除关联的sets）
          // 统一detailId类型为字符串，避免类型不匹配
          const deletedDetailIds = deletedDetails.map(item => String(item.detailId));

          // 4. 获取所有组记录，删除关联的sets
          const storedSets = localStorage.getItem("exercise_sets");
          const allSets = storedSets ? JSON.parse(storedSets) : [];
          // 过滤掉属于被删除详情的sets
          const newSets = allSets.filter(item => !deletedDetailIds.includes(String(item.detailId)));
          // 保存删除后的sets
          localStorage.setItem("exercise_sets", JSON.stringify(newSets));

          this.$message.success("删除成功（含关联动作和组记录）");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    // 新增：获取当前最大ID
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
      this.addForm = {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      };
      // 清除路由中的actionName参数
      if (this.$route.query.actionName) {
        this.$router.replace({
          query: { ...this.$route.query, actionName: undefined }
        });
      }
    },

    // 新增弹窗：提交表单
    submitAddForm() {
      this.$refs.addFormRef.validate((valid) => {
        if (valid) {
          // 自增ID = 最大ID + 1
          const maxId = this.getMaxLogId();
          const newLogId = maxId + 1; // 自增逻辑

          // 组装新记录
          const newLog = {
            logId: newLogId,
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
      this.editForm = {
        logId: '',
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      };
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
</style>
