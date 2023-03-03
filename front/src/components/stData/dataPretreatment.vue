<template>
  <el-row :gutter="20">
    <el-col :span="15">
      <div
          class="inline-flex"
          style="margin:5% 5%;"
          :style="{
          boxShadow: `var(${'--el-box-shadow-light'})`,
        }"
      >
        <br><br>
        <div style="text-align: center">
          <el-transfer
              v-model="rightValue"
              style="text-align: left; display: inline-block"
              filterable
              :left-default-checked="[2, 3]"
              :right-default-checked="[1]"
              :titles="['源数据', '预处理数据']"
              :button-texts="['删除', '选择']"
              :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}',
        }"
              :data="data"
              @change="handleChange"
          >
            <template #default="{ option }">
              <span>{{ option.key }} - {{ option.label }}</span>
            </template>
            <template #left-footer>
              <el-button class="transfer-footer" size="small">操作</el-button>
            </template>
            <template #right-footer>
              <el-button class="transfer-footer" size="small">确定</el-button>
            </template>
          </el-transfer>
        </div>
        <br><br>
      </div>
<!--      <el-steps :active="active" finish-status="success">-->
<!--        <el-step title="数据归一化" />-->
<!--        <el-step title="时间插值" />-->
<!--        <el-step title="空间插值" />-->
<!--        <el-step title="对数平滑" />-->
<!--        <el-step title="拼接" />-->
<!--      </el-steps>-->
<!--      <el-button style="margin-top: 12px" type="primary" @click="next">下一步</el-button>-->
<!--      <el-button style="margin-top: 12px" type="success">导出</el-button>-->
    </el-col>

    <el-col :span="9">
      <div style="margin-top: 10%;font-size: 25px">
        <h1>数据预处理方法选择</h1>
        <el-divider />
        <el-checkbox
            v-model="checkAll"
            :indeterminate="isIndeterminate"
            @change="handleCheckAllChange"
        >全选</el-checkbox
        >
        <el-divider style="width: 60%"/>
        <el-checkbox-group
            v-model="checkedProcessing"
            @change="handleCheckedCitiesChange"
        >
          <el-checkbox v-for="process in processing" :key="process" :label="process">{{
              process
            }}</el-checkbox>
        </el-checkbox-group>
        <el-button style="margin-top: 12px" type="success" @click="processingSubmit">确认</el-button>

      </div>
    </el-col>
  </el-row>

</template>


<script lang="ts" setup>
import {ref} from 'vue'
import type {VNode, VNodeProps} from 'vue'

interface Option {
  key: number
  label: string
  disabled: boolean
}

const generateData = (): Option[] => {
  const data: Option[] = []
  for (let i = 1; i <= 15; i++) {
    data.push({
      key: i,
      label: `水位数据 ${i}`,
      disabled: i % 4 === 0,
    })
  }
  return data
}

const data = ref(generateData())
const rightValue = ref([1])
const leftValue = ref([1])

const renderFunc = (
    h: (type: string, props: VNodeProps | null, children?: string) => VNode,
    option: Option
) => {
  return h('span', null, option.label)
}
const handleChange = (
    value: number | string,
    direction: 'left' | 'right',
    movedKeys: string[] | number[]
) => {
  console.log(value, direction, movedKeys)
}

// //步骤条
// const active = ref(0)
//
// const next = () => {
//   if (active.value++ > 4) active.value = 0
// }
const checkAll = ref(false)
const isIndeterminate = ref(true)
const checkedProcessing = ref(['数据归一化','空间插值'])
const processing = ['数据归一化', '时间插值' , '空间插值' , '对数平滑' , '拼接']

const handleCheckAllChange = (val: boolean) => {
  checkedProcessing.value = val ? processing : []
  isIndeterminate.value = false
}
const handleCheckedCitiesChange = (value: string[]) => {
  const checkedCount = value.length
  checkAll.value = checkedCount === processing.length
  isIndeterminate.value = checkedCount > 0 && checkedCount < processing.length
}

const processingSubmit=()=>{
  console.log(checkedProcessing.value)
}
</script>

<style scoped>
.transfer-footer {
  margin-left: 15px;
  padding: 6px 5px;
}
</style>