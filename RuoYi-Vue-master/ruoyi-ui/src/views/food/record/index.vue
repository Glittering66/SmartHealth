<template>
  <div class="app-container">
    <!-- 搜索区：卡片 + 栅格布局 -->
    <el-card shadow="never" class="search-card" v-show="showSearch">
      <div slot="header" class="card-header">
        <span>查询条件</span>
        <div class="header-actions">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </div>
      </div>

      <el-form :model="queryParams" ref="queryForm" size="small" label-width="68px">
        <el-row :gutter="12">
          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="食物ID" prop="foodId">
              <el-input v-model="queryParams.foodId" placeholder="请输入食物ID" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="食物名称" prop="foodName">
              <el-input v-model="queryParams.foodName" placeholder="请输入食物名称" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="类别" prop="foodGroup">
              <el-input v-model="queryParams.foodGroup" placeholder="如：水果/主食/零食" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="餐次" prop="mealType">
              <el-select v-model="queryParams.mealType" placeholder="请选择餐次" clearable style="width:100%">
                <el-option v-for="it in mealTypeOptions" :key="it.value" :label="it.label" :value="it.value" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="8" :lg="6">
            <el-form-item label="日期" prop="eatenAt">
              <el-date-picker v-model="queryParams.eatenAt" type="date" value-format="yyyy-MM-dd"
                              placeholder="请选择日期" clearable style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['food:record:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['food:record:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['food:record:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['food:record:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格：类别/份量/单位 单独成列 -->
    <el-table v-loading="loading" :data="recordList" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="id" width="90" />

      <el-table-column label="食物ID" align="center" prop="foodId" width="90" />
      <el-table-column label="食物名称" align="center" prop="foodName" min-width="160" show-overflow-tooltip />
      <el-table-column label="食物类别" align="center" prop="foodGroup" width="120" show-overflow-tooltip />

      <el-table-column label="餐次" align="center" prop="mealType" width="120">
        <template slot-scope="scope">
          <el-tag size="mini">{{ scope.row.mealType || '-' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="数量" align="center" prop="amount" width="110" />
      <el-table-column label="单位" align="center" prop="unit" width="130" show-overflow-tooltip />

      <el-table-column label="日期" align="center" prop="eatenAt" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.eatenAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="总量(g)" align="center" prop="totalWeightG" width="130" show-overflow-tooltip />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="140">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['food:record:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['food:record:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 新增/修改弹窗：增加 份量（food-serving） + 单位下拉 -->
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-form-item label="食物名称" prop="foodName">
          <el-autocomplete
            v-model="form.foodName"
            placeholder="请输入食物名称"
            :fetch-suggestions="querySearchFood"
            value-key="name"
            clearable
            @select="handleFoodSelect"
          />
        </el-form-item>

        <el-form-item label="食物类别" prop="foodGroup">
          <el-input v-model="form.foodGroup" placeholder="如：水果/主食/零食" clearable />
        </el-form-item>


        <el-form-item label="餐次" prop="mealType">
          <el-select v-model="form.mealType" placeholder="请选择餐次" style="width: 100%">
            <el-option v-for="it in mealTypeOptions" :key="it.value" :label="it.label" :value="it.value" />
          </el-select>
        </el-form-item>

        <el-form-item label="摄入量">
          <el-input-number
            v-model="form.amount"
            :min="0"
            style="width: 120px"
          />

          <el-select
            v-model="form.servingId"
            placeholder="单位"
            style="width: 160px; margin-left: 10px"
          >
            <el-option
              v-for="item in foodServings"
              :key="item.servingWeight"
              :label=" item.servingDesc+'('+item.servingWeight+'g)'"
              :value="item.servingWeight"
            />
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/food/record"
import {listFood,getFoodBaseDetail} from "@/api/food/food";

export default {
  name: "Record",
  data() {
    // 二选一校验：foodId 或 foodName 至少填写一个
    const validateFoodIdOrName = (rule, value, callback) => {
      const foodId = (this.form.foodId ?? "").toString().trim()
      const foodName = (this.form.foodName ?? "").toString().trim()
      if (!foodId && !foodName) return callback(new Error("食物ID或食物名称至少填写一个"))
      callback()
    }

    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      foodList: [],
      total: 0,
      recordList: [],
      title: "",
      open: false,
      selectedServing: null,

      mealTypeOptions: [
        { label: "早餐", value: "早餐" },
        { label: "午餐", value: "午餐" },
        { label: "晚餐", value: "晚餐" },
        { label: "加餐", value: "加餐" },
      ],


      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null, // 不显示，但可自动注入
        foodId: null,
        foodName: null,
        foodGroup: null,
        mealType: null,
        eatenAt: null,
      },
      queryParams_Food: {
        pageNum: 1,
        pageSize: 10,
        foodName: null,
        foodGroup: null
      },

      form: {
        foodId: null,
        foodName: '',
        foodGroup: '',
        servingId: null,
        amount: 1,
        unit: null
      },
      foodServings: [] ,// FoodServingDto[]

      rules: {
        foodId: [{ validator: validateFoodIdOrName, trigger: "blur" }],
        foodName: [{ validator: validateFoodIdOrName, trigger: "blur" }],
        foodGroup: [{ required: true, message: "食物类别不能为空", trigger: "blur" }],
        mealType: [{ required: true, message: "餐次不能为空", trigger: "change" }],
        amount: [{ required: true, message: "数量不能为空", trigger: "blur" }],

        // 份量一般可选；如果你希望必填，把 required: true 打开
        // foodServing: [{ required: true, message: "份量不能为空", trigger: "blur" }],
      },
    }
  },
  watch: {
    'form.servingId'(val) {
      if (!val) {
        this.selectedServing = null
        return
      }

      this.selectedServing = this.foodServings.find(
        s => s.servingWeight === val
      ) || null
    }
  },
  created() {
    this.getList()
  },
  methods: {

    // 从登录态拿 userId（不同ruoyi版本做兜底）
    getCurrentUserId() {
      try {
        const g = (this.$store && this.$store.getters) || {}
        const s = (this.$store && this.$store.state) || {}
        return (
          g.userId ||
          g.userid ||
          (s.user && (s.user.userId || s.user.userid)) ||
          (s.userinfo && (s.userinfo.userId || s.userinfo.userid)) ||
          null
        )
      } catch (e) {
        return null
      }
    },
    querySearchFood(queryString, cb) {
      if (!queryString) {
        cb([])
        return
      }
      console.log(queryString)
      this.queryParams_Food = {
        pageNum: 1,
        pageSize: 20,
        name: queryString,
        foodGroup: null     // ✅ 强制清空
      }

      listFood(this.queryParams_Food).then(res => {
        // el-autocomplete 需要数组
        console.log(res)
        cb(res.rows || [])
      })
    },

    handleFoodSelect(item) {
      console.log('这是item')
       console.log(item)
       this.form.foodName = item.name
      this.form.foodId = item.id
      //
       this.loadFoodDetail(item.name)
    },

    loadFoodDetail(foodName) {
      getFoodBaseDetail({ foodName }).then(res => {
        console.log(res)
        const data=res.data
        // 1. 基础字段回填
        this.form.foodName = data.foodName
        this.form.foodGroup = data.foodGroup

        // 2. servings 用于单位/数量选择
        this.foodServings = data.servings || []

        // 3. 默认选中一个单位（可选）
        if (this.foodServings.length) {
          this.form.servingId = this.foodServings[0].servingWeight
        }
      })
    },


    todayStr() {
      const d = new Date()
      const y = d.getFullYear()
      const m = String(d.getMonth() + 1).padStart(2, "0")
      const day = String(d.getDate()).padStart(2, "0")
      return `${y}-${m}-${day}`
    },

    /** 查询列表 */
    getList() {
      this.loading = true
      const uid = this.getCurrentUserId()
      console.log(uid)
      if (uid != null) this.queryParams.userId = uid

      listRecord(this.queryParams)
        .then(res => {
          this.recordList = res.rows
          this.total = res.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },

    cancel() {
      this.open = false
      this.reset()
    },

    reset() {
      this.form = {
        id: null,
        userId: null,
        foodId: null,
        foodName: null,
        foodGroup: null,
        servingId: null,        // ✅ 新增字段
        mealType: null,
        amount: null,
        unit: null,            // 默认给 serving（你想默认 g 就改成 "g"）
        eatenAt: this.todayStr(),   // 默认今天
        calories: null,
        note: null,
        createTime: null,
        updateTime: null,
      }
      this.resetForm("form")
    },

    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },

    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加食物摄入记录"
    },

    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getRecord(id).then(res => {
        this.form = res.data || {}
        if (!this.form.eatenAt) this.form.eatenAt = this.todayStr()
        if (!this.form.unit) this.form.unit = "serving"
        this.open = true
        this.title = "修改食物摄入记录"
      })
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) return

        // 自动注入 userId（避免数据库 user_id not null 报错）
        const uid = this.getCurrentUserId()
        if (uid != null && (this.form.userId == null || this.form.userId === "")) {
          this.form.userId = uid
        }

// 计算总质量 & 组装 unit（展示用）
        if (this.form.amount != null && this.selectedServing) {
          const w = this.selectedServing.servingWeight
          const d = this.selectedServing.servingDesc

          // 标准化总克数
          this.form.totalWeightG = this.form.amount * w

          // 👇 存库的 unit
          this.form.unit = `${d}(${w}g)`
        }

        // 兜底字段
        if (!this.form.eatenAt) this.form.eatenAt = this.todayStr()


        if (this.form.id != null) {
          updateRecord(this.form).then(() => {
            this.$modal.msgSuccess("修改成功")
            this.open = false
            this.getList()
          })
        } else {
          addRecord(this.form).then(() => {
            this.$modal.msgSuccess("新增成功")
            this.open = false
            this.getList()
          })
        }
      })
    },

    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除食物摄入记录编号为"' + ids + '"的数据项？')
        .then(function () {
          return delRecord(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess("删除成功")
        })
        .catch(() => {})
    },

    handleExport() {
      this.download("food/record/export", { ...this.queryParams }, `record_${new Date().getTime()}.xlsx`)
    },
  },
}
</script>

<style scoped>
.search-card { margin-bottom: 12px; }
.card-header { display: flex; align-items: center; justify-content: space-between; }
.header-actions > .el-button + .el-button { margin-left: 8px; }
</style>
