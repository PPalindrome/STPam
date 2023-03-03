<template>
  <div style="margin-top: 3%">
    <el-row :gutter="50">
      <el-col :span="12">
        <el-form
            ref="wpsFormRef"
            :model="wpsRequestForm"
            :rules="wpsRules"

            class="demo-ruleForm"
            :size="formSize"
            status-icon
        >
          <h2>WPS算法共享服务</h2>
          <br>
          <el-form-item label="选择已有算法模型名称" prop="modelName">
            <el-select v-model="wpsRequestForm.modelName" placeholder="addTest">
              <el-option label="addTest" value="addTest"/>
              <el-option label="lstmTest" value="lstmTest"/>
            </el-select>
          </el-form-item>
          <br>
          <el-form-item label="模型训练参数（可以为空）" prop="params">
              <el-tooltip
                  class="box-item"
                  effect="customized"
                  placement="bottom-start"
                  trigger="hover"
                  hide-after="200"
                  trigger-keys="['Enter']"
              >
                <template #content> 多个参数请按单个空格分割，如“1 2” </template>
                <el-input v-model="wpsRequestForm.params"/>
              </el-tooltip>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitWPSForm">发送</el-button>
            <!--        <el-button @click="resetForm(wpsFormRef)">重置</el-button>-->
          </el-form-item>

        </el-form>

        <h3>WPS请求XML文档</h3>
        <el-input v-model="wpsRequest" type="textarea" :autosize="{ minRows: 10, maxRows: 30 }" spellcheck="false"/>

        <h3>WPS响应XML文档</h3>
        <el-input v-model="wpsReponse" type="textarea" :autosize="{ minRows: 10, maxRows: 30 }" spellcheck="false"/>
      </el-col>
      <el-col :span="12">
        <el-row>
          <el-col :span="12"><h2>算法基本信息（查看）</h2></el-col>
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
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>


  </div>
</template>

<script setup lang="ts">
import {ref, reactive, onMounted, computed} from "vue";
import request from "../utils/request.js";

const loading = ref(true)
const formSize = ref('default')
// 渲染前信息获取展示
onMounted(() => {
  queryWPSModels()
})
// WPS模型信息查询
const searchWPSModel = ref('')
const filterWPStableData = computed(() =>
    WPStableData.WPSMData.filter(
        (data) =>
            !searchWPSModel.value ||
            data.mname.toLowerCase().includes(searchWPSModel.value.toLowerCase())
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
      mid: "待查询",
      mname: '待查询',
      mparam: '待查询',
      mdescribe: '待查询',
      muploadtime: '待查询',
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

// 使用WPS算法服务
// wps算法提交表单信息
const wpsRequestForm = reactive({
  modelName: 'addTest',
  params: '',
  number: '',

})
//算法返回信息
const wpsRequest = ref('暂无发送文档信息')
const wpsReponse = ref('暂无返回文档信息')
const submitWPSForm = () => {
  request.post('/runWPSModel', wpsRequestForm).then(res => {
    // console.log(res)
    wpsRequest.value = res[0]
    wpsReponse.value = res[1]
  })
  console.log('submit!')
}
</script>

<style>
.el-popper.is-customized {
  /* Set padding to ensure the height is 32px */
  color: rgba(124, 7, 7, 0.97);
  font-size: 16px;
  padding: 6px 12px;
  background: linear-gradient(90deg, rgb(159, 229, 151), rgb(204, 229, 129));
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #b2e68d, #bce689);
  right: 0;
}
</style>
