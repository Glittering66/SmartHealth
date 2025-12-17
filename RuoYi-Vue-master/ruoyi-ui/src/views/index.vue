<template>
  <div class="health-container">
    <!-- 顶部统计卡片 -->
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6" v-for="item in stats" :key="item.title">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-title">{{ item.title }}</div>
          <div class="stat-value">{{ item.value }}</div>
          <div class="stat-desc">{{ item.desc }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 中部内容 -->
    <el-row :gutter="20" class="content-row">
      <!-- 健康指标 -->
      <el-col :span="12">
        <el-card shadow="never">
          <div slot="header" class="card-header">
            <span>今日健康指标</span>
          </div>
          <el-table :data="healthData" size="small" border style="width: 100%">
            <el-table-column prop="name" label="指标" width="120" />
            <el-table-column prop="value" label="数值" />
            <el-table-column prop="status" label="状态">
              <template slot-scope="scope">
                <el-tag :type="scope.row.statusType" size="mini">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 饮食与运动建议 -->
      <el-col :span="12">
        <el-card shadow="never">
          <div slot="header" class="card-header">
            <span>智能建议</span>
          </div>
          <el-timeline>
            <el-timeline-item
              v-for="(item, index) in suggestions"
              :key="index"
              :timestamp="item.time"
              placement="top">
              {{ item.content }}
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>

    <!-- 底部操作区 -->
    <el-card shadow="never" class="action-card">
      <el-form :inline="true" :model="form" size="small">
        <el-form-item label="身高(cm)">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="体重(kg)">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="calculateBMI">计算BMI</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
      <div v-if="bmiResult" class="bmi-result">
        当前 BMI：<strong>{{ bmiResult }}</strong>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'SmartHealthDashboard',
  data() {
    return {
      stats: [
        { title: '今日步数', value: '8,462', desc: '目标 10,000 步' },
        { title: '' +
            '消耗热量', value: '530 kcal', desc: '运动消耗' },
        { title: '睡眠时长', value: '7.2 h', desc: '昨晚睡眠' },
        { title: '饮水量', value: '1.6 L', desc: '建议 ≥ 2 L' }
      ],
      healthData: [
        { name: '心率', value: '72 bpm', status: '正常', statusType: 'success' },
        { name: '血压', value: '118 / 76', status: '正常', statusType: 'success' },
        { name: '血糖', value: '5.2 mmol/L', status: '正常', statusType: 'success' },
        { name: 'BMI', value: '22.4', status: '健康', statusType: 'info' }
      ],
      suggestions: [
        { time: '08:00', content: '早餐建议：全麦面包 + 鸡蛋 + 牛奶' },
        { time: '12:30', content: '午餐注意控制油脂摄入，多吃蔬菜' },
        { time: '18:30', content: '晚间进行 30 分钟中等强度运动' }
      ],
      form: {
        height: '',
        weight: ''
      },
      bmiResult: ''
    }
  },
  methods: {
    calculateBMI() {
      const h = this.form.height / 100
      const w = this.form.weight
      if (!h || !w) {
        this.$message.warning('请输入完整的身高和体重')
        return
      }
      this.bmiResult = (w / (h * h)).toFixed(2)
    },
    resetForm() {
      this.form.height = ''
      this.form.weight = ''
      this.bmiResult = ''
    }
  }
}
</script>

<style scoped>
.health-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.stat-row {
  margin-bottom: 20px;
}

.stat-card {
  text-align: center;
}

.stat-title {
  font-size: 14px;
  color: #909399;
}

.stat-value {
  font-size: 26px;
  font-weight: bold;
  margin: 8px 0;
  color: #303133;
}

.stat-desc {
  font-size: 12px;
  color: #c0c4cc;
}

.card-header {
  font-size: 15px;
  font-weight: 600;
}

.content-row {
  margin-bottom: 20px;
}

.action-card {
  background: #ffffff;
}

.bmi-result {
  margin-top: 10px;
  font-size: 14px;
  color: #303133;
}
</style>
