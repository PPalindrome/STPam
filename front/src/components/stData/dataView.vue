<template>
  <div>
    <h2>多源数据信息上传</h2>
    <br />
    <el-upload
        ref="uploadRef"
        class="upload-demo"
        action="http://localhost:8081/files/upload/dataset"
        :auto-upload="false"
    >
      <template #trigger>
        <el-button type="primary">选择本地数据&#8194;<el-icon><Select/></el-icon>
        </el-button>
      </template>
      <el-button class="ml-3" type="success" @click="submitUpload" style="margin-left: 3%">上传至服务器&#8194;
        <el-icon>
          <UploadFilled/>
        </el-icon>
      </el-button>
      <template #tip>
        <div class="el-upload__tip">文件大小不得超过20GB!</div>
      </template>
    </el-upload>
  </div>
  <br />
  <div>
    <el-button class="ml-3" type="success" @click="toDataSetting">适配外部数据源</el-button>
  </div>

  <br /><br />
  <div style="width: 80%">
    <el-button type="primary" @click="queryDatasets">点击刷新</el-button>
    <el-table
        v-loading="loading"
        :data="filterDatasetTableData"
        style="width: 150%"
        :default-sort="{ prop: 'name', order: 'ascending' }"
        highlight-current-row
        @current-change="handleCurrentChange2"
        max-height="50%"
    >
      <el-table-column type="index" width="80" />
      <el-table-column prop="name" label="数据集名称" width="200" sortable >
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column prop="description" property="description" label="数据集描述" width="240%" />
      <el-table-column prop="date" property="date" label="上传时间"  width="240%"/>
      <el-table-column label="操作" show-overflow-tooltip>
        <template #default>
          <el-button link type="primary" size="small" @click="handleClick"
          >Detail</el-button>
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template #header >
          <el-input v-model="searchDataset" size="small" placeholder="输入数据集名称查找"/>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, computed, onMounted} from "vue";
import type {ElTable} from 'element-plus'
import type {TabsPaneContext} from 'element-plus'
import {
  UploadFilled,
  Select,
} from '@element-plus/icons-vue'
import type {UploadInstance} from 'element-plus'
import services from "../../utils/request";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const uploadRef = ref<UploadInstance>()
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
      name: '暂无',
      describe: '暂无',
      date: '暂无',
    },
  ]
})

//查询数据库已有的数据集
const queryDatasets = () => {
  services.post('/queryDatasets').then(res => {
    // console.log(res)
    DatasetTable.Datasets=res
  })
  loading.value = false
}
const filterDatasetTableData = computed(() =>
    DatasetTable.Datasets.filter(
        (data) =>
            !searchDataset.value ||
            data.name.toLowerCase().includes(searchDataset.value.toLowerCase())
    )
)
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