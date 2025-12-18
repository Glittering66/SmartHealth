<template>
  <div class="app-container">
    <!-- ================= 顶部导航 ================= -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
      <el-breadcrumb-item to="/food/foodgraph">食物知识图谱</el-breadcrumb-item>
      <el-breadcrumb-item>{{ foodGroup }}</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin: 16px 0">{{ pageTitle }}</h2>

    <!-- ================= 分类统计卡片 ================= -->
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="8">
        <el-card>
          <div class="stat-title">食物数量</div>
          <div class="stat-value">{{ stats.total }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div class="stat-title">平均热量 (kcal / 100g)</div>
          <div class="stat-value">{{ stats.avgCalories }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div class="stat-title">高蛋白食物数量（≥20g）</div>
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
      <el-form-item label="食物名称">
        <el-input
          v-model="queryParams.foodName"
          placeholder="请输入食物名称"
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

    <!-- ================= 宏量营养素表格 ================= -->
    <el-table
      v-loading="loading"
      :data="macroList"
      border
      @sort-change="handleSortChange"
    >
      <el-table-column label="食物ID" prop="foodId" align="center" />

      <el-table-column label="食物名称" prop="foodName" align="center" />

      <el-table-column
        label="热量(kcal)"
        prop="calories"
        align="center"
        sortable="custom"
      />

      <el-table-column
        label="蛋白质(g)"
        prop="protein"
        align="center"
        sortable="custom"
      />

      <el-table-column
        label="脂肪(g)"
        prop="fat"
        align="center"
        sortable="custom"
      />

      <el-table-column
        label="碳水化合物(g)"
        prop="carbohydrate"
        align="center"
        sortable="custom"
      />

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >
            详情
          </el-button>
        </template>
      </el-table-column>
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
import { listMacroByCombo } from "@/api/food/macro"

export default {
  name: "MacroByFoodGroup",
  data() {
    return {
      loading: false,
      foodGroup: "",
      pageTitle: "",
      total: 0,
      macroList: [],

      // 后端返回的统计信息
      stats: {
        total: 0,
        avgCalories: 0,
        highProteinCount: 0
      },

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        foodName: "",
        foodGroup: "",
        orderField: "", // 排序字段
        orderType: ""   // asc / desc
      }
    }
  },

  created() {
    // 从知识图谱页面带过来的分类
    this.foodGroup = this.$route.query.foodGroup || ""
    this.queryParams.foodGroup = this.foodGroup

    this.pageTitle = this.foodGroup
      ? `${this.foodGroup} · 宏量营养素概览`
      : "食物宏量营养素查询"

    this.getList()
  },

  methods: {
    /** ================= 跳转详情 ================= */
    handleDetail(row) {
      this.$router.push({
        path: "/food/fooddetail/index",
        query: {id: row.foodId}
      })
    },

    /** ================= 查询列表 ================= */
    getList() {
      this.loading = true
      listMacroByCombo(this.queryParams)
        .then(res => {
          this.macroList = res.rows || []
          this.total = res.total || 0
          this.stats = res.stats || {
            total: 0,
            avgCalories: 0,
            highProteinCount: 0
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },

    /** ================= 搜索 ================= */
    handleQuery() {
      this.queryParams.pageNum = 1
      // 搜索时重置排序
      this.queryParams.orderField = ""
      this.queryParams.orderType = ""
      this.getList()
    },

    /** ================= 重置 ================= */
    resetQuery() {
      this.queryParams.foodName = ""
      this.handleQuery()
    },

    /** ================= 排序（升 / 降 / 重置） ================= */
    handleSortChange({prop, order}) {
      // order: ascending / descending / null
      if (!order) {
        // 单列排序重置
        this.queryParams.orderField = ""
        this.queryParams.orderType = ""
      } else {
        this.queryParams.orderField = prop
        this.queryParams.orderType =
          order === "ascending" ? "asc" : "desc"
      }
      this.queryParams.pageNum = 1
      this.getList()
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
