<template>
  <div class="app-container">
    <!-- 面包屑 -->
    <el-breadcrumb separator="/" style="margin-bottom: 20px">
      <el-breadcrumb-item><a href="/workout/logs">训练记录</a></el-breadcrumb-item>
      <el-breadcrumb-item>训练动作详情</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 新增动作按钮 -->
    <el-button type="primary" icon="el-icon-plus" @click="openAddExerciseDialog" style="margin-bottom: 20px">
      新增训练动作
    </el-button>

    <!-- 训练动作列表 -->
    <el-table :data="exerciseList" border style="width: 100%">
      <el-table-column prop="exerciseName" label="训练动作" />
      <el-table-column prop="muscleGroup" label="目标肌群" width="120" />
      <el-table-column prop="equipment" label="使用器械" width="120" />
      <el-table-column prop="difficulty" label="难度" width="80" />
      <el-table-column prop="targetSets" label="目标组数" width="100" />
      <el-table-column prop="targetReps" label="目标次数" width="100" />
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="goToSets(scope.row.detailId)">
            组记录
          </el-button>
          <el-button size="mini" type="success" @click="editExercise(scope.row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="deleteExercise(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑动作弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px">
      <el-form :model="exerciseForm" label-width="120px">
        <el-form-item label="训练动作" prop="exerciseName">
          <el-input v-model="exerciseForm.exerciseName" placeholder="请输入动作名称" />
        </el-form-item>
        <el-form-item label="目标肌群" prop="muscleGroup">
          <el-input v-model="exerciseForm.muscleGroup" placeholder="请输入目标肌群" />
        </el-form-item>
        <el-form-item label="使用器械" prop="equipment">
          <el-input v-model="exerciseForm.equipment" placeholder="请输入使用器械" />
        </el-form-item>
        <el-form-item label="难度等级" prop="difficulty">
          <el-select v-model="exerciseForm.difficulty" placeholder="请选择难度">
            <el-option label="低" value="低" />
            <el-option label="中" value="中" />
            <el-option label="高" value="高" />
          </el-select>
        </el-form-item>
        <el-form-item label="目标组数" prop="targetSets">
          <el-input-number v-model="exerciseForm.targetSets" :min="1" />
        </el-form-item>
        <el-form-item label="目标次数" prop="targetReps">
          <el-input-number v-model="exerciseForm.targetReps" :min="1" />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveExercise">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      logId: this.$route.query.logId, // 接收训练记录ID
      exerciseList: [], // 当前训练记录的动作列表
      dialogVisible: false,
      dialogTitle: "新增训练动作",
      exerciseForm: {
        detailId: "",
        logId: this.$route.query.logId,
        exerciseName: "",
        muscleGroup: "",
        equipment: "",
        difficulty: "中",
        targetSets: 3,
        targetReps: 12
      }
    };
  },
  created() {
    this.loadExercises();
  },
  methods: {
    // 加载当前训练记录的动作
    loadExercises() {
      const storedExercises = localStorage.getItem("workout_exercise_details");
      const allExercises = storedExercises ? JSON.parse(storedExercises) : [];
      this.exerciseList = allExercises.filter(item => item.logId === this.logId);
    },

    // 保存动作到本地
    saveExercises(exercises) {
      localStorage.setItem("workout_exercise_details", JSON.stringify(exercises));
    },

    // 打开新增动作弹窗
    openAddExerciseDialog() {
      this.dialogTitle = "新增训练动作";
      this.exerciseForm = {
        detailId: `ex_${Date.now()}`,
        logId: this.logId,
        exerciseName: "",
        muscleGroup: "",
        equipment: "",
        difficulty: "中",
        targetSets: 3,
        targetReps: 12
      };
      this.dialogVisible = true;
    },

    // 编辑动作
    editExercise(row) {
      this.dialogTitle = "编辑训练动作";
      this.exerciseForm = { ...row };
      this.dialogVisible = true;
    },

    // 保存动作
    saveExercise() {
      const storedExercises = localStorage.getItem("workout_exercise_details");
      const allExercises = storedExercises ? JSON.parse(storedExercises) : [];

      // 判断是新增还是编辑
      const index = allExercises.findIndex(item => item.detailId === this.exerciseForm.detailId);
      if (index > -1) {
        allExercises[index] = this.exerciseForm;
      } else {
        allExercises.push(this.exerciseForm);
      }

      this.saveExercises(allExercises);
      this.loadExercises();
      this.dialogVisible = false;
      this.$message.success("保存成功");
    },

    // 删除动作
    deleteExercise(row) {
      this.$confirm("确定删除该动作吗？", "提示", { type: "warning" })
        .then(() => {
          const storedExercises = localStorage.getItem("workout_exercise_details");
          const allExercises = storedExercises ? JSON.parse(storedExercises) : [];
          const newExercises = allExercises.filter(item => item.detailId !== row.detailId);
          this.saveExercises(newExercises);
          this.loadExercises();
          this.$message.success("删除成功");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    // 跳转到组记录页
    goToSets(detailId) {
      this.$router.push({
        path: "/workout/sets",
        query: { detailId }
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
