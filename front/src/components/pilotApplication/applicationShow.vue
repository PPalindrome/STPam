<template>
  <el-row gutter="20">
    <el-col :span="13">
      <div>
        <h2 style="text-align: center">{{$t("menu.product.show.title")}}</h2>
        <br>
          <el-button style="background-color: #778899; color: #FFFFFF" @click="queryProduct">点击刷新</el-button>
        <el-table
            :data="filterProducttableData"
            style="max-height:90%"
            :default-sort="{ prop: 'pName', order: 'ascending' }"
            highlight-current-row
        >
          <el-table-column type="index" width="40" />
          <el-table-column prop="pName" :label="$t('menu.product.show.productName')" width="200" sortable>
            <template #default="scope">{{scope.row.pName}}</template>
          </el-table-column>
          <el-table-column prop="date" property="date" :label="$t('button.date')" width="120" />
          <el-table-column prop="modelUse" property="modelUse" :label="$t('button.modelName')" width="120" />
          <el-table-column prop="application" property="application" :label="$t('button.description')" width="200" />
          <el-table-column align="right">
            <template #header >
              <el-input v-model="searchProduct" size="small" :placeholder="$t('button.search')"/>
            </template>
            <template #default="scope" >
              <el-button style="background-color: #3CB371; color: #FFFFFF" size="small" @click="showImg(scope.$index, scope.row)">
                {{ $t("button.view") }}</el-button>
              <el-button style="background-color: #1E90FF; color: #FFFFFF" size="small" @click="download(scope.$index, scope.row)">
                {{ $t("button.download") }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-col>
    <el-col :span="11">
      <div
          class="inline-flex"
          style="margin: 10% 0; text-align: center"
          :style="{
          boxShadow: `var(${'--el-box-shadow-light'})`
        }"
      >

          <img src="../../assets/巴塘折线图.jpg">

      </div>
    </el-col>
  </el-row>

</template>

<script setup lang="ts">

import {ElMessage} from "element-plus";

import {computed, onMounted, reactive, ref} from 'vue'
import services from "../../utils/request.js";
import {Base64} from "js-base64";
const searchProduct=ref('')
onMounted(() => {
  queryProduct()
})
interface Product {
  pName: string
  date: string
  path: string
  application: string
  modelUse: string
}
//模型表格
const tableData =reactive({
  ProductData: [
    {
      pName: '暂无',
      date: '暂无',
      modelUse: '暂无',
      application: '暂无',
    },
  ]
})
const queryProduct=()=>{
  services.post('/queryProduct').then(res=>{
    console.log(res)
    tableData.ProductData=res
  })
}
const filterProducttableData = computed(() =>
    tableData.ProductData.filter(
        (data) =>
            !searchProduct.value ||
            data.pName.toLowerCase().includes(searchProduct.value.toLowerCase())
    )
)
let imgPath=''//存图片url
let imgBase='' //存图片字节流
let fit='fill' //img填充方式
let url=''
const showImg=(index,row)=>{
  imgPath=row.path
  services.post('/getImgBytes', {
    imgStr: imgPath,
    responseType: 'blob'
  }).then((response) => {
    if (response) {
      ElMessage({
        type: 'success',
        message: '成功'
      })
      imgBase=response
      const picUrl= window.URL.createObjectURL("data:image/png;base64,"+response)
      url=picUrl
      localStorage.setItem('picurl','data:image/png;base64,'+imgBase)
      console.log(picUrl)
    } else {
      ElMessage({
        type: 'error',
        message: '失败'
      })
    }
  })
}
const productSelect=(index: number,row: Product)=> {
  console.log(row.pName)
}


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
