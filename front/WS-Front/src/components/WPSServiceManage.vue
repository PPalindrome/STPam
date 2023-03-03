<template>
  <div style="margin-top: 3%">
    <el-row>
      <el-col :span="12"><h2>WPS共享模型管理</h2></el-col>
      <el-col :span="12">
        <el-button type="primary" @click="queryWPSModels">点击刷新</el-button>
      </el-col>
    </el-row>
    <br>
    <!--    <el-button type="success">新增</el-button>-->
    <el-row>
      <el-table v-loading="loading" :data="filterWPSModelTableData" style="width: 60%" :default-sort="{ prop: 'id', order: 'ascending' }">
        <el-table-column prop="muploadtime" label="添加日期" width="120" sortable>
          <template #default="scope">{{ scope.row.muploadtime }}</template>
        </el-table-column>
        <el-table-column prop="mname" property="mname" label="模型名称" width="120"/>
        <el-table-column prop="mparam" property="mparam" label="模型参数个数" width="120"/>
        <el-table-column prop="mdescribe" property="mdescribe" label="模型描述" show-overflow-tooltip/>
        <el-table-column align="right">
          <template #header>
            <el-input v-model="WPSModelSearch" size="small" placeholder="输入模型关键字搜索"/>
          </template>
          <template #default="scope">
            <el-button size="small" type="primary" @click="WPShandleDownload(scope.$index, scope.row)">下载</el-button>
            <el-popconfirm
                confirm-button-text="Yes"
                cancel-button-text="No"
                :icon="InfoFilled"
                icon-color="#626AEF"
                title="确定要删除吗？"
                @confirm="WPShandleDelete(scope.$index, scope.row)"
                @cancel="WPShandleDeleteCancel"
            >
              <template #reference>
                <el-button size="small" type="danger">删除</el-button>
              </template>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>
    </el-row>
  </div>

</template>

<script setup lang="ts">
import {ref, reactive, computed, onMounted, onUpdated} from "vue";
import request from "../utils/request.js"
import router from "../router/index.js"
import {InfoFilled} from '@element-plus/icons-vue'

const loading = ref(true)
// onUpdated(() => {
//   queryWPSModels()
// })

onMounted(() => {
  queryWPSModels()
})

interface WPSModel {
  muploadtime: string,
  mname: string,
  mparam: number,
  mdescribe: string,
  muploadlocation: string
}

//WPS模型查找
const WPSModelSearch = ref('');
const filterWPSModelTableData = computed(() =>
    WPSModelTableData.WPSMData.filter(
        (data) =>
            !WPSModelSearch.value ||
            data.mname.toLowerCase().includes(WPSModelSearch.value.toLowerCase())
    )
)
const WPShandleDownload = (index: number, row: WPSModel) => {
  console.log(index, row.muploadlocation)
  // router.push(row.muploadlocation)
  window.location.href = row.muploadlocation
}

const queryWPSModels = () => {
  request.get('/queryWPSModels').then(res => {
    console.log(res)
    WPSModelTableData.WPSMData = res
  })
  console.log('submit!')
  loading.value = false
}


// WPS模型删除
const WPShandleDelete = (index: number, row: WPSModel) => {
  console.log('row:!!!!!', row.muploadlocation, "index:!!!!!!", index)
  request.post('/deleteWPSModels', row).then(res => {
    console.log(res)
    queryWPSModels()
  })
  let filename=row.muploadlocation
  console.log("文件名：",filename)
  request.post("/files/delete",filename).then(res=>{
    console.log(res)
  })
  console.log('delete submit!')
}
const WPShandleDeleteCancel = () => {
  console.log("取消删除！")
}

const WPSModelTableData = reactive({
  WPSMData: [
    {
      muploadtime: "暂无",
      mname: '暂无',
      mparam: '暂无',
      mdescribe: '暂无',
      muploadlocation: ''
    },
  ]
})

</script>

<style scoped>

</style>
