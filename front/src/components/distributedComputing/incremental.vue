<template>

  <el-row>
    <el-col :span="10" style="margin-left: 3%">
      <h2>可增量时空过程深度学习方法设置</h2>
      <el-form>
        <h4>回放样本方法数据选择</h4>
        <el-upload
            ref="uploadRef"
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">选择数据&#8194;<el-icon><Select/></el-icon>
            </el-button>
          </template>

          <el-button class="ml-3" type="success" @click="submitUpload" style="margin-left: 3%">上传至服务器&#8194;
            <el-icon>
              <UploadFilled/>
            </el-icon>
          </el-button>

          <template #tip>
            <div class="el-upload__tip">文件大小不得超过20GB!</div>
          </template>
        </el-upload>

        <h4>时空过程深度学习模型选择：</h4>
        <el-form-item>
          <el-select v-model="form.model" placeholder="请选模型">
            <el-option v-for="o in modelItem" :label="o.tabName" :value="o.tabName"/>
          </el-select>
        </el-form-item>

        <h4>时空过程深度学习模型参数设置</h4>
        <h6>（默认与非增量模型参数相同）</h6>
        <el-form-item v-for="v in formModelItems" :label="v.name" style="margin: 1% 0%;width: 60%" label-width="120px">
          <el-input :placeholder="v.info" v-model="v.val"/>

        </el-form-item>

        <el-form-item style="margin-left: 40%">
          <el-button type="primary" @click="onSubmit">确定</el-button>
          <el-button>重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>


    <el-col :span="12">
      <h2>增量学习效果</h2>

      <h4>非增量效果</h4>
      <el-descriptions border>
      <el-descriptions-item v-for="v in unIncrementalResultInfo" :key="v.info" :item="v">
        <template #label>{{ v.title }}</template>
        {{ v.info }}
      </el-descriptions-item>
      </el-descriptions>
      <h4>增量效果</h4>
      <el-descriptions border>
      <el-descriptions-item v-for="v in incrementalResultInfo" :key="v.info" :item="v">
        <template #label>{{ v.title }}</template>
        {{ v.info }}
      </el-descriptions-item>
      </el-descriptions>
      <h4>额外资源开销</h4>
      <el-descriptions border>
        <el-descriptions-item v-for="v in otherResourceInfo" :key="v.info" :item="v">
          <template #label>{{ v.title }}</template>
          {{ v.info }}
        </el-descriptions-item>
      </el-descriptions>
      <el-row>

        <el-col :span="12">
          <h3>增量学习方法图</h3>
          <img alt="IncrementalMethod" src="../../assets/IncrementalMethod.png" style="width: 95%">
        </el-col>
        <el-col :span="12">
          <h3>可增量时空过程深度学习图</h3>
          <img alt="IncrementalSpatiotemporal" src="../../assets/IncrementalSpatiotemporal.png" style="width: 95%">
        </el-col>


      </el-row>

    </el-col>

  </el-row>

</template>

<script lang="ts" setup>
import type {UploadInstance} from 'element-plus'
import {reactive, ref} from 'vue'
import {
  UploadFilled,
  Select,
} from '@element-plus/icons-vue'

const form = reactive({
  model: '',

})

const modelItem = reactive([
  {tabName: 'STP-Net',},
  {tabName: '**-Net',},
  {tabName: '**-Net',},
  {tabName: '**-Net',},
  {tabName: '**-Net',},
  {tabName: '**-Net',},
])

const formModelItems = reactive([
  {name: '输入特征数量', info: '10', val: '',},
  {name: '隐藏层节点个数', info: '20', val: '',},
  {name: '单元层层数', info: '3', val: '',},
  {name: '批处理大小', info: '50', val: '',},
  {name: '权值衰减常量', info: '6', val: '',},
  {name: '学习率', info: '0.0001', val: '',},
  {name: '最大迭代次数', info: '150', val: '',},
  {name: '学习率策略', info: 'CONSTANT', val: '',},
  {name: '激活函数', info: '请输入relu、sigmoid或tanh', val: '',},
])

const uploadRef = ref<UploadInstance>()
const submitUpload = () => {
  uploadRef.value!.submit()
}

const onSubmit = () => {
  console.log('submit!')
}

const unIncrementalResultInfo = reactive([
  {title: '所需时间', info: '3min'},
  {title: '拟合度', info: '85%'},
])

const incrementalResultInfo = reactive([
  {title: '所需时间', info: '1min'},
  {title: '拟合度', info: '95%'},
])

const otherResourceInfo = reactive([
  {title: '内存消耗', info: '50%'},
  {title: '时间差值', info: '2min'},
])
</script>

<style scoped>

</style>