<template>
  <el-row :gutter="20">
    <el-col :span="18">
    <div>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span><h2>模型管理</h2></span>
            <el-button class="button" type="success">新增</el-button>
          </div>
        </template>
        <div>
          <el-table :data="filterModelTableData">
            <el-table-column label="发布时间" prop="modelDate"/>
            <el-table-column label="模型名称" prop="modelName"/>
            <el-table-column label="应用场景" prop="modelAddress"/>
            <el-table-column align="right">
              <template #header>
                <el-input v-model="modelSearch" size="small" placeholder="输入搜索"/>
              </template>
              <template #default="scope">
                <el-button size="small" type="primary" @click="MhandleEdit(scope.$index, scope.row)"
                >编辑
                </el-button
                >
                <el-button
                    size="small"
                    type="danger"
                    @click="MhandleDelete(scope.$index, scope.row)"
                >删除
                </el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
    <br>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import {computed, ref, onMounted, reactive} from 'vue'
import services from "../../utils/request";

// 渲染前信息获取展示
onMounted(() => {
  queryWPSModels()
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
    modelDate: '2022-07-25',
    modelName: 'Paddle Paddle',
    modelAddress: 'python -m paddle.distributed.launch --gpus=0,1',
  },
  {
    modelDate: '2022-07-25',
    modelName: 'LuoJiaNet',
    modelAddress: 'python -c "import luojianet_ms;luojianet_ms.run_check()"',
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
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 60%;
}
</style>
