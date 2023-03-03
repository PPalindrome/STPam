<template>
  <el-row gutter="10">
    <el-col :span="11" style="height: 780px">
      <h2>淹没分析</h2>
      <el-descriptions border style="width: 80%">
        <el-descriptions-item v-for="v in unIncrementalResultInfo" :key="v.info" :item="v">
          <template #label>{{ v.title }}</template>
          {{ v.info }}
        </el-descriptions-item>
      </el-descriptions>
    </el-col>
    <el-col :span="11">
      <Map/>
    </el-col>
  </el-row>

</template>

<script setup>
import {reactive} from "vue";
import Map from './map.vue'

const unIncrementalResultInfo = reactive([
  {title: '淹没时间', info: '6h'},
  {title: '淹没地点', info: ''},
  {title: '淹没范围', info: '300m²'},
  {title: '淹没过程', info: '洪水'},
  {title: '影响因素', info: '暴雨'},
  {title: '变化规律', info: '未知'},
])

</script>

<style scoped>

</style>