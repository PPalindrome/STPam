<template>

  <el-form :model="form" label-width="120px" style="width:60%;margin: 3% 5%">
    <el-form-item label="关键字" placeholder="请输入关键字">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item label="选择数据">
      <el-select v-model="form.region" placeholder="请选择你的数据">
        <el-option label="水位数据" value="水位数据" />
        <el-option label="遥感数据" value="遥感数据" />
      </el-select>
    </el-form-item>
    <el-form-item label="选择时间">
      <el-date-picker
          v-model="value1"
          type="datetimerange"
          range-separator="至"
          start-placeholder="Start date"
          end-placeholder="End date"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
      <el-button>重置</el-button>
    </el-form-item>
  </el-form>
  <div>
    <Chart :chartType="'bar'" :chartData="[0,0,0,0,0,0]" style="margin: 8% 5%"/>
    <Chart :chartType="'line'" :chartData="[0,0,0,0,0,0]" style="margin: 8% 5%"/>
  </div>
</template>

<script setup>
import {ref,reactive} from "vue";
import Chart from './chart.vue'

//选择时间
const value1 = ref([
  new Date(2016, 10, 10, 10, 10),
  new Date(2021, 10, 11, 10, 10),
])
// do not use same name with ref
const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const onSubmit = () => {
  console.log('submit!')
}

</script>

<style scoped>

</style>