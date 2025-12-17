<template>
  <div class="app-container">
    <h2>动作库参考</h2>
    <el-card class="action-library-card">
      <el-input v-model="searchAction" placeholder="搜索动作名称" style="margin-bottom: 20px" />
      <el-table :data="filteredActions" border style="width: 100%">
        <el-table-column prop="exerciseName" label="动作名称" />
        <el-table-column prop="muscleGroup" label="目标肌群" width="120" />
        <el-table-column prop="intensityLevel" label="难度" width="80" />
        <el-table-column prop="metValue" label="MET值" width="80" />
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="selectAction(scope.row)">
              选择
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getList } from "@/api/workout/reference"; // 引入接口请求方法

export default {
  data() {
    return {
      searchAction: "",
      actionLibrary: [], // 存储从数据库获取的所有动作数据
    };
  },
  created() {
    this.loadActionLibrary(); // 页面加载时获取数据
  },
  computed: {
    // 根据搜索关键词筛选数据
    filteredActions() {
      if (!this.searchAction) return this.actionLibrary;
      return this.actionLibrary.filter(item =>
        item.exerciseName.includes(this.searchAction)
      );
    }
  },
  methods: {
    // 调用后端接口获取exercise_met_reference表数据
    async loadActionLibrary() {
      try {
        // 调用若依框架的/list接口，参数为空表示查询所有
        const response = await getList({});
        // 若依接口返回的列表数据在response.rows中
        this.actionLibrary = response.rows;
      } catch (error) {
        this.$message.error("获取动作库数据失败：" + (error.msg || error.message));
      }
    },
    // 选择动作（保持原逻辑）
    selectAction(row) {
      this.$emit("selectAction", row); // 传递选中的动作数据
      this.$router.go(-1); // 返回上一页
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
.action-library-card {
  margin-top: 20px;
}
</style>
