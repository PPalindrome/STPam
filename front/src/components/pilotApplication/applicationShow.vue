<template>
  <el-row gutter="20">
    <el-col :span="12">
      <div>
        <h2 style="text-align: center">时空过程产品信息</h2>
        <br>
        <el-table :data="tableData" style="width: 90%; margin-left: 5%" max-height="400">
          <el-table-column fixed prop="date" label="生产日期" width="100" />
          <el-table-column prop="name" label="产品名称" width="120" />
          <el-table-column prop="model" label="模型使用" width="120" />
          <el-table-column prop="size" label="产品大小" width="120" />
          <el-table-column label="操作" width="90">
            <template #default="scope">
              <el-button
                  link
                  type="primary"
                  size="small"
                  @click.prevent="deleteRow(scope.$index)"
              >显示详情</el-button>
              <br>
              <el-button type="success" size="small">下载产品</el-button>
            </template>
          </el-table-column>
          <el-table-column align="right">
            <template #header >
              <el-input v-model="searchProduct" size="small" placeholder="查找时空过程产品"/>
            </template>
          </el-table-column>
        </el-table>
<!--        <el-button class="mt-4" style="width: 100%" @click="onAddItem"-->
<!--        >显示全部</el-button>-->
      </div>
      <div class="demo-image__placeholder">
        <div class="block">
        <span style="text-align: center">
          <div>
            <h3>时空过程模型结构</h3>
            <br>
          </div></span>
          <el-image :src="src">
            <template #placeholder>
              <div class="image-slot">加载中<span class="dot">...</span></div>
            </template>
          </el-image>
        </div>
      </div>
    </el-col>
    <el-col :span="12">
      <h2 style="text-align: center">产品详细信息</h2>
      <div
          class="inline-flex"
          style="margin: 5% 0; text-align: center"
          :style="{
          boxShadow: `var(${'--el-box-shadow-light'})`
        }"
      >
        <div >
          <img src="../../assets/巴塘折线图.jpg">
        </div>
<!--        <el-input-->
<!--            type='textarea'-->
<!--            @input='change($event)'-->
<!--            v-model="inputResult"-->
<!--            :autosize="{minRows:30,maxRows:30}"-->
<!--            readonly-->
<!--        />-->
      </div>
    </el-col>
  </el-row>

</template>

<script setup lang="ts">
const src =
    'https://img0.baidu.com/it/u=2835212255,2179566332&fm=253&fmt=auto&app=138&f=JPG?w=1149&h=500'

import { ref } from 'vue'
import dayjs from 'dayjs'
const searchProduct=ref('')
const now = new Date()

const tableData = ref([
  // {
  //   date: '暂无',
  //   name: '暂无',
  //   model: '暂无',
  //   size: '暂无',
  //   ability: '暂无',
  // },
  {
    date: '2022-08-12',
    name: '巴塘径流模拟图',
    model: 'DA-RNN',
    size: '64.90KB',
  },
])

const deleteRow = (index: number) => {
  tableData.value.splice(index, 1)
}

// const onAddItem = () => {
//   now.setDate(now.getDate() + 1)
//   tableData.value.push({
//     date: dayjs(now).format('YYYY-MM-DD'),
//     name: 'STPNet',
//     state: '完成',
//     version: '1.0.0',
//     size: '300KB',
//     author: '',
//     ability:'时空预测',
//   })
// }
</script>

<style scoped>
.demo-image__placeholder .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 100%;
  box-sizing: border-box;
  vertical-align: top;
}
.demo-image__placeholder .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.demo-image__placeholder .el-image {
  padding: 0 0px;
  max-width: 700px;
  max-height: 600px;
}

.demo-image__placeholder .dot {
  animation: dot 2s infinite steps(3, start);
  overflow: hidden;
}
</style>
