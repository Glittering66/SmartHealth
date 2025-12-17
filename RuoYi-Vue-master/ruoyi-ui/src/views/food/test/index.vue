<template>
  <!-- 整个页面只保留图谱 -->
  <div class="food-graph-container">
    <div ref="foodGraph" class="graph"></div>
  </div>
</template>

<script>
import * as echarts from "echarts"
import { listFood } from "@/api/food/food"

export default {
  name: "FoodGraph",
  data() {
    return {
      chart: null,
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
    /** ================== 加载食物数据 ================== */
    loadFoodData() {
      listFood({ pageNum: 1, pageSize: 9999 }).then(res => {
        this.foodList = res.rows
        this.$nextTick(() => {
          this.initFoodGraph()
        })
      })
    },

    /** ================== 统计食物分类 ================== */
    buildFoodCategoryStats() {
      const stats = {}

      this.foodList.forEach(item => {
        const group = item.foodGroup || "未分类"
        stats[group] = (stats[group] || 0) + 1
      })

      return stats
    },

    /** ================== 构建 Graph 数据 ================== */
    buildGraphData() {
      const categoryStats = this.buildFoodCategoryStats()

      const nodes = [
        {
          name: "食物",
          category: 0,
          symbolSize: 90,
          draggable: false
        }
      ]

      const links = []

      Object.keys(categoryStats).forEach(group => {
        const count = categoryStats[group]

        nodes.push({
          name: `${group}（${count}）`,
          rawName: group,            // ⭐ 原始分类名（用于跳转）
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

      // ⭐ 点击二级节点预留接口
      this.chart.off("click")
      this.chart.on("click", params => {
        if (params.data.category === 1) {
          this.handleCategoryClick(params.data.rawName)
        }
      })
    },

    /** ================== 分类点击跳转接口 ================== */
    handleCategoryClick(foodGroup) {
      // 👉 你可以在这里自由扩展
      // 1️⃣ 跳转分类详情页
      this.$router.push({
        path: "/food/food",
        query: { foodGroup }
      })

      // 2️⃣ 或跳转统计页面
      // this.$router.push(`/food/stat/${foodGroup}`)
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
