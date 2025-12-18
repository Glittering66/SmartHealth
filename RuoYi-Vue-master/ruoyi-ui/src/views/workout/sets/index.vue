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
      <el-table-column prop="setNumber" label="组号" width="150" />
      <el-table-column prop="weightKg" label="重量(kg)" width="150" />
      <el-table-column prop="completedReps" label="实际次数" width="150" />
      <el-table-column prop="rpeLevel" label="RPE" width="150" />
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
        <el-form-item label="组号">
          <el-input-number v-model="setForm.setNumber" :min="1" />
        </el-form-item>

        <el-form-item label="重量(kg)">
          <el-input-number v-model="setForm.weightKg" :min="0" />
        </el-form-item>

        <el-form-item label="实际次数">
          <el-input-number v-model="setForm.completedReps" :min="1" />
        </el-form-item>

        <el-form-item label="RPE">
          <el-select v-model="setForm.rpeLevel">
            <el-option label="6" :value="6" />
            <el-option label="7" :value="7" />
            <el-option label="8" :value="8" />
            <el-option label="9" :value="9" />
            <el-option label="10" :value="10" />
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
import {
  listSets,
  addSet,
  updateSet,
  deleteSetById
} from '@/api/workout/sets'

export default {
  data() {
    return {
      // 仅保留有效detailId：必须是大于0的数字，否则为null
      detailId: (() => {
        const id = Number(this.$route.query.detailId);
        return (id && id > 0) ? id : null;
      })(),
      setList: [], // 当前动作的组记录列表
      dialogVisible: false,
      dialogTitle: "新增组记录",
      setForm: {
        setId: null,
        detailId: this.$route.query.detailId,
        setNumber: 1,
        weightKg: 0,
        completedReps: 12,
        rpeLevel: 8,
        setDurationSeconds: null,
        notes: '',
        caloriesBurned: null
      }
    };
  },
  created() {
    // 核心：校验detailId有效性，无效则跳回Details界面
    if (!this.detailId) {
      this.$message.warning("请从运动动作详情页面进入组记录");
      // 跳回Details界面（若需要携带logId，可补充：`/workout/details?logId=${xxx}`，后续可优化）
      this.$router.push("/workout/details");
      return; // 终止后续所有逻辑
    }

    // 只有detailId有效时，才加载组记录数据
    this.loadSets();
  },
  methods: {
    // 加载当前动作的组记录
    async loadSets() {
      // 二次校验：若detailId无效，直接清空数据
      if (!this.detailId) {
        this.setList = [];
        return;
      }

      try {
        const res = await listSets(this.detailId);
        this.setList = res.rows || [];
      } catch (error) {
        this.$message.error("加载组记录列表失败：" + (error.message || "未知错误"));
        this.setList = [];
        console.error("加载失败详情：", error);
      }
    },

    // 保存组记录到本地
    saveSets(sets) {
      localStorage.setItem("exercise_sets", JSON.stringify(sets));
    },

    // 打开新增组弹窗
    openAddSetDialog() {
      this.dialogTitle = '新增组记录'
      this.setForm = {
        setId: null,
        detailId: this.detailId,
        setNumber: this.setList.length + 1, // ✅ 必传
        weightKg: 0,
        completedReps: 12,
        rpeLevel: 8,
        setDurationSeconds: null,
        notes: ''
      }
      this.dialogVisible = true
    },

    // 编辑组记录
    editSet(row) {
      this.dialogTitle = '编辑组记录'
      this.setForm = { ...row }   // setId 已存在
      this.dialogVisible = true
    },

    // 保存组记录
    async saveSet() {
      if (this.setForm.setId) {
        // 编辑
        await updateSet(this.setForm)
        this.$message.success('修改成功')
      } else {
        // 新增
        await addSet(this.setForm)

        this.$message.success('新增成功')
      }

      this.dialogVisible = false
      this.loadSets()

      // 如需同步热量
      this.calculateAndUpdateCalories()
    },

    // 计算并更新热量的方法
    // 替换原有的 calculateAndUpdateCalories 方法（核心修改：从后端接口取数）
    async calculateAndUpdateCalories() {
      // try {
      //   // ========== 1. 新增：调用后端接口，获取当前detailId对应的详情（含logId） ==========
      //   // 需封装后端接口：根据detailId查询单个动作详情（返回含logId的对象）
      //   const detailRes = await getDetailById(this.detailId);
      //   const currentExercise = detailRes.data; // 后端返回的当前动作详情（含logId）
      //   if (!currentExercise) {
      //     this.$message.warning("未找到关联的动作详情");
      //     return;
      //   }
      //   const logId = currentExercise.logId || currentExercise.log_id; // 兼容驼峰/下划线
      //
      //   // ========== 2. 新增：调用后端接口，获取当前logId下的所有动作详情 ==========
      //   // 需封装后端接口：根据logId查询所有动作详情
      //   const detailsRes = await listDetailsByLogId(logId);
      //   const logExercises = detailsRes.rows || []; // 该日志下的所有details（后端数据）
      //   if (logExercises.length === 0) {
      //     this.$message.warning("未找到该日志下的任何动作详情");
      //     return;
      //   }
      //
      //   // ========== 3. 新增：调用后端接口，获取所有组记录（或当前logId下的所有sets） ==========
      //   // 需封装后端接口：根据logId查询所有组记录
      //   const setsRes = await listSetsByLogId(logId);
      //   const allSets = setsRes.rows || []; // 后端的组记录数据
      //
      //   // ========== 后续逻辑不变（仅将原LocalStorage的变量替换为后端接口返回的数据） ==========
      //   let totalLogCalories = 0;
      //   let totalLogMinutes = 0;
      //
      //   logExercises.forEach((detail, index) => {
      //     // 注意：后端返回的字段可能是下划线（detail_id），需转为驼峰或直接用下划线匹配
      //     const detailId = detail.detailId || detail.detail_id;
      //     const exerciseSets = allSets.filter(item => (item.detailId || item.detail_id) === detailId);
      //
      //     // 体重、MET值从后端detail中获取（替换原LocalStorage的字段）
      //     const userWeight = Number(this.$store.state.user?.weight) || 60;
      //     const metValue = Number(detail.metValue || detail.met_value) || 5;
      //
      //     // 原有热量计算逻辑不变...
      //     const perSetMinutes = 5;
      //     const setCount = exerciseSets.length || 1;
      //     const detailMinutes = setCount * perSetMinutes;
      //     const timeHour = detailMinutes / 60;
      //
      //     const totalWeight = exerciseSets.reduce((sum, set) => {
      //       const weight = Number(set.weightKg || set.weight_kg) || 0;
      //       const reps = Number(set.completedReps || set.completed_reps) || 0;
      //       return sum + (weight * reps);
      //     }, 0);
      //     const strengthCalories = totalWeight * 0.1;
      //
      //     const detailCalories = Math.round(metValue * userWeight * timeHour + strengthCalories) || 5;
      //     totalLogCalories += detailCalories;
      //     totalLogMinutes += detailMinutes;
      //   });
      //
      //   // ========== 4. 替换：更新日志的热量和时长（调用后端接口，而非LocalStorage） ==========
      //   // 需封装后端接口：更新日志的总热量和总时长
      //   await updateLogCaloriesAndDuration({
      //     logId: logId,
      //     totalCalories: totalLogCalories || 5,
      //     totalDuration: totalLogMinutes
      //   });
      //
      //   // 触发事件总线，通知logs页面刷新数据
      //   this.$bus.$emit("logsDataUpdated");
      //   this.$message.success(`热量已同步更新，总热量：${totalLogCalories || 5}kcal（共${logExercises.length}个动作详情）`);
      //
      // } catch (error) {
      //   console.error("热量计算失败：", error);
      //   this.$message.error("热量更新失败，请重试");
      // }
    },

    // 删除组记录
    async deleteSet(row) {
      await this.$confirm('确定删除该组记录吗？', '提示', { type: 'warning' })
      await deleteSetById(row.setId)
      this.$message.success('删除成功')
      this.loadSets()
      this.calculateAndUpdateCalories()
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
