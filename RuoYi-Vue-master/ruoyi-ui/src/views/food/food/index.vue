<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="食物名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入食物名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="食物分类" prop="foodGroup">
        <el-input
          v-model="queryParams.foodGroup"
          placeholder="请输入食物分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >
          搜索
        </el-button>
        <el-button
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
        >
          重置
        </el-button>
      </el-form-item>
    </el-form>


    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="foodList"
      @selection-change="handleSelectionChange"
    >
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="编号" prop="id" align="center" />
      <el-table-column label="食物名称" prop="name" align="center" />
      <el-table-column label="食物分类" prop="foodGroup" align="center" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
<!--        <el-button-->
<!--          size="mini"-->
<!--          type="text"-->
<!--          icon="el-icon-view"-->
<!--          @click="handleDetail(scope.row)"-->
<!--        >-->
<!--          查看详情-->
<!--        </el-button>-->
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加 / 修改对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="食物名称" prop="name">
          <el-input
            v-model="form.name"
            placeholder="请输入食物名称"
          />
        </el-form-item>
        <el-form-item label="食物分类" prop="foodGroup">
          <el-input
            v-model="form.foodGroup"
            placeholder="请输入食物分类"
          />
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
import {
  listFood,
  getFood,
  delFood,
  addFood,
  updateFood
} from "@/api/food/food"

export default {
  name: "Food",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      foodList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        foodGroup: null
      },
      form: {},
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleDetail(row) {
      this.$router.push({
        path: "/food/fooddetail/index",
        query: {
          id: row.id
        }
      })
    },
    getList() {
      this.loading = true
      listFood(this.queryParams).then(response => {
        this.foodList = response.rows
        this.total = response.total
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
        name: null,
        foodGroup: null
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
      this.title = "添加食物"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getFood(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改食物"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateFood(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addFood(this.form).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm(`是否确认删除食物编号为 "${ids}" 的数据项？`)
        .then(() => delFood(ids))
        .then(() => {
          this.getList()
          this.$modal.msgSuccess("删除成功")
        })
    },
    handleExport() {
      this.download(
        "food/food/export",
        {...this.queryParams},
        `food_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
