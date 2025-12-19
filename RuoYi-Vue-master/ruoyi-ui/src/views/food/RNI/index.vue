<template>
  <div class="nutrition-container">
    <el-card shadow="never">
      <div class="title">个体化每日营养素推荐摄入量计算</div>

      <!-- 输入区 -->
      <el-form :model="form" label-width="120px" inline>
        <el-form-item label="性别">
          <el-select v-model="form.gender" style="width: 120px">
            <el-option label="男" value="male" />
            <el-option label="女" value="female" />
          </el-select>
        </el-form-item>

        <el-form-item label="年龄 (岁)">
          <el-input-number v-model="form.age" :min="1" />
        </el-form-item>

        <el-form-item label="身高 (cm)">
          <el-input-number v-model="form.height" :min="100" />
        </el-form-item>

        <el-form-item label="体重 (kg)">
          <el-input-number v-model="form.weight" :min="30" />
        </el-form-item>

        <el-form-item label="活动水平">
          <el-select v-model="form.activity">
            <el-option label="久坐" :value="1.2" />
            <el-option label="轻体力" :value="1.375" />
            <el-option label="中等" :value="1.55" />
            <el-option label="高" :value="1.725" />
            <el-option label="很高" :value="1.9" />
          </el-select>
        </el-form-item>

        <el-form-item label="日照水平">
          <el-select v-model="form.sun">
            <el-option label="少" value="low" />
            <el-option label="中" value="mid" />
            <el-option label="多" value="high" />
          </el-select>
        </el-form-item>

        <el-form-item label="是否吸烟">
          <el-switch v-model="form.smoke" />
        </el-form-item>
      </el-form>

      <el-button type="primary" @click="calculate">计算推荐摄入量</el-button>
      <el-date-picker
        v-model="selectedDate"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="选择日期"
        style="margin-right: 10px"
      />

      <el-button type="warning" @click="loadDailyNutrition">
        查询当日营养差值
      </el-button>
      <el-button
        type="success"
        icon="el-icon-check"
        @click="saveProfile"
      >
        保存当前用户基础信息
      </el-button>
    </el-card>

    <el-card v-if="result" shadow="never" class="result-card">
      <div class="subtitle">每日推荐摄入量</div>

      <el-descriptions :column="3" border>
        <el-descriptions-item
          v-for="item in nutritionFields"
          :key="item.key"
          :label="item.label"
        >
          {{ result[item.key] }} {{ item.unit }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-card v-if="actual" shadow="never" class="result-card">
      <div class="subtitle">当日实际摄入量</div>

      <el-descriptions :column="3" border>
        <el-descriptions-item
          v-for="item in nutritionFields"
          :key="item.key"
          :label="item.label"
        >
          {{ actual[item.key] || 0 }} {{ item.unit }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-card v-if="diff" shadow="never" class="result-card">
      <div class="subtitle">营养差值（推荐 − 实际）</div>

      <el-descriptions :column="3" border>
        <el-descriptions-item
          v-for="item in nutritionFields"
          :key="item.key"
          :label="item.label"
        >
      <span
        :style="{
          color: diff[item.key] < 0 ? 'red' : '#67C23A'
        }"
      >
        {{ diff[item.key] }} {{ item.unit }}
      </span>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>



  </div>
</template>

<script>
import {addProfile, listProfile, updateProfile} from "@/api/food/profile"
import { getDailyActualNutrition } from '@/api/food/food'
export default {
  name: "DailyNutritionCalculator",
  data() {
    return {
      form: {
        gender: "male",
        age: 25,
        height: 170,
        weight: 65,
        activity: 1.55,
        sun: "mid",
        smoke: false
      },nutritionFields: [
        { key: 'energy', label: '热量', unit: 'kcal' },
        { key: 'protein', label: '蛋白质', unit: 'g' },
        { key: 'fat', label: '脂肪', unit: 'g' },
        { key: 'carb', label: '碳水化合物', unit: 'g' },
        { key: 'fiber', label: '膳食纤维', unit: 'g' },
        { key: 'sugar', label: '糖（上限）', unit: 'g' },

        { key: 'vitA', label: '维生素A', unit: 'µg' },
        { key: 'vitC', label: '维生素C', unit: 'mg' },
        { key: 'vitD', label: '维生素D', unit: 'µg' },
        { key: 'vitE', label: '维生素E', unit: 'mg' },
        { key: 'vitB6', label: '维生素B6', unit: 'mg' },
        { key: 'vitB12', label: '维生素B12', unit: 'µg' },

        { key: 'ca', label: '钙', unit: 'mg' },
        { key: 'mg', label: '镁', unit: 'mg' },
        { key: 'fe', label: '铁', unit: 'mg' },
        { key: 'na', label: '钠（上限）', unit: 'mg' },
        { key: 'k', label: '钾', unit: 'mg' },
        { key: 'zn', label: '锌', unit: 'mg' },

        { key: 'omega3', label: 'Omega-3', unit: 'mg' },
        { key: 'epaDha', label: 'EPA + DHA', unit: 'mg' }
      ]
      ,
      result: null,
      actual: null,          // 实际摄入量（新增）
      diff: null,            // 差值（新增）
      selectedDate: ''       // 查询日期（新增）
    }
  },
  methods: {
    async loadDailyNutrition() {
      if (!this.result) {
        this.$message.warning('请先计算推荐摄入量')
        return
      }

      if (!this.selectedDate) {
        this.$message.warning('请选择日期')
        return
      }

      const res = await getDailyActualNutrition(this.selectedDate)
      console.log('res:', res)
      this.actual = res.data || {}

      // 前端相减
      const diff = {}
      Object.keys(this.result).forEach(key => {
        const r = Number(this.result[key] || 0)
        const a = Number(this.actual[key] || 0)
        diff[key] = r - a
      })

      this.diff = diff
    },
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
    },async saveProfile() {
      // const userId = this.getCurrentUserId()
      const userId = 0
      console.log('userId:', userId)

      const {
        gender,
        age,
        height,
        weight,
        activity,
        sun,
        smoke
      } = this.form

      const payload = {
        userId,
        gender,
        age,
        height,
        weight,
        activityLevel: activity,
        sunLevel: sun,
        smoke: smoke ? 1 : 0
      }

      try {
        // ① 查询是否已有该用户的 Profile
        const res = await listProfile({ userId })

        const list = res.rows || res.data || []

        if (list.length > 0) {
          // ② 已存在 → 更新（一定要带 id）
          const existing = list[0]

          await updateProfile({
            ...payload,
            id: existing.id
          })
        } else {
          // ③ 不存在 → 新增
          await addProfile(payload)
        }

        this.$message.success('用户基础信息保存成功')
      } catch (e) {
        console.error(e)
        this.$message.error('保存失败')
      }
    },
    calculate() {
      const { gender, age, height, weight, activity, sun, smoke } = this.form

      // 1. BMR
      const bmr =
        gender === "male"
          ? 10 * weight + 6.25 * height - 5 * age + 5
          : 10 * weight + 6.25 * height - 5 * age - 161

      // 2. TEE
      const energy = Math.round(bmr * activity)

      // 3. 宏量营养素
      const protein = Math.round(weight * 0.8)
      const fat = Math.round((energy * 0.3) / 9)
      const carb = Math.round(
        (energy - protein * 4 - fat * 9) / 4
      )
      const fiber = Math.round((14 * energy) / 1000)
      const sugar = Math.round((energy * 0.1) / 4)

      // 4. 维生素
      const vitA = gender === "male" ? 900 : 700
      const vitC = (gender === "male" ? 90 : 75) + (smoke ? 35 : 0)
      const vitD = sun === "low" ? 20 : sun === "mid" ? 15 : 10
      const vitE = 15
      const vitB6 = age < 50 ? 1.3 : 1.7
      const vitB12 = 2.4

      // 5. 矿物质
      const ca = age < 50 ? 1000 : 1200
      const mg = gender === "male" ? 420 : 320
      const fe = gender === "male" ? 8 : 18
      const na = 2300
      const k = 4700
      const zn = gender === "male" ? 11 : 8

      // 6. 脂肪酸
      const omega3 = gender === "male" ? 1.6 : 1.1
      const epaDha = 300

      this.result = {
        energy,
        protein,
        fat,
        carb,
        fiber,
        sugar,
        vitA,
        vitC,
        vitD,
        vitE,
        vitB6,
        vitB12,
        ca,
        mg,
        fe,
        na,
        k,
        zn,
        omega3,
        epaDha
      }
    }
  }
}
</script>

<style scoped>
.nutrition-container {
  padding: 20px;
}
.title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 15px;
}
.subtitle {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}
.result-card {
  margin-top: 20px;
}
</style>
