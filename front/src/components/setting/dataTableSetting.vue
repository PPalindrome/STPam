<template>
  <div>
    <h2>下载适配数据库中数据</h2>
    <br />
  </div>
  <br />

  <br /><br />
  <div style="width: 100%">
    <el-table
        v-loading="loading"
        :data="DatasetTable.Datasets"
        style="width: 150%"
        :default-sort="{ prop: 'name', order: 'ascending' }"
        highlight-current-row
        @current-change="handleCurrentChange2"
        max-height="50%"
    >
      <el-table-column type="index" />
      <el-table-column prop="name" label="数据表名称" sortable >
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column prop="size" property="size" label="数据表大小" />
      <el-table-column prop="date" property="date" label="查询时间"  />
      <el-table-column label="操作" show-overflow-tooltip>
        <template #default>
          <el-button link type="primary" size="small" @click="handleClick"
          >Download</el-button>
        </template>
      </el-table-column>
      <el-table-column align="left">
        <template #header>
          <div style="display: flex;align-items: center">
            <el-input v-model="searchDataset" placeholder="输入数据集名称查找"/>
            <el-button style="margin-left: 10px" type="primary" @click="queryDatasets">点击查找</el-button>
          </div>
        </template>
      </el-table-column>

    </el-table>
  </div>
  <br>
  <div class="demo-pagination-block" style="margin: auto">
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
import type {UploadInstance} from 'element-plus'
import services from "../../utils/request";
import axios from 'axios'
import {ElMessage, genFileId} from "element-plus";
import {useRouter} from "vue-router";
const uploadRef = ref<UploadInstance>()
const loading = ref(true)
const searchDataset=ref('')
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
// const handleClick = (val: Dataset) => {
//   console.log(val.name)
// }
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
      name: '暂无',
      size: '暂无',
      date: '暂无',
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
//查询数据库已有的数据集
const data={
  pageSize:'',
  currentPage:'',
  searchData:'',
  databaseName:'',
  datasourceID:''
}
const queryDatasets = () => {
  // data.databaseName=history.state.
  data.databaseName=history.state.databaseName
  data.datasourceID=history.state.datasourceID
  data.pageSize=String(pageSize.value)
  data.currentPage=String(currentPage.value)
  data.searchData=searchDataset.value
  services.post("data/querryPage",data)
      .then((result)=>{
        DatasetTable.Datasets=result
      })
  services.post("data/querryTotal",data)
      .then((result)=>{
        console.log(result)
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
const downloadData={
  databaseName:'',
  tableName:''
}
const handleCurrentChange2=(val: Dataset)=>{
  // currentPath=val.path
  showSuccess("已选择数据集："+val.name)
  downloadData.databaseName=history.state.databaseName
  downloadData.tableName=val.name
   services.post("data/download",downloadData)
      .then((result)=>{

      })
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