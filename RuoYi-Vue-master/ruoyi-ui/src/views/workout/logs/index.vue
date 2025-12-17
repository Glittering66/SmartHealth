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
          <!-- 修改1：筛选区域改为日期时间范围选择（datetime range），添加格式配置 -->
          <el-date-picker
            v-model="filterForm.dateRange"
            type="datetime range"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
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
// 问题3修复：补充导入 addLogs、updateLogs（需确保 API 文件中导出了这两个方法）
import {delLogs, listLogs, addLogs, updateLogs} from "@/api/workout/logs";
import { delDetails } from "@/api/workout/details";
import { delSets } from "@/api/workout/sets";

export default {
  data() {
    return {
      filterForm: { dateRange: [], logName: "" },
      allLogs: [], // 从数据库获取的所有记录
      filteredLogs: [],
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: '',
        userId: localStorage.getItem('userId') || '' // 保留用户ID
      },
      editForm: {
        // 问题5修复：添加 userId 字段
        logId: '',
        userId: '',
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
    // 初始化用户ID（从登录态获取，确保新增时能关联用户）
    this.addForm.userId = localStorage.getItem('userId') || '';
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
    // 问题1修复：修改 loadLogs 方法，添加兜底处理，确保 data 是数组
    async loadLogs() {
      try {
        // 调用 API 查询所有运动记录（可传筛选参数，此处先查全部）
        const response = await listLogs({});
        // 步骤1：兼容后端返回格式，兜底为数组（根据后端实际格式调整）
        // 兼容：response.data / response.rows / 直接返回数组 / 空数据
        const rawData = response?.data || response?.rows || response || [];
        // 步骤2：确保是数组（防止后端返回非数组）
        const safeData = Array.isArray(rawData) ? rawData : [];

        // 字段映射：确保前端字段与后端返回一致（后端下划线转前端驼峰）
        this.allLogs = safeData.map(log => ({
          logId: log.logId || log.log_id || '',
          userId: log.userId || log.user_id || '',
          logDate: log.workoutDate || log.workout_date || '',
          logName: log.workoutName || log.workout_name || '',
          totalDuration: log.totalDuration || log.total_duration || 0,
          totalCalories: log.totalCalories || log.total_calories || 0,
          notes: log.notes || ''
        }));

        this.filteredLogs = [...this.allLogs]; // 初始化筛选列表
      } catch (error) {
        this.$message.error("接口请求失败：" + (error.message || "网络异常"));
        // 步骤3：接口报错时，兜底设为空数组
        this.allLogs = [];
        this.filteredLogs = [];
      }
    },

    // 保存运动记录到本地（保留，可作为兜底）
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
        // 关键：将回调改为async，支持异步API调用
        .then(async () => {
          try {
            // 问题2修复：定义 logId 变量（row.logId）
            const logId = row.logId;
            // ================ 新增：同步删除数据库数据 ================
            // 依次调用3个删除API（需确保后端API接收logId参数）
            await delLogs(row.logId);
            this.$message.success("删除成功");
            this.loadLogs();

            // ================ 原有本地存储删除逻辑（保持不变） ================
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

            this.$message.success("删除成功（含关联动作、组记录及数据库数据）");
          } catch (error) {
            // 捕获API调用异常
            this.$message.error("删除失败：" + (error.message || "网络异常"));
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    // 新增：获取当前最大ID（保留，本地逻辑）
    getMaxLogId() {
      if (this.allLogs.length === 0) return 0;
      const ids = this.allLogs.map(item => Number(item.logId));
      return Math.max(...ids);
    },

    // 新增弹窗：打开弹窗
    openAddDialog() {
      this.addDialogVisible = true;
      this.resetAddForm();
    },

    // 问题4修复：重置新增表单时，保留 userId
    resetAddForm() {
      this.$refs.addFormRef && this.$refs.addFormRef.resetFields();
      // 保留 userId，不被清空
      const userId = this.addForm.userId;
      this.addForm = {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: '',
        userId: userId // 恢复 userId
      };
      // 清除路由中的actionName参数
      if (this.$route.query.actionName) {
        this.$router.replace({
          query: { ...this.$route.query, actionName: undefined }
        });
      }
    },

    // 新增弹窗：提交表单
    async submitAddForm() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (valid) {
          try {
            // 字段映射：前端驼峰 → 数据库下划线（匹配后端接收格式）
            const addData = {
              userId: this.addForm.userId,
              workoutDate: this.addForm.logDate,
              workoutName: this.addForm.logName,
              totalDuration: Number(this.addForm.totalDuration),
              totalCalories: Number(this.addForm.totalCalories) || 0,
              notes: this.addForm.notes || ""
            };

            // 调用新增 API，插入数据库
            const response = await addLogs(addData);
            if (response.code === 200) {
              this.$message.success('新增运动记录成功（已插入数据库）');
              // 重新加载数据，刷新列表（从数据库获取最新记录）
              this.loadLogs();
              this.addDialogVisible = false;
              this.resetAddForm(); // 重置表单
            } else {
              this.$message.error("新增失败：" + response.msg);
            }
          } catch (error) {
            this.$message.error("新增请求失败：" + (error.message || "网络异常"));
          }
        }
      });
    },

    // 编辑弹窗：打开弹窗（补充：赋值 userId）
    openEditDialog(row) {
      // 赋值 userId（从 row 中获取）
      this.editForm = { ...row, userId: row.userId || localStorage.getItem('userId') };
      this.editDialogVisible = true;
    },

    // 编辑弹窗：重置表单
    resetEditForm() {
      this.$refs.editFormRef && this.$refs.editFormRef.resetFields();
      this.editForm = {
        logId: '',
        userId: '', // 保留 userId 字段
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      };
    },

    // 问题6修复：编辑提交添加 try/catch，判断响应格式
    async submitEditForm() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (valid) {
          try {
            const editData = {
              logId: this.editForm.logId,
              userId: this.editForm.userId,
              workoutDate: this.editForm.logDate,
              workoutName: this.editForm.logName,
              totalDuration: Number(this.editForm.totalDuration),
              totalCalories: Number(this.editForm.totalCalories),
              notes: this.editForm.notes
            };

            const response = await updateLogs(editData);
            if (response.code === 200) {
              this.$message.success('修改成功（已更新数据库）');
              this.loadLogs();
              this.editDialogVisible = false;
            } else {
              this.$message.error("修改失败：" + response.msg);
            }
          } catch (error) {
            this.$message.error("修改请求失败：" + (error.message || "网络异常"));
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
