<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-form  ref="modelParaRef" style="margin-left:5%">
          <h2>时空过程深度学习模型选择</h2>
          <br />
          <el-collapse v-model="activeNames" @change="handleChange" style="width: 100%">
            <el-collapse-item name="1">
              <template #title>
                <div class="el-collapse-item-title" style="font-size:20px;font-weight:bolder">上传新模型</div>
                <a>（多个文件以zip压缩包格式上传）</a>
              </template>
              <div style="margin-left: 30px">
                <el-space fill style="width: 50%">
                  <el-form-item label="模型名称" >
                    <el-input id="imname" v-model="modelParam.mname"></el-input>
                  </el-form-item>
                  <el-form-item label="模型版本">
                    <el-input id="iversion" v-model="modelParam.version"></el-input>
                  </el-form-item>
                </el-space>
                <el-row>
                  <el-upload
                      ref="modelUploadRef"
                      action="http://localhost:8081/files/upload/model"
                      :before-upload="beforeUploadModel"
                      :on-success="fileUploadSuccess"
                      :on-remove="handleRemoveModel"
                  >
                    <template #trigger>
                      <el-button type="primary" >选择文件并上传&#8194;<el-icon><UploadFilled/></el-icon></el-button>
                    </template>
                  </el-upload>
                  <el-button type="primary" style="margin-left: 40px; background-color: cadetblue" >发布模型服务</el-button>
                </el-row>
              </div>
            </el-collapse-item>
            <el-collapse-item name="2">
              <template #title>
                <div class="el-collapse-item-title" style="font-size:20px;font-weight:bolder">选择模型</div>
              </template>
              <div>
                <el-col span="12">
                  <el-form-item prop="hModel">
                    <el-button type="primary" @click="queryModels">点击刷新</el-button>
                    <el-table
                        v-loading="loading"
                        :data="filterModeltableData"
                        style="width: 100%"
                        :default-sort="{ prop: 'mname', order: 'ascending' }"
                        highlight-current-row
                    >
                      <el-table-column type="index" width="20" />
                      <el-table-column prop="mname" label="模型名称" width="130" sortable >
                        <template #default="scope">{{ scope.row.mname }}</template>
                      </el-table-column>
                      <el-table-column prop="version" property="version" label="版本" width="100" />
                      <el-table-column prop="author" property="author" label="作者" width="100" />
                      <el-table-column prop="filename" property="filename" label="模型文件名" width="150" />
                      <el-table-column prop="date" property="date" label="上传时间" />
                      <el-table-column align="right">
                        <template #header >
                          <el-input v-model="searchModel" size="small" placeholder="查找模型"/>
                        </template>
                        <template #default="scope">
                          <el-button style="background-color: #3CB371; color: #FFFFFF" size="small" @click="modelSelect(scope.$index, scope.row)">选择</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                </el-col>
              </div>
            </el-collapse-item>
          </el-collapse>
      </el-form>
    </el-col>
    <el-col :span="12">
      <h2>数据集选择</h2>
      <br />
      <div style="width: 100%">
        <el-button type="primary" @click="queryDatasets">点击刷新</el-button>
        <el-table
            v-loading="loading"
            :data="filterDatasetTableData"
            style="width: 150%"
            :default-sort="{ prop: 'name', order: 'ascending' }"
            highlight-current-row
            max-height="600px"
        >
          <el-table-column type="index" width="20" />
          <el-table-column prop="name" label="数据集名称" width="120" sortable >
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column prop="path" property="path" label="数据集路径" width="100" />
          <el-table-column prop="date" property="date" label="上传时间" width="100" />
          <el-table-column prop="description" property="description" label="数据集描述" />
          <el-table-column align="right">
            <template #header >
              <el-input v-model="searchDataset" size="small" placeholder="查找数据集"/>
            </template>
            <template #default="scope">
              <el-button style="background-color: #3CB371; color: #FFFFFF" size="small" @click="datasetSelect(scope.$index, scope.row)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div style="margin-left: 70%;margin-top: 5%">
        <el-button type="success" @click="onSubmit">确定</el-button>
        <el-button type="danger" @click="resetForm(modelParaRef)">重置</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import type { ElTable } from 'element-plus'
import { ref,reactive,onMounted, computed} from "vue";
import {
  UploadFilled,
} from '@element-plus/icons-vue'
import type {UploadInstance} from 'element-plus'
import services from "../../utils/request.js"
import {ElMessage, ElMessageBox, FormInstance} from "element-plus";
const size = ref('')
const modelUploadRef = ref<FormInstance>()
const modelParaRef = ref<FormInstance>()
const uploadRef = ref<UploadInstance>()
const loading = ref(true)
const searchModel=ref('')
const searchDataset=ref('')
//当前模型名称、文件名、版本
let currentmodel=""
let currentName=""
let currentVersion=""
//当前数据集路径
let currentPath=""


//折叠面板
const activeNames = ref(['1','2'])
const handleChange = (val) => {
  console.log(val)
}

//查询数据库已有模型和数据集
onMounted(() => {
  queryModels()
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
const showError=(message)=>{
  ElMessage({
    showClose: true,
    message: message,
    type: 'error',
  })
}

//模型
interface Model {
  filename: string
  version: string
  mname: string
  author: string
  date: string
}
//模型数组
const modelParam=reactive({
  filename:'',
  author:window.localStorage.getItem('name'),
  mname:document.getElementById('iname'),
  version:document.getElementById('iversion'),
})

//上传新模型
const beforeUploadModel=(rawFile)=>{
  currentName=rawFile.name
  currentVersion=rawFile.version
  return true
}
const fileUploadSuccess=(res)=>{
  if(res.code==200){
    modelParam.filename=currentName
    services.post('/save',modelParam).then(res2 =>{
      console.log(res2)
    })
    showSuccess("已选择模型："+modelParam.mname)
  }else{
    showError(res.message)
  }
}
const handleRemoveModel= (uploadFile) => {
  services.post('/files/delete/model',uploadFile.name).then(res =>{
    console.log(res)
  })
}

//模型表格
const tableData =reactive({
  ModelData: [
    {
      filename: '暂无',
      version: '暂无',
      mname: '暂无',
      author: '暂无',
      date: '暂无',
    },
  ]
})

const queryModels = () => {
  services.post('/queryModels').then(res => {
    console.log(res)
    tableData.ModelData=res
  })
  loading.value = false
}
const filterModeltableData = computed(() =>
    tableData.ModelData.filter(
        (data) =>
            !searchModel.value ||
            data.mname.toLowerCase().includes(searchModel.value.toLowerCase())
    )
)
//选择已有模型
const modelSelect=(index: number,row: Model)=>{
  currentName=row.filename
  currentVersion=row.version
  currentmodel=row.mname
  showSuccess("已选择模型："+currentmodel)
}

//数据集
interface Dataset {
  name: string
  path: string
  date: string
  description: string
}
//数据集表格
const DatasetTable =reactive({
  Datasets: [
    {
      name: '暂无',
      path: '暂无',
      date: '暂无',
      description: '暂无'
    },
  ]
})

//查询数据库已有的数据集
const queryDatasets = () => {
  services.post('/queryDatasets').then(res => {
    console.log(res)
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
//选择数据集
const datasetSelect=(index: number,row: Dataset)=>{
  currentPath=row.path
  showSuccess("已选择数据集："+row.name)
}

//上传文件给分布式计算
import {useRouter} from 'vue-router'
const router=useRouter();
const onSubmit = () => {
  window.localStorage.setItem('currentName', currentName)
  window.localStorage.setItem('currentPath', currentPath)
  router.push('/distributedComputing/computing')
}

//重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

//wps
const modelInfo = reactive([
  {title: '模型名称', info: ''},
  {title: '模型版本', info: ''},
  {title: '模型文件名', info: ''},
])
const ModelDetails=()=>{
  modelInfo[0].info=currentmodel
  modelInfo[1].info=currentVersion
  modelInfo[2].info=currentName
}

</script>

<style scoped>
</style>