<template>
  <el-row :gutter="20">
    <el-col :span="18">
      <template #header>
        <div class="card-header">
          <span><h2>模型管理</h2></span>
        </div>
      </template>
      <div style="width: 80%">
        <el-table :data="filterModelTableData">
          <el-table-column label="模型名称" prop="mname" style="font-size: 20px"/>
          <el-table-column label="版本" prop="version"/>
          <el-table-column label="作者" prop="author"/>
          <el-table-column label="模型文件名" prop="filename"/>
          <el-table-column label="上传时间" prop="date"/>
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
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import {computed, ref, onMounted, reactive} from 'vue'
import services from "../../utils/request";

// 渲染前信息获取展示
onMounted(() => {
  queryModels()
})

// 模型查找
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
const modelSearch = ref('');
const queryModels = () => {
  services.post('/queryModels').then(res => {
    console.log(res)
    tableData.ModelData=res
  })
}
const filterModelTableData = computed(() =>
    tableData.ModelData.filter(
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
