<template>
  <el-row :gutter="10">
    <el-col :span="12">
      <a style="font-size:36px; font-weight:bold">{{ $t('menu.compute.title') }}</a>
      <div style="margin:5% 5%">
        <el-select v-model="Archs" :placeholder="$t('button.choose')" @change="selectExistArch">
                <el-option v-for="m in distributeArch" :label="m.name" :value="m.name"/>
        </el-select>
        <el-button type="success" @click="compute" style="margin-left: 10px">{{ $t('menu.compute.calculate') }}</el-button>
      </div>
      <div  style="margin: 0% 5%;width: 80%;height: 100%">
        <el-input type='textarea' @input='change($event)' v-model="inputResult" :autosize="{minRows:30,maxRows:30}" readonly />
      </div>
    </el-col>

    <el-col :span="12">
      <el-row>
        <el-col :span="12"><h2>{{ $t('menu.compute.state') }}</h2></el-col>
        <el-col :span="12"><img alt="computing" src="../../assets/computing.png" style="width: 30%;margin: 5% 20%"></el-col>
      </el-row>

      <div>
        <el-card class="box-card" style="margin: 3% 0%">
          <template #header>
            <div class="card-header">
              <el-row>
                <el-col :span="18"><h3>{{ $t('menu.compute.information') }}</h3></el-col>
                <el-col :span="6"><el-button class="button" type="text">{{ $t('button.view') }}</el-button></el-col>
              </el-row>
            </div>
          </template>
          <div>GPU：NVIDIA Quadro RTX A6000 48GB（x2）</div>
          <el-divider />
          <div>{{ $t('menu.compute.start') }}：2022/8/12 16:50:13</div>
          <el-divider />
          <div>{{ $t('menu.compute.end') }}：2022/8/12 16:58:29</div>
          <el-divider />
          <div>{{ $t('menu.compute.duration') }}：8′16″</div>
          <el-divider />
          <div>{{ $t('menu.compute.usage') }}：2.0%</div>
          <el-divider />
          <div>{{ $t('menu.compute.bandwidth') }}：768GB/S</div>
          <el-divider />
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue'
const Archs=ref('')
let currentArch=""

//分布式计算框架选择
const distributeArch = reactive([
  {name: 'PyTorch'},
  {name: 'TensorFlow'},
  {name: 'PaddlePadddle'},
  {name: 'LuoJiaNET'}
])
const selectExistArch=(val)=>{
  currentArch=val
  switch (val){
    case 'PyTorch': services.get('/pytorch.json').then(res => {inputResult.value=res});break
    case 'TensorFlow': services.get('/tensorflow.json').then(res => {inputResult.value=res});break
    case 'PaddlePadddle': services.get('/PaddlePaddle.json').then(res => {inputResult.value=res});break
    case 'LuoJiaNET': services.get('/LuoJiaNET.json').then(res => {inputResult.value=res});break
  }
}

//开始分布式计算
const inputResult = ref('')
import services from "../../utils/request.js"
import {ElMessage, ElMessageBox} from "element-plus";
const params=reactive({
  modelFileName:'',
  datasetPath:'',
  distributeArch:'',
  command:'',
  outpath:''
})
const compute=()=>{
  params.modelFileName=localStorage.getItem('currentName')||''
  params.datasetPath=localStorage.getItem('currentPath')||''
  params.distributeArch=currentArch
  params.command=localStorage.getItem('currentCommand')||''
  params.outpath=localStorage.getItem('outpath')||''
  services.post('/build',params).then(res=>{
    console.log(res)
  })
  // services.get('/JSJ.json').then(res => {     // 获取public下的test.json文件数据
  //   inputResult.value=res
  // })

}
const change=(e)=>{
  this.$forceUpdate(e)
}

const  rankNum = ref(2);
console.log(rankNum)

// do not use same name with ref
const rankItems = reactive([])

const onSubmit = () => {
  console.log('submit!')
}

const computeInfo = reactive([
  {title: '运行时间', info: 'STP-Net'},
  {title: 'GPU负载情况', info: 'V1.0.0'},

])

//获取当前时间
const getCurrentTime = () => {
  //获取当前时间并打印
  let yy = new Date().getFullYear();
  let mm = new Date().getMonth() + 1;
  let dd = new Date().getDate();
  let hh = new Date().getHours();
  let mf = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes();
  let ss = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds();
  return yy + '/' + mm + '/' + dd + ' ' + hh + ':' + mf + ':' + ss;
}


</script>
