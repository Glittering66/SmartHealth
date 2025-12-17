<template>
  <div class="app-container">
    <h2>运动动作库参考</h2>
    <el-card class="action-library-card">
      <el-input v-model="searchAction" placeholder="搜索运动动作名称" style="margin-bottom: 20px" />
      <el-table :data="filteredActions" border style="width: 100%">
        <el-table-column prop="exerciseName" label="运动动作名称" />
        <el-table-column prop="muscleGroup" label="目标肌群" width="150" />
        <el-table-column prop="intensityLevel" label="难度" width="150" />
        <el-table-column prop="metValue" label="MET值" width="150" />
        <el-table-column prop="description" label="描述" min-width="200" />
        <!-- 移除：选择按钮的操作列 -->
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getList } from "@/api/workout/reference";

export default {
  data() {
    return {
      searchAction: "",
      actionLibrary: []
      // 移除：与跳转相关的变量
      // from: this.$route.query.from,
      // logId: this.$route.query.logId
    };
  },
  created() {
    this.loadActionLibrary();
  },
  computed: {
    filteredActions() {
      if (!this.searchAction) return this.actionLibrary;
      return this.actionLibrary.filter(item =>
        item.exerciseName.includes(this.searchAction)
      );
    }
  },
  methods: {
    async loadActionLibrary() {
      try {
        const response = await getList({});
        this.actionLibrary = response.rows;
      } catch (error) {
        this.$message.error("获取动作库数据失败：" + (error.msg || error.message));
      }
    }
    // 移除：选择按钮的点击事件方法
    // selectAction(row) { ... }
  }
};
</script>
