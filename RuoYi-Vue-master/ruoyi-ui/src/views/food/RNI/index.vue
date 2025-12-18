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
    </el-card>

    <!-- 结果区 -->
    <el-card v-if="result" shadow="never" class="result-card">
      <div class="subtitle">每日推荐摄入量</div>

      <el-descriptions :column="3" border>
        <el-descriptions-item label="热量">
          {{ result.energy }} kcal
        </el-descriptions-item>
        <el-descriptions-item label="蛋白质">
          {{ result.protein }} g
        </el-descriptions-item>
        <el-descriptions-item label="脂肪">
          {{ result.fat }} g
        </el-descriptions-item>
        <el-descriptions-item label="碳水化合物">
          {{ result.carb }} g
        </el-descriptions-item>
        <el-descriptions-item label="膳食纤维">
          {{ result.fiber }} g
        </el-descriptions-item>
        <el-descriptions-item label="糖（上限）">
          {{ result.sugar }} g
        </el-descriptions-item>

        <el-descriptions-item label="维生素A">
          {{ result.vitA }} µg
        </el-descriptions-item>
        <el-descriptions-item label="维生素C">
          {{ result.vitC }} mg
        </el-descriptions-item>
        <el-descriptions-item label="维生素D">
          {{ result.vitD }} µg
        </el-descriptions-item>
        <el-descriptions-item label="维生素E">
          {{ result.vitE }} mg
        </el-descriptions-item>
        <el-descriptions-item label="维生素B6">
          {{ result.vitB6 }} mg
        </el-descriptions-item>
        <el-descriptions-item label="维生素B12">
          {{ result.vitB12 }} µg
        </el-descriptions-item>

        <el-descriptions-item label="钙">
          {{ result.ca }} mg
        </el-descriptions-item>
        <el-descriptions-item label="镁">
          {{ result.mg }} mg
        </el-descriptions-item>
        <el-descriptions-item label="铁">
          {{ result.fe }} mg
        </el-descriptions-item>
        <el-descriptions-item label="钠（上限）">
          {{ result.na }} mg
        </el-descriptions-item>
        <el-descriptions-item label="钾">
          {{ result.k }} mg
        </el-descriptions-item>
        <el-descriptions-item label="锌">
          {{ result.zn }} mg
        </el-descriptions-item>

        <el-descriptions-item label="Omega-3 (ALA)">
          {{ result.omega3 }} g
        </el-descriptions-item>
        <el-descriptions-item label="EPA+DHA">
          {{ result.epaDha }} mg
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
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
      },
      result: null
    }
  },
  methods: {
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
