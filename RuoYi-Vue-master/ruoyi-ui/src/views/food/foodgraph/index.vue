<template>
  <!-- 整个页面只保留图谱 -->
  <div class="food-graph-container">
    <div ref="foodGraph" class="graph"></div>
  </div>
</template>

<script>
import * as echarts from "echarts"
import { getFoodGroupStats } from "@/api/food/food"

export default {
  name: "FoodGraph",
  data() {
    return {
      chart: null,
      // 后端返回的数据结构：
      // [{ foodGroup: '谷物', count: 12 }, ...]
      foodList: []
    }
  },
  mounted() {
    this.loadFoodData()
    window.addEventListener("resize", this.resizeChart)
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizeChart)
    if (this.chart) {
      this.chart.dispose()
    }
  },
  methods: {
    /** ================== 加载食物分类统计数据（数据库聚合） ================== */
    loadFoodData() {
      getFoodGroupStats().then(res => {
        this.foodList = res.data || []
        this.$nextTick(() => {
          this.initFoodGraph()
        })
      })
    },

    /** ================== 构建 Graph 数据 ================== */
    buildGraphData() {
      const nodes = [
        {
          name: "食物",
          category: 0,
          symbolSize: 90,
          draggable: false
        }
      ]

      const links = []

      this.foodList.forEach(item => {
        const group = item.foodGroup || "未分类"
        const count = item.count || 0

        nodes.push({
          name: `${group}（${count}）`,
          rawName: group, // 原始分类名（用于跳转）
          value: count,
          category: 1,
          symbolSize: Math.min(65, 40 + Math.sqrt(count) * 6),
          draggable: true
        })

        links.push({
          source: "食物",
          target: `${group}（${count}）`,
          value: "分类"
        })
      })

      return { nodes, links }
    },

    /** ================== 初始化力导向图 ================== */
    initFoodGraph() {
      if (!this.chart) {
        this.chart = echarts.init(this.$refs.foodGraph)
      }

      const { nodes, links } = this.buildGraphData()

      const option = {
        title: {
          text: "食物分类知识图谱",
          left: "center",
          top: 20
        },
        tooltip: {
          formatter: params => {
            if (params.data.category === 1) {
              return `${params.data.rawName}<br/>数量：${params.data.value}`
            }
            return params.name
          }
        },
        series: [
          {
            type: "graph",
            layout: "force",
            roam: true,
            force: {
              repulsion: 500,
              edgeLength: 150,
              gravity: 0.1
            },
            categories: [
              { name: "根节点" },
              { name: "食物分类" }
            ],
            data: nodes,
            links: links,
            label: {
              show: true,
              formatter: "{b}",
              fontSize: 14
            },
            edgeLabel: {
              show: true,
              formatter: "{c}"
            }
          }
        ]
      }

      this.chart.setOption(option)

      // 分类节点点击跳转
      this.chart.off("click")
      this.chart.on("click", params => {
        if (params.data && params.data.category === 1) {
          this.handleCategoryClick(params.data.rawName)
        }
      })
    },

    /** ================== 分类点击跳转 ================== */
    handleCategoryClick(foodGroup) {
      this.$router.push({
        path: "/food/macro",
        query: { foodGroup }
      })
    },

    resizeChart() {
      if (this.chart) {
        this.chart.resize()
      }
    }
  }
}
</script>

<style scoped>
.food-graph-container {
  width: 100%;
  height: calc(100vh - 84px); /* 若依顶部导航高度 */
}

.graph {
  width: 100%;
  height: 100%;
}
</style>
