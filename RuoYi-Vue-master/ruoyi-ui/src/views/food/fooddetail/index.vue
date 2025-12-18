<template>
  <div class="app-container">
    <!-- 加载中 -->
    <el-card shadow="never" v-if="loading">
      <el-skeleton :rows="10" animated />
    </el-card>

    <!-- 内容 -->
    <template v-else>
      <!-- 顶部基本信息 -->
      <el-card shadow="never">
        <div class="food-header">
          <div class="title">{{ detail.foodName || "--" }}</div>
          <div class="sub">分类：{{ detail.foodGroup || "--" }}</div>
        </div>
      </el-card>

      <!-- Tabs -->
      <el-tabs type="border-card" class="mt20">

        <!-- 份量信息 -->
        <el-tab-pane label="食用份量">
          <el-table
            v-if="detail.servings.length"
            :data="detail.servings"
            border
            style="width: 100%"
          >
            <el-table-column label="单份重量(g)" prop="servingWeight" align="center" />
            <el-table-column label="份量描述" prop="servingDesc" align="center" />
          </el-table>
          <el-empty v-else description="暂无份量信息" />
        </el-tab-pane>

        <!-- 宏量营养素 -->
        <el-tab-pane label="宏量营养素">
          <el-descriptions :column="3" border>
            <el-descriptions-item label="热量">
              {{ showValue(detail.macro.calories, "kcal") }}
            </el-descriptions-item>
            <el-descriptions-item label="蛋白质">
              {{ showValue(detail.macro.protein, "g") }}
            </el-descriptions-item>
            <el-descriptions-item label="脂肪">
              {{ showValue(detail.macro.fat, "g") }}
            </el-descriptions-item>
            <el-descriptions-item label="碳水化合物">
              {{ showValue(detail.macro.carbohydrate, "g") }}
            </el-descriptions-item>
            <el-descriptions-item label="膳食纤维">
              {{ showValue(detail.macro.fiber, "g") }}
            </el-descriptions-item>
            <el-descriptions-item label="糖">
              {{ showValue(detail.macro.sugars, "g") }}
            </el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>

        <!-- 脂肪酸 -->
        <el-tab-pane label="脂肪酸">
          <template v-if="hasData(detail.fattyAcids)">
            <el-descriptions :column="3" border>
              <el-descriptions-item label="Omega-3">
                {{ showValue(detail.fattyAcids.omega3, "g") }}
              </el-descriptions-item>
              <el-descriptions-item label="Omega-6">
                {{ showValue(detail.fattyAcids.omega6, "g") }}
              </el-descriptions-item>
              <el-descriptions-item label="DHA">
                {{ showValue(detail.fattyAcids.dha, "g") }}
              </el-descriptions-item>
              <el-descriptions-item label="EPA">
                {{ showValue(detail.fattyAcids.epa, "g") }}
              </el-descriptions-item>
              <el-descriptions-item label="单不饱和脂肪">
                {{ showValue(detail.fattyAcids.monoFat, "g") }}
              </el-descriptions-item>
              <el-descriptions-item label="多不饱和脂肪">
                {{ showValue(detail.fattyAcids.polyFat, "g") }}
              </el-descriptions-item>
            </el-descriptions>
          </template>
          <el-empty v-else description="暂无脂肪酸数据" />
        </el-tab-pane>

        <!-- 矿物质 -->
        <el-tab-pane label="矿物质">
          <template v-if="hasData(detail.minerals)">
            <el-descriptions :column="3" border>
              <el-descriptions-item label="钙">
                {{ showValue(detail.minerals.calcium, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="铁">
                {{ showValue(detail.minerals.iron, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="钾">
                {{ showValue(detail.minerals.potassium, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="镁">
                {{ showValue(detail.minerals.magnesium, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="钠">
                {{ showValue(detail.minerals.sodium, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="锌">
                {{ showValue(detail.minerals.zinc, "mg") }}
              </el-descriptions-item>
            </el-descriptions>
          </template>
          <el-empty v-else description="暂无矿物质数据" />
        </el-tab-pane>

        <!-- 维生素 -->
        <el-tab-pane label="维生素">
          <template v-if="hasData(detail.vitamins)">
            <el-descriptions :column="3" border>
              <el-descriptions-item label="维生素A">
                {{ showValue(detail.vitamins.vitaminARae, "μg") }}
              </el-descriptions-item>
              <el-descriptions-item label="维生素C">
                {{ showValue(detail.vitamins.vitaminC, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="维生素D">
                {{ showValue(detail.vitamins.vitaminD, "μg") }}
              </el-descriptions-item>
              <el-descriptions-item label="维生素E">
                {{ showValue(detail.vitamins.vitaminE, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="维生素B6">
                {{ showValue(detail.vitamins.vitaminB6, "mg") }}
              </el-descriptions-item>
              <el-descriptions-item label="维生素B12">
                {{ showValue(detail.vitamins.vitaminB12, "μg") }}
              </el-descriptions-item>
            </el-descriptions>
          </template>
          <el-empty v-else description="暂无维生素数据" />
        </el-tab-pane>

      </el-tabs>

      <!-- 返回 -->
      <div class="mt20">
        <el-button icon="el-icon-back" @click="goBack">返回</el-button>
      </div>
    </template>
  </div>
</template>

<script>
import { getFoodDetail } from "@/api/food/food"

/** 默认完整结构（核心） */
const DEFAULT_DETAIL = {
  foodName: "",
  foodGroup: "",
  servings: [],
  macro: {
    calories: null,
    protein: null,
    fat: null,
    carbohydrate: null,
    fiber: null,
    sugars: null
  },
  fattyAcids: {
    omega3: null,
    omega6: null,
    dha: null,
    epa: null,
    monoFat: null,
    polyFat: null
  },
  minerals: {
    calcium: null,
    iron: null,
    potassium: null,
    magnesium: null,
    sodium: null,
    zinc: null
  },
  vitamins: {
    vitaminARae: null,
    vitaminC: null,
    vitaminD: null,
    vitaminE: null,
    vitaminB6: null,
    vitaminB12: null
  }
}

export default {
  name: "FoodDetail",
  data() {
    return {
      loading: true,
      detail: JSON.parse(JSON.stringify(DEFAULT_DETAIL))
    }
  },
  watch: {
    "$route.query.id": {
      immediate: false,
      handler(newId, oldId) {
        if (newId && newId !== oldId) {
          this.fetchByRoute()
        }
      }
    }
  },
  created() {
    this.fetchByRoute()
  },
  methods: {
    fetchByRoute() {
      const id = this.$route.query.id
      if (!id) return
      this.detail = JSON.parse(JSON.stringify(DEFAULT_DETAIL))
      this.loading = true
      this.loadDetail(id)
    },
    loadDetail(id) {
      getFoodDetail(id).then(res => {
        const data = res.data || {}
        this.detail = {
          ...DEFAULT_DETAIL,
          ...data,
          macro: { ...DEFAULT_DETAIL.macro, ...data.macro },
          fattyAcids: { ...DEFAULT_DETAIL.fattyAcids, ...data.fattyAcids },
          minerals: { ...DEFAULT_DETAIL.minerals, ...data.minerals },
          vitamins: { ...DEFAULT_DETAIL.vitamins, ...data.vitamins }
        }
      }).finally(() => {
        this.loading = false
      })
    },
    showValue(val, unit = "") {
      if (val === null || val === undefined || val === "") {
        return "--"
      }
      return unit ? `${val} ${unit}` : val
    },
    hasData(obj) {
      return obj && Object.values(obj).some(v => v !== null && v !== undefined)
    },
    goBack() {
      this.$router.back()
    }
  }
}
</script>

<style scoped>
.food-header {
  display: flex;
  flex-direction: column;
}

.food-header .title {
  font-size: 20px;
  font-weight: 600;
}

.food-header .sub {
  margin-top: 6px;
  color: #909399;
}

.mt20 {
  margin-top: 20px;
}
</style>
