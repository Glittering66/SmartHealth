<template>
  <div class="app-container">
    <!-- ================= 顶部导航 ================= -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
      <el-breadcrumb-item to="/food/foodgraph">食物知识图谱</el-breadcrumb-item>
      <el-breadcrumb-item>{{ foodGroup }}</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin: 16px 0">{{ foodGroup }} · 宏量营养素概览</h2>

    <!-- ================= 分类统计卡片（后端直接返回） ================= -->
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="8">
        <el-card>
          <div class="stat-title">食物数量</div>
          <div class="stat-value">{{ stats.total }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div class="stat-title">平均热量 (kcal)</div>
          <div class="stat-value">{{ stats.avgCalories }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div class="stat-title">高蛋白食物数量</div>
          <div class="stat-value">{{ stats.highProteinCount }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- ================= 查询条件 ================= -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      label-width="80px"
    >
      <el-form-item label="食物ID" prop="foodId">
        <el-input
          v-model="queryParams.foodId"
          placeholder="请输入食物ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >
          搜索
        </el-button>
        <el-button
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
        >
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <!-- ================= 食物宏量营养素表格（联表结果） ================= -->
    <el-table v-loading="loading" :data="macroList" border>
      <el-table-column label="食物ID" prop="foodId" align="center" />
      <el-table-column label="食物名称" prop="foodName" align="center" />
      <el-table-column label="热量(kcal)" prop="calories" align="center" />
      <el-table-column label="蛋白质(g)" prop="protein" align="center" />
      <el-table-column label="脂肪(g)" prop="fat" align="center" />
      <el-table-column label="碳水化合物(g)" prop="carbohydrate" align="center" />
    </el-table>

    <!-- ================= 分页 ================= -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listMacroByGroup } from "@/api/food/macro"

export default {
  name: "MacroByFoodGroup",
  data() {
    return {
      loading: false,
      foodGroup: "",
      total: 0,
      macroList: [],
      // 统计信息由后端直接返回
      stats: {
        total: 0,
        avgCalories: 0,
        highProteinCount: 0
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        foodId: ""
      }
    }
  },
  created() {
    // 从知识图谱页面获取分类参数
    this.foodGroup = this.$route.query.foodGroup || "未分类"
    this.getList()
  },
  methods: {
    /** ================= 查询（联表 + 统计） ================= */
    getList() {
      this.loading = true
      listMacroByGroup(this.foodGroup, this.queryParams).then(res => {
        this.macroList = res.rows || []
        this.total = res.total || 0
        this.stats = res.stats || {
          total: 0,
          avgCalories: 0,
          highProteinCount: 0
        }
        this.loading = false
      })
    },

    /** ================= 搜索 ================= */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },

    /** ================= 重置 ================= */
    resetQuery() {
      this.queryParams.foodId = ""
      this.handleQuery()
    }
  }
}
</script>

<style scoped>
.stat-title {
  font-size: 14px;
  color: #909399;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  margin-top: 8px;
}
</style>
