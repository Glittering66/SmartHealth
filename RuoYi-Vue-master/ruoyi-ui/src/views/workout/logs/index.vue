<template>
  <div class="app-container">
    <!-- 查询表单：时间范围选择器 -->
    <el-form :inline="true" :model="queryParams" class="query-form" @submit.prevent="handleQuery">
      <el-form-item label="时间范围" prop="timeRange">
        <el-date-picker
          v-model="queryParams.timeRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 新增：新建按钮区域 -->
    <div class="btn-bar" style="margin: 10px 0;">
      <el-button type="primary" icon="el-icon-plus" @click="openAddDialog">新增运动记录</el-button>
    </div>

    <!-- 运动统计图表容器 -->
    <div id="sportChart" style="width: 100%; height: 400px; margin: 20px 0;"></div>

    <!-- 运动记录列表 -->
    <el-card class="log-list-card">
      <el-table :data="filteredLogs" border style="width: 100%" v-loading="listLoading">
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
// 引入API（确保接口定义正确）
import { delLogs, listLogs, addLogs, updateLogs, statisticByDate } from "@/api/workout/logs";
import * as echarts from 'echarts';

export default {
  data() {
    return {
      // 确保queryParams正确定义，包含timeRange
      queryParams: {
        timeRange: null, // 时间范围（daterange）
        pageNum: 1,      // 分页页码
        pageSize: 10     // 分页大小
      },
      // 列表加载状态
      listLoading: false,
      // 渲染的列表数据
      filteredLogs: [],
      // 原始列表数据
      allLogs: [],
      // ECharts实例（初始化为null）
      chartInstance: null,
      // 弹窗显隐
      addDialogVisible: false,
      editDialogVisible: false,
      // 新增表单数据（处理Vuex可能不存在的情况，添加容错）
      addForm: {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: '',
        userId: this.$store.getters.userId || '' // 容错：若getters不存在则赋值为空
      },
      // 编辑表单数据
      editForm: {
        logId: '',
        userId: '',
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      },
      // 表单校验规则
      addFormRules: {
        logDate: [{ required: true, message: '请选择运动日期时间', trigger: 'blur' }],
        logName: [{ required: true, message: '请输入运动名称', trigger: 'blur' }],
        totalDuration: [{ required: true, message: '请输入总时长', trigger: 'blur' }]
      },
      editFormRules: {
        logDate: [{ required: true, message: '请选择运动日期时间', trigger: 'blur' }],
        logName: [{ required: true, message: '请输入运动名称', trigger: 'blur' }],
        totalDuration: [{ required: true, message: '请输入总时长', trigger: 'blur' }]
      },
      // 筛选表单（可选）
      filterForm: {
        dateRange: [],
        logName: ""
      }
    };
  },
  created() {
    // 首次加载数据（带默认参数）
    this.handleQuery();
    // 监听事件总线（若有）
    this.$bus && this.$bus.$on("logsDataUpdated", () => {
      this.handleQuery();
    });
    // 监听路由参数（自动填充运动名称）
    this.$watch('$route.query', (query) => {
      if (query.actionName) {
        this.openAddDialog();
        this.$nextTick(() => {
          this.addForm.logName = query.actionName;
        });
      }
    }, { immediate: true });
  },
  mounted() {
    // 在mounted中初始化ECharts（DOM已渲染）
    this.initChart();
  },
  beforeDestroy() {
    // 销毁ECharts实例+移除事件监听
    if (this.chartInstance) {
      this.chartInstance.dispose();
      this.chartInstance = null;
    }
    window.removeEventListener('resize', this.handleChartResize);
    // 销毁事件总线
    this.$bus && this.$bus.$off("logsDataUpdated");
  },
  methods: {
    // ECharts resize事件处理（抽成独立方法）
    handleChartResize() {
      this.chartInstance && this.chartInstance.resize();
    },
    // 初始化ECharts图表
    initChart() {
      const chartDom = document.getElementById('sportChart');
      if (!chartDom) {
        this.$message.warning('图表容器不存在，无法初始化图表');
        return;
      }
      this.chartInstance = echarts.init(chartDom);
      // 初始化时显示“加载中”文本（替代转圈）
      this.setChartTip('加载中...');
      // 添加resize监听
      window.addEventListener('resize', this.handleChartResize);
    },
    // 统一查询逻辑：图表+列表联动
    async handleQuery() {
      this.listLoading = true;
      try {
        // 格式化查询参数
        const params = this.formatQueryParams();
        // 1. 查询列表数据
        const logRes = await listLogs(params);
        const rawData = logRes.rows || [];
        // 字段映射（后端下划线 → 前端驼峰）
        this.allLogs = rawData.map(log => ({
          logId: log.logId || log.log_id,
          userId: log.userId || log.user_id,
          logDate: log.workoutDate || log.workout_date,
          logName: log.workoutName || log.workout_name,
          totalDuration: log.totalDuration || log.total_duration,
          totalCalories: log.totalCalories || log.total_calories,
          notes: log.notes || ''
        }));
        this.filteredLogs = [...this.allLogs];
        // 2. 查询图表统计数据
        await this.loadChartData(params);
      } catch (error) {
        this.$message.error('数据加载失败：' + error.message);
        this.allLogs = [];
        this.filteredLogs = [];
      } finally {
        this.listLoading = false;
      }
    },
    // 格式化查询参数
    formatQueryParams() {
      const params = { ...this.queryParams };
      // 处理时间范围：拆分为startTime/endTime
      if (params.timeRange && params.timeRange.length === 2) {
        params.startTime = `${params.timeRange[0]} 00:00:00`;
        params.endTime = `${params.timeRange[1]} 23:59:59`;
      }
      // 删除后端不需要的字段
      delete params.timeRange;
      return params;
    },
    // 加载图表数据（添加兜底）
    async loadChartData(params) {
      // 确保图表实例存在
      if (!this.chartInstance) {
        this.initChart();
        if (!this.chartInstance) return;
      }

      try {
        // 调用统计接口
        const res = await statisticCalorieDuration(params);
        const { dateList = [], calorieList = [], durationList = [] } = res.data || {};

        // 有数据：正常渲染图表，清空提示文本
        const option = {
          title: { text: '每日热量消耗&运动时长统计', left: 'center' },
          tooltip: {
            trigger: 'axis',
            formatter: (params) => {
              let tip = `<div>日期：${params[0].name}</div>`;
              params.forEach(item => {
                const unit = item.seriesName === '热量消耗' ? 'kcal' : '分钟';
                tip += `<div>${item.seriesName}：${item.value} ${unit}</div>`;
              });
              return tip;
            }
          },
          xAxis: { type: 'category', data: dateList },
          yAxis: [
            { type: 'value', name: '运动时长（分钟）', min: 0, axisLabel: { formatter: '{value} 分' } },
            { type: 'value', name: '热量消耗（kcal）', min: 0, axisLabel: { formatter: '{value} kcal' }, position: 'right', offset: 80 }
          ],
          series: [
            { name: '运动时长', type: 'bar', data: durationList, yAxisIndex: 0, itemStyle: { color: '#1890ff' } },
            { name: '热量消耗', type: 'line', data: calorieList, yAxisIndex: 1, itemStyle: { color: '#ff4d4f' }, lineStyle: { width: 2 } }
          ],
          legend: { data: ['运动时长', '热量消耗'], top: 30 },
          graphic: null // 清空提示文本（有数据时不显示）
        };
        this.chartInstance.setOption(option);

        // 无数据：显示“暂无统计数据”文本
        if (dateList.length === 0) {
          this.setChartTip('暂无统计数据');
        }
      } catch (error) {
        // 加载失败：显示“加载失败”文本（不转圈）
        this.$message.error('图表加载失败：' + error.message);
        this.setChartTip('加载失败，请重试');
      }
    },
    // 新增：设置图表的文本提示（替代loading转圈）
    setChartTip(text) {
      const option = {
        // 清空轴和数据，避免干扰
        xAxis: { data: [] },
        yAxis: [
          { type: 'value', name: '运动时长（分钟）' },
          { type: 'value', name: '热量消耗（kcal）', position: 'right' }
        ],
        series: [],
        // 显示提示文本
        graphic: {
          type: 'text',
          left: 'center',
          top: 'center',
          style: {
            text: text,
            fontSize: 16,
            fill: '#999' // 灰色文本，与界面风格统一
          }
        }
      };
      this.chartInstance.setOption(option);
    },
    // 重置查询参数
    resetQuery() {
      this.queryParams = {
        timeRange: null,
        pageNum: 1,
        pageSize: 10
      };
      this.filterForm = { dateRange: [], logName: "" };
      this.handleQuery();
    },
    // 删除运动记录（简化逻辑，仅调用后端接口）
    async deleteLog(row) {
      try {
        await this.$confirm('确定删除该记录及关联动作和组记录吗？', '提示', { type: 'warning' });
        await delLogs(row.logId);
        this.$message.success('删除成功');
        this.handleQuery();
      } catch (error) {
        if (error.name !== 'AbortError') {
          this.$message.error('删除失败：' + (error.message || '网络异常'));
        }
      }
    },
    // 打开新增弹窗
    openAddDialog() {
      this.addDialogVisible = true;
      this.resetAddForm();
    },
    // 重置新增表单
    resetAddForm() {
      this.$refs.addFormRef && this.$refs.addFormRef.resetFields();
      // 保留用户ID（从Vuex获取，添加容错）
      const userId = this.$store.getters.userId || '';
      this.addForm = {
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: '',
        userId: userId
      };
      // 清除路由参数
      if (this.$route.query.actionName) {
        this.$router.replace({ query: { ...this.$route.query, actionName: undefined } });
      }
    },
    // 重置编辑表单（修复未定义问题）
    resetEditForm() {
      this.$refs.editFormRef && this.$refs.editFormRef.resetFields();
      // 容错：若Vuex的userId不存在则赋值为空
      const userId = this.$store.getters.userId || '';
      this.editForm = {
        logId: '',
        userId: userId,
        logDate: '',
        logName: '',
        totalDuration: '',
        totalCalories: 0,
        notes: ''
      };
    },
    // 提交新增表单
    async submitAddForm() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        try {
          const addData = {
            userId: this.addForm.userId,
            workoutDate: this.addForm.logDate,
            workoutName: this.addForm.logName,
            totalDuration: Number(this.addForm.totalDuration),
            totalCalories: 0,
            notes: this.addForm.notes
          };
          const res = await addLogs(addData);
          if (res.code === 200) {
            this.$message.success('新增成功');
            this.addDialogVisible = false;
            this.handleQuery();
          } else {
            this.$message.error('新增失败：' + res.msg);
          }
        } catch (error) {
          this.$message.error('新增请求失败：' + error.message);
        }
      });
    },
    // 打开编辑弹窗
    openEditDialog(row) {
      this.editForm = {
        logId: row.logId,
        userId: row.userId || (this.$store.getters.userId || ''), // 容错
        logDate: row.logDate,
        logName: row.logName,
        totalDuration: row.totalDuration,
        totalCalories: row.totalCalories,
        notes: row.notes
      };
      this.editDialogVisible = true;
    },
    // 提交编辑表单
    async submitEditForm() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (!valid) return;
        try {
          const editData = {
            logId: this.editForm.logId,
            userId: this.editForm.userId,
            workoutDate: this.editForm.logDate,
            workoutName: this.editForm.logName,
            totalDuration: Number(this.editForm.totalDuration),
            totalCalories: this.editForm.totalCalories,
            notes: this.editForm.notes
          };
          const res = await updateLogs(editData);
          if (res.code === 200) {
            this.$message.success('修改成功');
            this.editDialogVisible = false;
            this.handleQuery();
          } else {
            this.$message.error('修改失败：' + res.msg);
          }
        } catch (error) {
          this.$message.error('修改请求失败：' + error.message);
        }
      });
    },
    // 跳转到详情页
    goToDetails(logId) {
      this.$router.push({path: "/workout/details", query: {logId}});
    },
    // 筛选逻辑（可选）
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
        result = result.filter(item => item.logName.includes(this.filterForm.logName));
      }
      this.filteredLogs = result;
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.log-list-card {
  margin-top: 20px;
}

.query-form {
  margin-bottom: 20px;
}

/* 新增按钮栏样式 */
.btn-bar {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
</style>
