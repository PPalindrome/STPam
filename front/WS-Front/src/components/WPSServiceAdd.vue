<template>
  <div style="margin-top: 3%">
    <el-row :gutter="50">
      <el-col :span="12">
        <h2>WPS算法服务共享</h2>
        <br/>
        <el-form :model="WPSModelForm" label-width="120px">
          <el-form-item label="模型及数据集">
            <el-upload
                v-model:file-list="fileList"
                :headers="headers"
                ref="uploadModelService"
                class="upload-demo"
                action="http://localhost:8088/files/upload"
                :auto-upload="false"
                :before-upload="beforeUploadModel"
                :on-success="fileUploadSuccess"
                :on-remove="handleRemoveModel"
                :on-preview="handlePreview"
                :before-remove="beforeRemove"
                :limit="3"
                :on-exceed="handleExceed"
                multiple
            >
              <template #trigger>
                <el-button type="primary">选择文件</el-button>
              </template>
              <el-button class="ml-3" type="success" @click="submitUpload" style="margin-left: 5px">
                上传至服务器
              </el-button>
              <template #tip>
                <div class="el-upload__tip text-red">
                  请上传python模型文件，文件大小不超过10MB! 数据集请在右侧上传！<br>
                </div>
              </template>
            </el-upload>
          </el-form-item>

          <el-form-item label="WPS算法名称">
            <el-input v-model="WPSModelForm.mname"/>
          </el-form-item>
          <el-form-item label="WPS算法参数个数">
            <el-input v-model="WPSModelForm.mparam"/>
          </el-form-item>
          <el-form-item label="WPS算法描述">
            <el-input type="textarea" v-model="WPSModelForm.mdescribe" :autosize="{ minRows: 3, maxRows: 6 }" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="WPSModelSubmit">添加</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="12">
        <el-row>
          <el-col :span="12">
            <h2>算法数据集上传</h2>
          </el-col>
          <el-col :span="12">
            <el-button type="primary" @click="queryWPSModels">点击刷新信息</el-button>
          </el-col>
        </el-row>
        <br/>
        <el-table v-loading="loading" :data="filterWPStableData" :default-sort="{ prop: 'id', order: 'ascending' }">
          <el-table-column prop="muploadtime" label="添加日期" width="120" sortable>
            <template #default="scope">{{ scope.row.muploadtime }}</template>
          </el-table-column>
          <el-table-column prop="mname" property="mname" label="模型名称" width="120"/>
          <el-table-column prop="mparam" property="mparam" label="模型参数个数" width="120"/>
          <el-table-column prop="mdescribe" property="mdescribe" label="模型描述" show-overflow-tooltip/>
          <el-table-column align="right">
            <template #header>
              <el-input v-model="searchWPSModel" size="small" placeholder="请输入模型名称关键字查找"/>
            </template>
            <template #default="scope">
              <el-upload
                  class="upload-demo"
                  :action="baserUrl+scope.row.mname"
                  multiple
                  :on-preview="handlePreview"
                  :on-remove="handleRemoveDataset"
                  :before-remove="beforeRemove"
                  :limit="3"
                  :on-exceed="handleExceed"
              >
                <el-tooltip
                    class="box-item"
                    effect="dark"
                    placement="top"
                    trigger="hover"
                    hide-after="200"
                    trigger-keys="['Enter']"
                >
                  <template #content>数据集单次最多支持上传三个文件！每个大小不超过3GB！</template>
                <el-button type="primary">选择数据集上传</el-button>
<!--                <template #tip>-->
<!--                  <div class="el-upload__tip">-->
<!--                    数据集单次最多支持上传三个文件！每个大小不超过3GB！-->
<!--                  </div>-->
<!--                </template>-->
                </el-tooltip>
              </el-upload>

            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, onMounted, onActivated, computed} from "vue";
import {ElMessage, genFileId, ElMessageBox} from 'element-plus'
import type {UploadInstance, UploadProps, UploadRawFile, UploadUserFile} from 'element-plus'
import request from "../utils/request.js";
import axios from "axios";

const baserUrl="http://localhost:8088/files/uploadDataset/"
// 渲染前信息获取展示
onMounted(() => {
  queryWPSModels()
})
// onActivated(()=>{
//   queryWPSModels()
// })
const loading = ref(true)

const headers = reactive({
  Authorization: localStorage.token,
})
// 文件上传
const fileList = ref<UploadUserFile[]>([])

// 上传py文件模型和数据集
const uploadModelService = ref<UploadInstance>()

// const handleExceed: UploadProps['onExceed'] = (files) => {
//   upload.value!.clearFiles()
//   const file = files[0] as UploadRawFile
//   file.uid = genFileId()
//   upload.value!.handleStart(file)
// }

const submitUpload = () => {
  uploadModelService.value!.submit()
}

const handleRemoveModel = (uploadFile) => {
  // if(uploadFile.status==='success') {
  request.post('/files/delete', uploadFile.name).then(res => {
    console.log(res)
  })
  // }
}


const handlePreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log(uploadFile)
}

const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(
      `最多上传3个文件, 本次你选择了 ${files.length} 个文件, 加起来总共 ${
          files.length + uploadFiles.length
      } `
  )
}

const beforeRemove: UploadProps['beforeRemove'] = (uploadFile, uploadFiles) => {
  return ElMessageBox.confirm(
      `确定取消上传 ${uploadFile.name} ?`
  ).then(
      () => true,
      () => false
  )
}

const WPSModelForm = reactive({
  mname: 'addTest',
  mparam: '2',
  mdescribe: '加法，需要两个参数',
  muploadlocation: '',
})

const WPSModelSubmit = () => {
  request.post('/addWPSModels', WPSModelForm).then(res => {
    console.log(res)
    // wpsReponse = res as Ref
    // console.log(wpsReponse)
    queryWPSModels();
  })

  console.log('submit!')
}


//文件上传
const showSuccess = (message) => {
  ElMessage({
    showClose: true,
    message: message,
    type: 'success',
  })
}
const showError = (message) => {
  ElMessage({
    showClose: true,
    message: message,
    type: 'error',
  })
}
const fileUploadSuccess = (res) => {
  if (res.code == 200) {
    showSuccess(res.message)
    console.log(res.obj)
    WPSModelForm.muploadlocation = res.obj
  } else {
    showError(res.message)
  }
}
const beforeUploadModel = (rawFile) => {
  console.log('文件准备上传！')
  // if(!rawFile.name.endsWith('.py')){
  //   showError("模型文件不符合格式")
  //   return false
  // }else if(rawFile.size/1024/1024/8>10){
  //   showError("模型文件过大，无法上传")
  //   return false
  // }
  // mName=rawFile.name
  // return true
}

//数据集上传取消
const handleRemoveDataset=(uploadFile)=>{
  request.post('/files/deleteDataset', uploadFile.name).then(res => {
    console.log(res)
  })
}

// 使用WPS算法服务
// wps算法提交表单信息
const wpsRequestForm = reactive({
  modelName: 'addTest',
  params: '',
  number: '',

})


// WPS模型信息查询
const searchWPSModel = ref('')
const filterWPStableData = computed(() =>
    WPStableData.WPSMData.filter(
        (data) =>
            !searchWPSModel.value ||
            data.wpsModelName.toLowerCase().includes(searchWPSModel.value.toLowerCase())
    )
)
// const handleEdit = (index: number, row: User) => {
//   console.log(index, row)
// }
// const handleDelete = (index: number, row: User) => {
//   console.log(index, row)
// }
const WPStableData = reactive({
  WPSMData: [
    {
      id: "待查询",
      wpsModelName: '待查询',
      wpsModelParamNum: '待查询',
      wpsModelDescribe: '待查询',
      wpsModelUploadTime: '待查询',
    },
  ]
})

const queryWPSModels = () => {
  request.get('/queryWPSModels').then(res => {
    console.log(res)
    WPStableData.WPSMData = res
  })
  console.log('submit!')
  loading.value = false
}

</script>

<style scoped>

</style>
