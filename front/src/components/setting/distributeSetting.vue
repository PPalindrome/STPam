<template>
  <el-row :gutter="20">
    <el-col :span="12">
    <br>
    <div>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span><h2>分布式框架管理</h2></span>
            <el-button class="button" type="success">新增</el-button>
          </div>
        </template>
        <el-table :data="filterComputingTableData">
          <el-table-column label="发布时间" prop="modelDate"/>
          <el-table-column label="分布式计算框架名称" prop="modelName"/>
          <el-table-column label="上传用户" prop="modelAddress"/>
          <el-table-column align="right">
            <template #header>
              <el-input v-model="computingSearch" size="small" placeholder="输入搜索"/>
            </template>
            <template #default="scope">
              <el-button size="small" type="primary" @click="ChandleEdit(scope.$index, scope.row)"
              >编辑
              </el-button
              >
              <el-button
                  size="small"
                  type="danger"
                  @click="ChandleDelete(scope.$index, scope.row)"
              >删除
              </el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
    </el-col>
    <el-col :span="12">
      <div style="margin-left: 10%">
        <h2>分布式框架示例</h2>
        <div  style="margin-top: 3%;width: 80%;height: 100%">
          <el-input type='textarea' @input='change($event)' v-model="inputResult" :autosize="{minRows:30,maxRows:30}" spellcheck="false" />
        </div>
      </div>
    </el-col>

  </el-row>
</template>

<script lang="ts" setup>
import {computed, ref, onMounted, reactive} from 'vue'
import services from "../../utils/request";
const inputResult = ref('')
const change=(e)=>{

}

// 渲染前信息获取展示
onMounted(() => {
  services.get('/pytorch.json').then(res =>{
    inputResult.value=res
  })
})


interface Model {
  modelDate: string
  modelName: string
  modelAddress: string
}

interface Computing {
  modelDate: string
  modelName: string
  modelAddress: string
}

interface WPSModel {
  wpsModelName: string,
  wpsModelParamNum: string,
  wpsModelDescribe: string,
}

// 模型查找
const modelSearch = ref('');
const filterModelTableData = computed(() =>
    modelTableData.filter(
        (data) =>
            !modelSearch.value ||
            data.modelName.toLowerCase().includes(modelSearch.value.toLowerCase())
    )
)
const MhandleEdit = (index: number, row: Model) => {
  console.log(index, row)
}
const MhandleDelete = (index: number, row: Model) => {
  console.log(index, row)
}
//分布式查找
const computingSearch = ref('');
const filterComputingTableData = computed(() =>
    computingTableData.filter(
        (data) =>
            !computingSearch.value ||
            data.modelName.toLowerCase().includes(computingSearch.value.toLowerCase())
    )
)
const ChandleEdit = (index: number, row: Computing) => {
  console.log(index, row)
}
const ChandleDelete = (index: number, row: Computing) => {
  console.log(index, row)
}

//WPS模型查找
const WPSModelSearch = ref('');
const filterWPSModelTableData = computed(() =>
    WPSModelTableData.WPSMData.filter(
        (data) =>
            !WPSModelSearch.value ||
            data.wpsModelName.toLowerCase().includes(WPSModelSearch.value.toLowerCase())
    )
)
const WPShandleEdit = (index: number, row: Computing) => {
  console.log(index, row)
}

const queryWPSModels = () => {
  services.get('/queryWPSModels').then(res => {
    console.log(res)
    WPSModelTableData.WPSMData = res
  })
  console.log('submit!')
}
// WPS模型删除
const WPShandleDelete = (index: number, row: Computing) => {
  console.log(row)
  services.post('/deleteWPSModels', row).then(res => {
    console.log(res)
  })
  console.log('submit!')
}
const WPShandleDeleteCancel = () => {
  console.log("取消删除！")
}
const modelTableData: Model[] = [
  {
    modelDate: '2022-10-13',
    modelName: 'DA-RNN',
    modelAddress: '金沙江径流模拟',
  },
  {
    modelDate: '2022-08-13',
    modelName: 'train_fleet_dygraph',
    modelAddress: '飞桨示例',
  },
]

const computingTableData: Computing[] = [
  {
    modelDate: '2022-06-13',
    modelName: 'PyTorch',
    modelAddress: 'phw',
  },
  {
    modelDate: '2022-07-25',
    modelName: 'TensorFlow',
    modelAddress: 'phw',
  },
  {
    modelDate: '2022-07-25',
    modelName: 'Paddle Paddle',
    modelAddress: 'phw',
  },
  {
    modelDate: '2022-08-18',
    modelName: 'LuoJiaNet',
    modelAddress: 'ztx',
  },
]

const WPSModelTableData = reactive({
  WPSMData: [
    {
      wpsModelUploadTime: "暂无",
      wpsModelName: '暂无',
      wpsModelParamNum: '暂无',
      wpsModelDescribe: '暂无',
    },
  ]
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 16px;
}

.item {
  margin-bottom: 5px;
}

.box-card {
  width: 100%;
  height: 200%;
}
</style>
