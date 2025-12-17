<template>
  <div class="app-container">
    <!-- 面包屑 -->
    <el-breadcrumb separator="/" style="margin-bottom: 20px">
      <el-breadcrumb-item><a href="/workout/logs">运动记录</a></el-breadcrumb-item>
      <el-breadcrumb-item><a href="/workout/details">运动动作详情</a></el-breadcrumb-item>
      <el-breadcrumb-item>组记录</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 新增组按钮 -->
    <el-button type="primary" icon="el-icon-plus" @click="openAddSetDialog" style="margin-bottom: 20px">
      新增组记录
    </el-button>

    <!-- 组记录列表 -->
    <el-table :data="setList" border style="width: 100%">
      <el-table-column prop="setNum" label="组号" width="150" />
      <el-table-column prop="weight" label="重量(kg)" width="150" />
      <el-table-column prop="actualReps" label="实际次数" width="150" />
      <el-table-column prop="rpe" label="RPE" width="150" />
      <el-table-column prop="notes" label="备注" />
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="editSet(scope.row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="deleteSet(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑组弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
      <el-form :model="setForm" label-width="100px">
        <el-form-item label="组号" prop="setNum">
          <el-input-number v-model="setForm.setNum" :min="1" />
        </el-form-item>
        <el-form-item label="重量(kg)" prop="weight">
          <el-input-number v-model="setForm.weight" :min="0" />
        </el-form-item>
        <el-form-item label="实际次数" prop="actualReps">
          <el-input-number v-model="setForm.actualReps" :min="1" />
        </el-form-item>
        <el-form-item label="RPE" prop="rpe">
          <el-select v-model="setForm.rpe" placeholder="请选择RPE">
            <el-option label="6" value="6" />
            <el-option label="7" value="7" />
            <el-option label="8" value="8" />
            <el-option label="9" value="9" />
            <el-option label="10" value="10" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="setForm.notes" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveSet">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      detailId: this.$route.query.detailId, // 接收动作ID
      setList: [], // 当前动作的组记录列表
      dialogVisible: false,
      dialogTitle: "新增组记录",
      setForm: {
        setId: "",
        detailId: this.$route.query.detailId,
        setNum: 1,
        weight: 0,
        actualReps: 12,
        rpe: "8",
        notes: ""
      }
    };
  },
  created() {
    this.loadSets();
  },
  methods: {
    // 加载当前动作的组记录
    loadSets() {
      const storedSets = localStorage.getItem("exercise_sets");
      const allSets = storedSets ? JSON.parse(storedSets) : [];
      // 统一detailId类型为字符串，避免类型不匹配导致过滤失败
      this.setList = allSets.filter(item => String(item.detailId) === String(this.detailId));
    },

    // 保存组记录到本地
    saveSets(sets) {
      localStorage.setItem("exercise_sets", JSON.stringify(sets));
    },

    // 打开新增组弹窗
    openAddSetDialog() {
      this.dialogTitle = "新增组记录";
      this.setForm = {
        setId: `set_${Date.now()}`,
        detailId: this.detailId,
        setNum: this.setList.length + 1,
        weight: 0,
        actualReps: 12,
        rpe: "8",
        notes: ""
      };
      this.dialogVisible = true;
    },

    // 编辑组记录
    editSet(row) {
      this.dialogTitle = "编辑组记录";
      this.setForm = { ...row };
      this.dialogVisible = true;
    },

    // 保存组记录
    saveSet() {
      const storedSets = localStorage.getItem("exercise_sets");
      const allSets = storedSets ? JSON.parse(storedSets) : [];

      // 判断是新增还是编辑
      const index = allSets.findIndex(item => item.setId === this.setForm.setId);
      if (index > -1) {
        allSets[index] = this.setForm;
      } else {
        allSets.push(this.setForm);
      }

      this.saveSets(allSets);
      this.loadSets();
      this.dialogVisible = false;
      this.$message.success("保存成功");

      // 新增：计算并更新热量消耗
      this.calculateAndUpdateCalories();
    },

    // 计算并更新热量的方法
    async calculateAndUpdateCalories() {
      try {
        // 1. 获取当前动作详情，拿到所属的logId
        const storedExercises = localStorage.getItem("workout_exercise_details");
        const allExercises = storedExercises ? JSON.parse(storedExercises) : [];
        const currentExercise = allExercises.find(item => String(item.detailId) === String(this.detailId));
        if (!currentExercise) {
          this.$message.warning("未找到关联的动作详情");
          return;
        }

        // 2. 关键：获取当前logId下的所有动作详情（多个details）
        const logId = Number(currentExercise.logId); // 统一为数字类型
        const logExercises = allExercises.filter(item => Number(item.logId) === logId); // 该日志下的所有details
        console.log(`当前日志${logId}下的动作详情数量：`, logExercises.length); // 调试日志
        if (logExercises.length === 0) {
          this.$message.warning("未找到该日志下的任何动作详情");
          return;
        }

        // 3. 获取所有组记录（用于匹配每个detail的sets）
        const storedSets = localStorage.getItem("exercise_sets");
        const allSets = storedSets ? JSON.parse(storedSets) : [];

        // 4. 初始化总热量和总时长（必须初始化为0，确保累加正确）
        let totalLogCalories = 0; // 整个日志的总热量
        let totalLogMinutes = 0; // 整个日志的总时长

        // 5. 遍历该日志下的每个detail，计算每个detail的热量并累加（重点：不跳过任何detail）
        logExercises.forEach((detail, index) => {
          console.log(`正在计算第${index+1}个动作详情（detailId：${detail.detailId}）的热量`); // 调试日志
          // 5.1 获取当前detail对应的所有sets（多个sets）
          const exerciseSets = allSets.filter(item => String(item.detailId) === String(detail.detailId));
          console.log(`动作详情${detail.detailId}对应的sets数量：`, exerciseSets.length); // 调试日志

          // 5.2 计算当前detail的热量（即使没有sets，也给基础热量，不跳过）
          // 体重（兜底60kg）
          const userWeight = Number(this.$store.state.user?.weight) || 60;
          // MET值（从detail获取，兜底5）
          const metValue = Number(detail.met) || 5;
          // 时长：每组默认5分钟，无sets则按1组计算（避免时长为0）
          const perSetMinutes = 5;
          const setCount = exerciseSets.length || 1; // 无sets则默认1组
          const detailMinutes = setCount * perSetMinutes;
          const timeHour = detailMinutes / 60;

          // 力量运动消耗：总重量（weight×actualReps）×系数0.1；无sets则总重量为0
          const totalWeight = exerciseSets.reduce((sum, set) => {
            const weight = Number(set.weight) || 0;
            const reps = Number(set.actualReps) || 0;
            return sum + (weight * reps);
          }, 0);
          const strengthCalories = totalWeight * 0.1;

          // 当前detail的热量（即使计算结果为0，也兜底5kcal，确保有基础热量）
          const detailCalories = Math.round(metValue * userWeight * timeHour + strengthCalories) || 5;
          console.log(`动作详情${detail.detailId}的热量：`, detailCalories); // 调试日志

          // 5.3 累加到日志的总热量和总时长（关键：不管有没有sets，都累加）
          totalLogCalories += detailCalories;
          totalLogMinutes += detailMinutes;
        });

        // 6. 最终兜底：若总热量仍为0（极端情况），设置最小热量为5kcal
        totalLogCalories = totalLogCalories || 5;
        console.log(`日志${logId}的总热量：`, totalLogCalories); // 调试日志

        // 7. 更新日志的热量和时长
        const storedLogs = localStorage.getItem("workout_logs");
        let allLogs = storedLogs ? JSON.parse(storedLogs) : [];
        const logIndex = allLogs.findIndex(item => Number(item.logId) === logId);
        if (logIndex === -1) {
          this.$message.warning("未找到关联的运动日志");
          return;
        }

        // 7.1 更新日志的总热量和总时长
        allLogs[logIndex].totalCalories = totalLogCalories;
        allLogs[logIndex].totalDuration = totalLogMinutes;
        // 保存到localStorage
        localStorage.setItem("workout_logs", JSON.stringify(allLogs));

        // 8. 触发事件总线，通知logs页面刷新数据
        this.$bus.$emit("logsDataUpdated");

        this.$message.success(`热量已同步更新，总热量：${totalLogCalories}kcal（共${logExercises.length}个动作详情）`);
      } catch (error) {
        console.error("热量计算失败：", error);
        this.$message.error("热量更新失败，请重试");
      }
    },

    // 删除组记录
    deleteSet(row) {
      this.$confirm("确定删除该组记录吗？", "提示", { type: "warning" })
        .then(() => {
          const storedSets = localStorage.getItem("exercise_sets");
          const allSets = storedSets ? JSON.parse(storedSets) : [];
          const newSets = allSets.filter(item => item.setId !== row.setId);
          this.saveSets(newSets);
          this.loadSets();
          this.$message.success("删除成功");

          // 新增：删除后重新计算热量
          this.calculateAndUpdateCalories();
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
