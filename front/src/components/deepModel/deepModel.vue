<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-form  ref="modelParaRef" style="margin-left:5%">
          <h2>{{ $t('menu.mission.title') }}</h2>
          <br />
          <el-collapse v-model="activeNames" @change="handleChange" style="width: 100%">
            <el-collapse-item name="1">
              <template #title>
                <div class="el-collapse-item-title" style="font-size:20px;font-weight:bolder">{{ $t('menu.mission.uploadNew') }}</div>
                <br /><a>{{ $t('menu.mission.warning') }}</a>
              </template>
              <div style="margin-left: 30px">
                <el-space fill style="width: 50%">
                  <el-form-item :label="$t('table.modelName')" >
                    <el-input id="imname" v-model="modelParam.mname"></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('table.modelVersion')">
                    <el-input id="iversion" v-model="modelParam.version"></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('menu.mission.command')">
                    <el-input id="icommand" v-model="modelParam.command"></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('menu.mission.outPath')">
                    <el-input id="iOutPath" v-model="modelParam.outpath"></el-input>
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
                      <el-button style="background-color: #778899; color: #FFFFFF" >{{ $t('menu.mission.selectFile') }}&#8194;<el-icon><UploadFilled/></el-icon></el-button>
                    </template>
                  </el-upload>
                  <el-button style="margin-left: 10px; background-color: #DAA520; color: #FFFFFF" >{{ $t('menu.mission.submit') }}</el-button>
                </el-row>
              </div>
            </el-collapse-item>
            <el-collapse-item name="2">
              <template #title>
                <div class="el-collapse-item-title" style="font-size:20px;font-weight:bolder">{{ $t('menu.mission.chooseModel') }}</div>
              </template>
              <div>
                <el-col span="12">
                  <el-form-item prop="hModel">
                    <el-button style="background-color: #778899; color: #FFFFFF" @click="queryModels">{{ $t('button.fresh') }}</el-button>
                    <el-table
                        v-loading="loading"
                        :data="filterModeltableData"
                        style="width: 100%"
                        :default-sort="{ prop: 'mname', order: 'ascending' }"
                        highlight-current-row
                    >
                      <el-table-column type="index" width="20" />
                      <el-table-column prop="mname" :label="$t('table.modelName')" width="80" sortable >
                        <template #default="scope">{{ scope.row.mname }}</template>
                      </el-table-column>
                      <el-table-column prop="version" property="version" :label="$t('table.modelVersion')" width="70" />
<!--                      <el-table-column prop="author" property="author" label="作者" width="60" />-->
                      <el-table-column prop="filename" property="filename" :label="$t('table.modelFile')" width="80" />
                      <el-table-column prop="date" property="date" :label="$t('button.date')" />
                      <el-table-column align="right">
                        <template #header >
                          <el-input v-model="searchModel" size="small" :placeholder="$t('button.search')"/>
                        </template>
                        <template #default="scope" >
                          <el-button style="background-color: #3CB371; color: #FFFFFF" size="small" @click="modelSelect(scope.$index, scope.row)">
                            {{ $t('button.choose') }}</el-button>
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
      <h2>{{ $t('menu.mission.chooseData') }}</h2>
      <br />
      <div style="width: 100%">
        <el-button style="background-color: #778899; color: #FFFFFF" @click="queryDatasets">{{ $t('button.fresh') }}</el-button>
        <el-table
            v-loading="loading"
            :data="filterDatasetTableData"
            style="width: 150%"
            :default-sort="{ prop: 'name', order: 'ascending' }"
            highlight-current-row
            max-height="600px"
        >
          <el-table-column type="index" width="20" />
          <el-table-column prop="name" :label="$t('table.dataName')" width="80" sortable >
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column prop="path" property="path" :label="$t('table.dataPath')" width="150" />
          <el-table-column prop="date" property="date" :label="$t('button.date')" width="80" />
          <el-table-column prop="description" property="description" :label="$t('button.description')" />
          <el-table-column align="right">
            <template #header >
              <el-input v-model="searchDataset" size="small" :placeholder="$t('button.search')"/>
            </template>
            <template #default="scope">
              <el-button style="background-color: #3CB371; color: #FFFFFF" size="small" @click="datasetSelect(scope.$index, scope.row)">
                {{ $t('button.choose') }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="margin-top: 3%">
        <el-button type="success" @click="onSubmit" style="float:right; font-size: 20px;height: 120%">{{ $t('menu.mission.mission') }}</el-button>
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
//模型命令行
let currentCommand=""

//查看模型
//关闭查看
const handleClose = (done: () => void) => {
  // ElMessageBox.confirm('Are you sure to close this dialog?')
  //     .then(() => {
  //       done()
  //     })
  //     .catch(() => {
  //       // catch error
  //     })
  done()
}

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
  command: string
  outpath:string
}
//模型数组
const modelParam=reactive({
  filename:'',
  author:window.localStorage.getItem('name'),
  mname:document.getElementById('iname'),
  version:document.getElementById('iversion'),
  command:document.getElementById('icommand'),
  outpath:document.getElementById('iOutPath'),
})
let outPath:any
//上传新模型
const beforeUploadModel=(rawFile)=>{
  currentName=rawFile.name
  currentVersion=rawFile.version
  currentCommand=rawFile.command
  outPath=modelParam.outpath
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

//查看模型
const modelCheck=(index: number,row: Model)=>{
  ElMessageBox({

  })
}

//选择已有模型
const modelSelect=(index: number,row: Model)=>{
  currentName=row.filename
  currentVersion=row.version
  currentmodel=row.mname
  currentCommand=row.command
  outPath=row.outpath
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
const datasource={
  pageSize:'',
  currentPage:'',
  searchData:''
}
const currentPage = ref(1)
const pageSize = ref(10)
const total=ref(100)
//查询数据库已有的数据集
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
  localStorage.setItem('currentName', currentName)
  localStorage.setItem('currentPath', currentPath)
  localStorage.setItem('currentCommand',currentCommand)
  localStorage.setItem('outpath',outPath)
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