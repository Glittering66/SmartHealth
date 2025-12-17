<template>
  <div class="app-container">
    <!-- 面包屑 -->
    <el-breadcrumb separator="/" style="margin-bottom: 20px">
      <el-breadcrumb-item><a href="/workout/logs">训练记录</a></el-breadcrumb-item>
      <el-breadcrumb-item><a href="/workout/details">训练动作详情</a></el-breadcrumb-item>
      <el-breadcrumb-item>组记录</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 新增组按钮 -->
    <el-button type="primary" icon="el-icon-plus" @click="openAddSetDialog" style="margin-bottom: 20px">
      新增组记录
    </el-button>

    <!-- 组记录列表 -->
    <el-table :data="setList" border style="width: 100%">
      <el-table-column prop="setNum" label="组号" width="80" />
      <el-table-column prop="weight" label="重量(kg)" width="100" />
      <el-table-column prop="actualReps" label="实际次数" width="100" />
      <el-table-column prop="rpe" label="RPE" width="80" />
      <el-table-column prop="notes" label="备注" min-width="180" />
      <el-table-column label="操作" width="120">
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
      this.setList = allSets.filter(item => item.detailId === this.detailId);
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
