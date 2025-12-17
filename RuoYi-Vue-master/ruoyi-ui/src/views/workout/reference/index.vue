<template>
  <div class="app-container">
    <h2>动作库参考</h2>
    <el-card class="action-library-card">
      <el-input v-model="searchAction" placeholder="搜索动作名称" style="margin-bottom: 20px" />
      <el-table :data="filteredActions" border style="width: 100%">
        <el-table-column prop="name" label="动作名称" />
        <el-table-column prop="muscleGroup" label="目标肌群" width="120" />
        <el-table-column prop="equipment" label="使用器械" width="120" />
        <el-table-column prop="difficulty" label="难度" width="80" />
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
export default {
  data() {
    return {
      searchAction: "",
      // 预设动作库数据
      actionLibrary: [
        { name: "杠铃卧推", muscleGroup: "胸肌", equipment: "杠铃", difficulty: "中" },
        { name: "哑铃深蹲", muscleGroup: "大腿", equipment: "哑铃", difficulty: "高" },
        { name: "引体向上", muscleGroup: "背肌", equipment: "单杠", difficulty: "高" },
        { name: "哑铃弯举", muscleGroup: "二头肌", equipment: "哑铃", difficulty: "低" },
        { name: "绳索下拉", muscleGroup: "三头肌", equipment: "绳索", difficulty: "中" }
      ]
    };
  },
  computed: {
    // 筛选动作
    filteredActions() {
      if (!this.searchAction) return this.actionLibrary;
      return this.actionLibrary.filter(item =>
        item.name.includes(this.searchAction)
      );
    }
  },
  methods: {
    // 选择动作（传递到训练动作详情页）
    selectAction(row) {
      // 通过Vuex/EventBus传递，或返回上一页填充
      this.$emit("selectAction", row);
      this.$router.go(-1);
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
