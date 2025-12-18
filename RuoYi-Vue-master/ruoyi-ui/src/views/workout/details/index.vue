<template>
  <div class="app-container">
    <!-- 面包屑 -->
    <el-breadcrumb separator="/" style="margin-bottom: 20px">
      <el-breadcrumb-item><a href="/workout/logs">运动记录</a></el-breadcrumb-item>
      <el-breadcrumb-item>运动动作详情</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 操作按钮组 -->
    <div style="margin-bottom: 20px">
      <el-button type="primary" icon="el-icon-plus" @click="openAddExerciseDialog">
        新增运动动作
      </el-button>
    </div>

    <!-- 运动动作列表 -->
    <el-table :data="exerciseList" border style="width: 100%">
      <el-table-column prop="exerciseName" label="运动动作" />
      <el-table-column prop="muscleGroup" label="目标肌群" width="150" />
      <el-table-column prop="equipment" label="使用器械" width="150" />
      <el-table-column prop="difficulty" label="难度" width="150" />
      <el-table-column prop="targetSets" label="目标组数" width="150" />
      <el-table-column prop="targetReps" label="目标次数" width="150" />
      <el-table-column label="操作" width="300">
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
      <el-form :model="exerciseForm" :rules="exerciseRules" ref="exerciseFormRef" label-width="120px">
        <el-form-item label="运动动作" prop="exerciseName">
          <!-- 下拉选择器：显示reference中的动作名称 -->
          <el-select
            v-model="exerciseForm.exerciseName"
            placeholder="请选择运动动作（参考动作库）"
            filterable
            style="width: 100%"
            @change="onExerciseNameChange"
            v-loading="selectLoading"
          >
            <!-- 修正：用闭合标签，避免Vue2解析问题 -->
            <el-option
              v-for="item in exerciseOptions"
              :key="item.exerciseName + item.metValue"
            :label="item.exerciseName"
            :value="item.exerciseName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="目标肌群" prop="muscleGroup">
          <el-input v-model="exerciseForm.muscleGroup" placeholder="请输入目标肌群" />
        </el-form-item>
        <el-form-item label="使用器械" prop="equipment">
          <el-input v-model="exerciseForm.equipment" placeholder="请输入使用器械" />
        </el-form-item>
        <el-form-item label="难度" prop="difficulty">
          <el-select v-model="exerciseForm.difficulty" placeholder="请选择难度">
            <el-option label="低" value="低" />
            <el-option label="中" value="中" />
            <el-option label="高" value="高" />
          </el-select>
        </el-form-item>
        <el-form-item label="目标组数" prop="targetSets">
          <el-input-number v-model="exerciseForm.targetSets" :min="1" placeholder="请输入目标组数" />
        </el-form-item>
        <el-form-item label="目标次数" prop="targetReps">
          <el-input-number v-model="exerciseForm.targetReps" :min="1" placeholder="请输入目标次数" />
        </el-form-item>
        <el-form-item label="MET值" prop="met" hidden>
          <el-input v-model="exerciseForm.met" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveExercise">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 步骤1：引入reference页面的API方法（统一数据来源）
import { getList } from "@/api/workout/reference";
import {
  listDetails,
  addDetails,
  updateDetails,
  deleteDetailCascade
} from '@/api/workout/details'
// 移除原来的axios直接引入（如果不需要的话）
// import axios from 'axios'

export default {
  data() {
    return {
      // 仅保留有效logId：必须是大于0的数字，否则为null
      logId: (() => {
        const id = Number(this.$route.query.logId);
        return (id && id > 0) ? id : null;
      })(),
      exerciseList: [],
      dialogVisible: false,
      dialogTitle: "新增运动动作",
      exerciseOptions: [], // 存储reference页面的动作库数据
      selectLoading: false, // 下拉框加载状态
      exerciseForm: {
        detailId: "",
        logId: this.$route.query.logId,
        exerciseName: "", // 绑定reference的动作名称
        muscleGroup: "",
        equipment: "",
        difficulty: "中",
        targetSets: 3,
        targetReps: 12,
        met: 0
      },
      exerciseRules: {
        exerciseName: [{ required: true, message: "请选择运动动作（参考动作库）", trigger: "change" }],
        muscleGroup: [{ required: true, message: "请输入目标肌群", trigger: "blur" }],
        targetSets: [{ required: true, message: "请输入目标组数", trigger: "blur" }],
        targetReps: [{ required: true, message: "请输入目标次数", trigger: "blur" }]
      }
    };
  },
  created() {
    if (!this.logId) {
      this.$message.warning("请从运动记录页面进入详情");
      this.$router.push("/workout/logs"); // 跳回logs路由
      return; // 终止后续所有逻辑
    }
    this.loadExercises();
    this.loadExerciseMetReference(); // 加载reference的动作库数据
  },
  methods: {
    async loadExercises() {
      // 若logId突然失效（如用户手动修改URL），直接返回
      if (!this.logId) {
        this.exerciseList = [];
        return;
      }

      try {
        const res = await listDetails(this.logId);
        this.exerciseList = res.rows || [];
      } catch (error) {
        this.$message.error("加载运动动作列表失败：" + (error.message || "未知错误"));
        this.exerciseList = [];
        console.error("加载失败详情：", error);
      }
    },

    // 步骤2：修改为调用reference的API方法（和reference页面用同一接口）
    async loadExerciseMetReference() {
      this.selectLoading = true;
      try {
        const response = await getList({});
        this.exerciseOptions = (response.rows || []).map(item => {
          const newItem = {
            exerciseName: item.exercise_name || item.exerciseName || '', // 确保有默认值
            muscleGroup: item.muscle_group || item.muscleGroup || '',
            equipment: item.equipment || '',
            metValue: item.met_value || item.metValue || 0,
            intensityLevel: item.intensity_level || item.intensityLevel || ''
          };
          // 打印每个item的exerciseName具体值
          console.log("单个动作数据：", newItem.exerciseName);
          return newItem;
        });
        console.log("处理后的完整数组：", this.exerciseOptions);
      } catch (error) {
        this.$message.error("获取动作库数据失败：" + error.message);
        this.exerciseOptions = [];
      } finally {
        this.selectLoading = false;
      }
    },

    // 加载当前运动记录的动作（原有逻辑不变）
    // loadExercises() {
    //   const storedExercises = localStorage.getItem("workout_exercise_details");
    //   const allExercises = storedExercises ? JSON.parse(storedExercises) : [];
    //   this.exerciseList = allExercises.filter(item => item.logId === this.logId);
    // },

    // 保存动作到本地（原有逻辑不变）
    async saveExercise() {
      this.$refs.exerciseFormRef.validate(async valid => {
        if (!valid) return

        const data = {
          detailId: this.exerciseForm.detailId,
          logId: this.logId,
          exerciseName: this.exerciseForm.exerciseName,
          muscleGroup: this.exerciseForm.muscleGroup,
          equipment: this.exerciseForm.equipment,
          difficulty: this.exerciseForm.difficulty,
          targetSets: this.exerciseForm.targetSets,
          targetReps: this.exerciseForm.targetReps,
          met: this.exerciseForm.met
        }

        if (data.detailId) {
          await updateDetails(data)
          this.$message.success('修改成功')
        } else {
          await addDetails(data)
          this.$message.success('新增成功')
        }

        this.dialogVisible = false
        this.loadExercises()
      })
    },

    // 打开新增动作弹窗（原有逻辑不变）
    openAddExerciseDialog() {
      this.dialogTitle = "新增运动动作";
      this.exerciseForm = {
        detailId: null,     // ⭐ 关键
        logId: this.logId,
        exerciseName: "",
        muscleGroup: "",
        equipment: "",
        difficulty: "中",
        targetSets: 3,
        targetReps: 12,
        met: 0
      };
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.$refs.exerciseFormRef.resetFields();
      });
    },

    // 编辑动作（原有逻辑不变）
    editExercise(row) {
      this.dialogTitle = "编辑运动动作";
      this.exerciseForm = {...row};
      this.dialogVisible = true;
    },

    // 删除动作（原有逻辑不变）
    async deleteExercise(row) {
      await this.$confirm(
        '确认删除该动作及其所有组记录吗？',
        '提示',
        { type: 'warning' }
      )

      await deleteDetailCascade(row.detailId)

      this.$message.success('删除成功')
      this.loadExercises()
    },

    // 跳转到组记录页（原有逻辑不变）
    goToSets(detailId) {
      this.$router.push({
        path: "/workout/sets",
        query: {detailId}
      });
    },

    // 步骤3：选择动作后自动填充关联数据（和reference的字段匹配）
    onExerciseNameChange() {
      if (!this.exerciseForm.exerciseName) return;
      // 从reference的动作库数据中匹配选中的动作名称
      const currentExercise = this.exerciseOptions.find(
        item => item.exerciseName === this.exerciseForm.exerciseName
      );
      if (currentExercise) {
        // 自动填充reference中的关联数据（字段和reference一致）
        this.exerciseForm.muscleGroup = currentExercise.muscleGroup || "";
        this.exerciseForm.equipment = currentExercise.equipment || "";
        this.exerciseForm.met = currentExercise.metValue || 0;
        // 可选：如果reference有难度字段，也可以自动填充
        // this.exerciseForm.difficulty = currentExercise.intensityLevel || "中";
      }
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
