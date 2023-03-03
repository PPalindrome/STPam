<template>
  <div>
    <iframe src="http://172.27.187.122:8066" width="100%" height=800px frameborder="0" scrolling="no"></iframe>
  </div>
</template>

<script setup>

</script>

<style>

</style>

 <!--<template>-->
<!--  <div>-->
<!--    <el-form :model="processingForm">-->
<!--      <el-row :gutter="10">-->
<!--        <el-col :span="10">-->
<!--          <h2>选择共享数据集</h2>-->
<!--          <el-form-item>-->
<!--            <el-table-->
<!--                ref="multipleTableRef"-->
<!--                :data="tableData"-->
<!--                style="width: 100%"-->
<!--                height="100%"-->
<!--                @selection-change="handleSelectionChange"-->
<!--                :default-sort="{ prop: 'date', order: 'descending' }"-->
<!--            >-->
<!--              <el-table-column type="selection" width="55"/>-->
<!--              <el-table-column prop="date" label="数据集日期" width="120" sortable>-->
<!--                <template #default="scope">{{ scope.row.date }}</template>-->
<!--              </el-table-column>-->
<!--              <el-table-column prop="name" property="name" label="数据集类型" width="120"/>-->
<!--              <el-table-column prop="address" property="address" label="数据集大小" show-overflow-tooltip/>-->
<!--            </el-table>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <h2>选择共享对象主机</h2>-->
<!--          <el-form-item label="主机IP地址">-->
<!--            <el-input v-model="processingForm.ip" style="width: 50%"/>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="主机名称（编号）">-->
<!--            <el-select v-model="processingForm.number" placeholder="请选择你要共享数据的主机">-->
<!--              <el-option label="主机1号" value="1"/>-->
<!--              <el-option label="主机2号" value="2"/>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--      <div style="margin-top: 20px">-->
<!--        &lt;!&ndash;          <el-button @click="toggleSelection([tableData[1], tableData[2]])" type="primary">确认选择</el-button>&ndash;&gt;-->
<!--        <el-button @click="tableCommit" type="primary">确认选择</el-button>-->
<!--        <el-button @click="toggleSelection(),resetForm">重新选择</el-button>-->
<!--      </div>-->
<!--    </el-form>-->

<!--    <br>-->


<!--    <el-row :gutter="10">-->
<!--      <h2>WPS算法上传共享</h2>-->
<!--      <el-col>-->
<!--        <el-upload-->
<!--            ref="uploadModelService"-->
<!--            class="upload-demo"-->
<!--            action="http://localhost:8081/wpsmodel/upload"-->
<!--            :auto-upload="false"-->
<!--        >-->
<!--          <template #trigger>-->
<!--            <el-button type="primary">选择文件</el-button>-->
<!--          </template>-->
<!--          <el-button class="ml-3" type="success" @click="submitUpload" style="margin-left: 5px">-->
<!--            上传至服务器-->
<!--          </el-button>-->
<!--          <template #tip>-->
<!--            <div class="el-upload__tip text-red">-->
<!--              请上传python模型文件，文件大小不超过10MB! 如果有数据集请一并上传！-->
<!--            </div>-->
<!--          </template>-->
<!--        </el-upload>-->
<!--      </el-col>-->
<!--      <el-col :span="10">-->
<!--        <h2>算法基本信息（增加）</h2>-->
<!--        <el-form :model="WPSModelForm" label-width="120px">-->
<!--          <el-form-item label="WPS算法名称">-->
<!--            <el-input v-model="WPSModelForm.wpsModelName"/>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="WPS算法参数个数">-->
<!--            <el-input v-model="WPSModelForm.wpsModelParamNum"/>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="WPS算法描述">-->
<!--            <el-input v-model="WPSModelForm.wpsModelDescribe"/>-->
<!--          </el-form-item>-->
<!--          <el-form-item>-->
<!--            <el-button type="primary" @click="WPSModelSubmit">添加</el-button>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--      </el-col>-->
<!--      <el-col :span="14">-->
<!--        <h2>算法基本信息（查看）</h2>-->
<!--        <el-button type="primary" @click="queryWPSModels">点击刷新信息</el-button>-->
<!--        <br/>-->
<!--        <el-table :data="filterWPStableData" style="width: 100%" :default-sort="{ prop: 'id', order: 'ascending' }">-->
<!--          <el-table-column prop="wpsModelUploadTime" label="添加日期" width="120" sortable>-->
<!--            <template #default="scope">{{ scope.row.wpsModelUploadTime }}</template>-->
<!--          </el-table-column>-->
<!--          <el-table-column prop="wpsModelName" property="wpsModelName" label="模型名称" width="120"/>-->
<!--          <el-table-column prop="wpsModelParamNum" property="wpsModelParamNum" label="模型参数个数" width="120"/>-->
<!--          <el-table-column prop="wpsModelDescribe" property="wpsModelDescribe" label="模型描述" show-overflow-tooltip/>-->
<!--          <el-table-column align="right">-->
<!--            <template #header>-->
<!--              <el-input v-model="searchWPSModel" size="small" placeholder="请输入模型名称关键字查找"/>-->
<!--            </template>-->
<!--            &lt;!&ndash;          <template #default="scope">&ndash;&gt;-->
<!--            &lt;!&ndash;            <el-button size="small" @click="handleEdit(scope.$index, scope.row)"&ndash;&gt;-->
<!--            &lt;!&ndash;            >Edit</el-button&ndash;&gt;-->
<!--            &lt;!&ndash;            >&ndash;&gt;-->
<!--            &lt;!&ndash;            <el-button&ndash;&gt;-->
<!--            &lt;!&ndash;                size="small"&ndash;&gt;-->
<!--            &lt;!&ndash;                type="danger"&ndash;&gt;-->
<!--            &lt;!&ndash;                @click="handleDelete(scope.$index, scope.row)"&ndash;&gt;-->
<!--            &lt;!&ndash;            >Delete</el-button&ndash;&gt;-->
<!--            &lt;!&ndash;            >&ndash;&gt;-->
<!--            &lt;!&ndash;          </template>&ndash;&gt;-->
<!--          </el-table-column>-->
<!--        </el-table>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--    <el-row>-->
<!--      <el-form-->
<!--          ref="wpsFormRef"-->
<!--          :model="wpsRequestForm"-->
<!--          :rules="wpsRules"-->
<!--          label-width="120px"-->
<!--          class="demo-ruleForm"-->
<!--          :size="formSize"-->
<!--          status-icon-->
<!--      >-->
<!--        <h2>WPS算法共享服务</h2>-->
<!--        <el-col>-->
<!--          <el-form-item label="选择已有算法模型名称" prop="modelName">-->
<!--            <el-select v-model="wpsRequestForm.modelName" placeholder="addTest">-->
<!--              <el-option label="addTest" value="addTest"/>-->
<!--              <el-option label="lstmTest" value="lstmTest"/>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col>-->
<!--          <el-form-item label="模型训练参数（可以为空）" prop="params">-->
<!--            <el-tooltip-->
<!--                class="box-item"-->
<!--                effect="dark"-->
<!--                content="多个参数请按单个空格分割，如“1 2”"-->
<!--                placement="top-start"-->
<!--            >-->
<!--              <el-input v-model="wpsRequestForm.params"/>-->
<!--            </el-tooltip>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col>-->
<!--          <el-form-item>-->
<!--            <el-button type="primary" @click="submitWPSForm" style="margin-top: 20%">发送</el-button>-->
<!--            &lt;!&ndash;        <el-button @click="resetForm(wpsFormRef)">重置</el-button>&ndash;&gt;-->
<!--          </el-form-item>-->
<!--        </el-col>-->

<!--      </el-form>-->
<!--    </el-row>-->
<!--    <el-row :gutter="10">-->

<!--      <el-col :span="10">-->
<!--        <h3>WPS请求XML文档</h3>-->
<!--        <el-input v-model="wpsRequest" type="textarea" :autosize="{ minRows: 10, maxRows: 30 }" spellcheck="false"/>-->
<!--      </el-col>-->

<!--      <el-col :span="10">-->
<!--        <h3>WPS响应XML文档</h3>-->
<!--        &lt;!&ndash;        {{ wpsReponse }}&ndash;&gt;-->
<!--        <el-input v-model="wpsReponse" type="textarea" :autosize="{ minRows: 10, maxRows: 30 }" spellcheck="false"/>-->
<!--      </el-col>-->
<!--    </el-row>-->

<!--  </div>-->
<!--</template>-->

<!--<script setup lang="ts">-->
<!--import {ref, reactive, Ref, computed, onMounted,onBeforeMount} from 'vue'-->
<!--import type {ElTable, FormInstance} from 'element-plus'-->


<!--// 渲染前信息获取展示-->
<!--onMounted(() => {-->
<!--  queryWPSModels()-->
<!--})-->


<!--//表单整体信息-->
<!--const processingForm = reactive({-->
<!--  multipleSelection: ref<User[]>([]),-->
<!--  ip: '',-->
<!--  number: '',-->

<!--})-->

<!--// 数据集内容-->

<!--interface User {-->
<!--  date: string-->
<!--  name: string-->
<!--  address: string-->
<!--}-->

<!--const tableData: User[] = [-->
<!--  {-->
<!--    date: '2021-05-03',-->
<!--    name: '水文',-->
<!--    address: '20GB',-->
<!--  },-->
<!--  {-->
<!--    date: '2021-05-02',-->
<!--    name: '遥感',-->
<!--    address: '120GB',-->
<!--  },-->
<!--  {-->
<!--    date: '2021-05-04',-->
<!--    name: '图谱',-->
<!--    address: '90GB',-->
<!--  },-->
<!--]-->
<!--const formSize = ref('default')-->
<!--// 数据集定义 选择-->
<!--const multipleTableRef = ref<InstanceType<typeof ElTable>>()-->
<!--const multipleSelection = ref<User[]>([])-->
<!--const toggleSelection = (rows?: User[]) => {-->
<!--  if (rows) {-->
<!--    rows.forEach((row) => {-->
<!--      // TODO: improvement typing when refactor table-->
<!--      // eslint-disable-next-line @typescript-eslint/ban-ts-comment-->
<!--      // @ts-expect-error-->
<!--      multipleTableRef.value!.toggleRowSelection(row, undefined)-->
<!--    })-->
<!--  } else {-->
<!--    multipleTableRef.value!.clearSelection()-->
<!--  }-->
<!--}-->

<!--const handleSelectionChange = (val: User[]) => {-->
<!--  multipleSelection.value = val-->
<!--}-->

<!--//选择数据提交-->
<!--const tableCommit = () => {-->
<!--  //测试共享数据集输出-->
<!--  for (var i = 0; i < multipleSelection.value.length; i++) {-->
<!--    console.log('共享数据集输出', multipleSelection.value[i].date)-->
<!--  }-->
<!--  processingForm.multipleSelection = multipleSelection.value-->
<!--  //测试表单中的共享数据集输出-->
<!--  console.log('表单中的共享数据集输出：', processingForm.multipleSelection[1])-->
<!--  //测试整个表单输出-->
<!--  console.log('表单输出：', processingForm)-->
<!--}-->

<!--//重置表单-->
<!--const resetForm = (formEl: FormInstance | undefined) => {-->
<!--  console.log("reset fail")-->
<!--  if (!formEl) return-->
<!--  formEl.resetFields()-->
<!--}-->

<!--const onSubmit = () => {-->
<!--  console.log('submit!')-->
<!--}-->

<!--// 上传模型至WPS服务器并发布为服务-->
<!--import {ElMessage, genFileId} from 'element-plus'-->
<!--import type {UploadInstance, UploadProps, UploadRawFile} from 'element-plus'-->
<!--import services from "../../utils/request";-->
<!--import axios from "axios";-->

<!--const WPSModelForm = reactive({-->
<!--  wpsModelName: 'addTest',-->
<!--  wpsModelParamNum: '2',-->
<!--  wpsModelDescribe: '加法，需要两个参数',-->
<!--})-->

<!--const WPSModelSubmit = () => {-->
<!--  services.post('/addWPSModels', WPSModelForm).then(res => {-->
<!--    console.log(res)-->
<!--    // wpsReponse = res as Ref-->
<!--    // console.log(wpsReponse)-->
<!--  })-->
<!--  console.log('submit!')-->
<!--}-->

<!--// WPS模型信息查询-->
<!--const searchWPSModel = ref('')-->
<!--const filterWPStableData = computed(() =>-->
<!--    WPStableData.WPSMData.filter(-->
<!--        (data) =>-->
<!--            !searchWPSModel.value ||-->
<!--            data.wpsModelName.toLowerCase().includes(searchWPSModel.value.toLowerCase())-->
<!--    )-->
<!--)-->
<!--const handleEdit = (index: number, row: User) => {-->
<!--  console.log(index, row)-->
<!--}-->
<!--const handleDelete = (index: number, row: User) => {-->
<!--  console.log(index, row)-->
<!--}-->
<!--const WPStableData = reactive({-->
<!--  WPSMData: [-->
<!--    {-->
<!--      id: "待查询",-->
<!--      wpsModelName: '待查询',-->
<!--      wpsModelParamNum: '待查询',-->
<!--      wpsModelDescribe: '待查询',-->
<!--      wpsModelUploadTime:'待查询',-->
<!--    },-->
<!--  ]-->
<!--})-->

<!--const queryWPSModels = () => {-->
<!--  services.get('/queryWPSModels', WPSModelForm).then(res => {-->
<!--    console.log(res)-->
<!--    WPStableData.WPSMData = res-->
<!--  })-->
<!--  console.log('submit!')-->
<!--}-->

<!--// 上传py文件模型和数据集-->
<!--const uploadModelService = ref<UploadInstance>()-->

<!--// const handleExceed: UploadProps['onExceed'] = (files) => {-->
<!--//   upload.value!.clearFiles()-->
<!--//   const file = files[0] as UploadRawFile-->
<!--//   file.uid = genFileId()-->
<!--//   upload.value!.handleStart(file)-->
<!--// }-->

<!--const submitUpload = () => {-->
<!--  uploadModelService.value!.submit()-->
<!--}-->
<!--//文件上传-->
<!--const showSuccess = (message) => {-->
<!--  ElMessage({-->
<!--    showClose: true,-->
<!--    message: message,-->
<!--    type: 'success',-->
<!--  })-->
<!--}-->
<!--const showError = (message) => {-->
<!--  ElMessage({-->
<!--    showClose: true,-->
<!--    message: message,-->
<!--    type: 'error',-->
<!--  })-->
<!--}-->
<!--const fileUploadSuccess = (res) => {-->
<!--  if (res.code == 200) {-->
<!--    showSuccess(res.message)-->
<!--  } else {-->
<!--    showError(res.message)-->
<!--  }-->
<!--}-->
<!--const beforeUploadModel = (rawFile) => {-->
<!--  console.log('文件准备上传！')-->
<!--  // if(!rawFile.name.endsWith('.py')){-->
<!--  //   showError("模型文件不符合格式")-->
<!--  //   return false-->
<!--  // }else if(rawFile.size/1024/1024/8>10){-->
<!--  //   showError("模型文件过大，无法上传")-->
<!--  //   return false-->
<!--  // }-->
<!--  // mName=rawFile.name-->
<!--  // return true-->
<!--}-->
<!--const handleRemoveModel = (uploadFile) => {-->
<!--  // if(uploadFile.status==='success') {-->
<!--  services.post('/files/delete/model', uploadFile.name).then(res => {-->
<!--    console.log(res)-->
<!--  })-->
<!--  // }-->
<!--}-->
<!--// 使用WPS算法服务-->
<!--// wps算法提交表单信息-->
<!--const wpsRequestForm = reactive({-->
<!--  modelName: 'addTest',-->
<!--  params: '',-->
<!--  number: '',-->

<!--})-->

<!--//算法返回信息-->
<!--const wpsRequest = ref('暂无发送文档信息')-->
<!--const wpsReponse = ref('暂无返回文档信息')-->
<!--const submitWPSForm = () => {-->
<!--  services.post('/runWPSModel', wpsRequestForm).then(res => {-->
<!--    // console.log(res)-->
<!--    wpsRequest.value = res[0]-->
<!--    wpsReponse.value = res[1]-->
<!--  })-->
<!--  console.log('submit!')-->
<!--}-->

<!--</script>-->

<!--<style scoped>-->

<!--</style>-->
