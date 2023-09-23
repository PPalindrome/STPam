<template>
  <div>
    <h2>{{$t('menu.dataAnalysis.view.title')}}</h2>
    <br />
    <el-upload
        ref="uploadRef"
        class="upload-demo"
        action="http://localhost:8081/files/upload/dataset"
        :auto-upload="false"
        :limit="1"
        :on-exceed="handleExceed"
        :on-success="uploadSuccess"
    >
      <template #trigger>
        <el-button type="primary">{{$t('menu.dataAnalysis.view.chooseLocalData')}}&#8194;<el-icon><Select/></el-icon>
        </el-button>
      </template>
      <el-button class="ml-3" type="success" @click="submitUpload" style="margin-left: 3%">{{$t('menu.dataAnalysis.view.uploadToServer')}}&#8194;
        <el-icon>
          <UploadFilled/>
        </el-icon>
      </el-button>
      <template #tip>
        <div class="el-upload__tip">{{$t('menu.dataAnalysis.view.warning')}}!</div>
      </template>
    </el-upload>
  </div>
  <br />
  <div>
    <el-button class="ml-3" type="success" @click="toDataSetting">{{$t('menu.dataAnalysis.view.adapt')}}</el-button>
  </div>

  <br /><br />
  <div>
    <el-table
        v-loading="loading"
        :data="DatasetTable.Datasets"
        :default-sort="{ prop: 'name', order: 'ascending' }"
        highlight-current-row
        @current-change="handleCurrentChange2"
        max-height="50%"
    >
      <el-table-column type="index" width="100" />
      <el-table-column prop="name" :label="$t('menu.dataAnalysis.view.table.name')" width="400" sortable >
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column prop="description" property="description" :label="$t('button.description')" width="500" />
      <el-table-column prop="date" property="date" :label="$t('button.date')"  width="200"/>
      <el-table-column align="right" width="200">
        <template #header>
          <el-input v-model="searchDataset" :placeholder="$t('button.search')"/>
        </template>
        <template #default="scope" >
          <el-button link type="primary" size="small" @click="handleClick"
          >{{ $t('button.view') }}</el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
  <div class="demo-pagination-block">
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        v-model:total="total"
        @size-change="sizeChange"
        @current-change="currentChange"
        @total-change="totalChange"
        :page-sizes="[5, 10, 15, 20]"
        :small="small"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
    />
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, computed, onMounted} from "vue";
import type {ElTable, UploadProps, UploadRawFile} from 'element-plus'
import type {TabsPaneContext} from 'element-plus'
import {
  UploadFilled,
  Select,
} from '@element-plus/icons-vue'
import type {UploadInstance} from 'element-plus'
import services from "../../utils/request";
import axios from 'axios'
import {ElMessage, genFileId} from "element-plus";
import {useRouter} from "vue-router";
const uploadRef = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  uploadRef.value!.handleStart(file)
}
const uploadSuccess=(response)=>{
  if(response.code==200){
    ElMessage({
      message: 'Success！',
      type: 'success',
    })
  }
  else{
    ElMessage({
      message: response.message,
      type: 'error',
    })
  }
}
const submitUpload = () => {
  uploadRef.value!.submit()
}
const loading = ref(true)
const searchDataset=ref('')
let currentPath=""
//查询数据库已有模型和数据集
onMounted(() => {
  queryDatasets()
})
//显示
const showSuccess=(message)=>{
  ElMessage({
    showClose: true,
    message: message,
    type: 'success',
  })
}
const handleClick = () => {
  console.log('click')
}
//数据集
interface Dataset {
  name: string
  description: string
  date: string
}
//数据集表格
const DatasetTable =reactive({
  Datasets: [
    {
      name: 'No Data',
      describe: 'No Data',
      date: 'No Data',
    },
  ],
})
const currentPage = ref(1)
const pageSize = ref(10)
const total=ref(100)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const sizeChange = (size)=>{
  pageSize.value=size;
  queryDatasets();
}
const currentChange = (page)=>{
  currentPage.value=page
  queryDatasets();
}
// const handleCurrentChange = (val: number) => {
//   console.log(`current page: ${val}`)
// }
//查询数据库已有的数据集
const datasource={
  pageSize:'',
  currentPage:'',
  searchData:''
}
const queryDatasets = () => {
  datasource.pageSize=String(pageSize.value)
  datasource.currentPage=String(currentPage.value)
  datasource.searchData=searchDataset.value
  console.log()
  services.post("queryDatasets/page",datasource)
      .then((result)=>{
        DatasetTable.Datasets=result
      })
  services.post("queryDatasets/total",datasource)
      .then((result)=>{
        console.log(result.obj)
        total.value=result.obj;
      })
  loading.value=false
}
// const filterDatasetTableData = computed(() =>
//     DatasetTable.Datasets.filter(
//         (data) =>
//             !searchDataset.value ||
//             data.name.toLowerCase().includes(searchDataset.value.toLowerCase())
//     )
// )
const handleCurrentChange2=(val: Dataset)=>{
  // currentPath=val.path
  showSuccess("已选择数据集："+val.name)
}

//跳转到数据源适配
const router=useRouter();
const toDataSetting=()=>{
  router.push('/setting/databaseSetting');
}
</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}

.block:last-child {
  border-right: none;
}

.block .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
</style>