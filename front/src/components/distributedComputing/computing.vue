<template>
  <el-row :gutter="10">
    <el-col :span="12">
      <a style="font-size:36px; font-weight:bold">时空过程分布式分析挖掘</a>
      <div style="margin:5% 5%">
        <el-select v-model="Archs" placeholder="请选择分布式计算框架" >
                <el-option v-for="m in distributeArch" :label="m.name" :value="m.name" @click="selectExistArch(m.name)" />
        </el-select>
        <el-button type="success" @click="compute" style="margin-left: 10px">开始计算</el-button>
      </div>
      <div  style="margin: 0% 5%;width: 80%;height: 100%">
        <el-input type='textarea' @input='change($event)' v-model="inputResult" :autosize="{minRows:30,maxRows:30}" readonly />
      </div>
    </el-col>

    <el-col :span="12">
      <el-row>
        <el-col :span="12"><h2>分布式计算情况</h2></el-col>
        <el-col :span="12"><img alt="computing" src="../../assets/computing.png" style="width: 30%;margin: 5% 20%"></el-col>
      </el-row>

      <div>
        <el-card class="box-card" style="margin: 3% 0%">
          <template #header>
            <div class="card-header">
              <el-row>
                <el-col :span="18"><h3>GPU基本信息</h3></el-col>
                <el-col :span="6"><el-button class="button" type="text">查看详细信息</el-button></el-col>
              </el-row>
            </div>
          </template>
          <div>型号：NVIDIA Quadro RTX A6000 48GB（x2）</div>
          <el-divider />
          <div>开始时间：2022/8/12 16:50:13</div>
          <el-divider />
          <div>结束时间：2022/8/12 16:58:29</div>
          <el-divider />
          <div>训练时长：8′16″</div>
          <el-divider />
          <div>GPU显存使用率：2.0%</div>
          <el-divider />
          <div>显存位宽：384-BIT</div>
          <el-divider />
          <div>显存带宽：768GB/S</div>
          <el-divider />
          <div>单精度浮点计算(FP32)：38.7TFLOPS</div>
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
  {name: 'PaddlePadddle飞桨'},
  {name: 'LuojiaNet'}
])
const selectExistArch=(val)=>{
  currentArch=val
}

//开始分布式计算
const inputResult = ref('')
import services from "../../utils/request.js"
const params=reactive({
  modelFileName:'',
  datasetPath:'',
  distributePath:'',
})
const compute=()=>{
  // params.modelFileName=window.localStorage.getItem('currentName')
  // params.datasetPath=window.localStorage.getItem('currentPath')
  // params.distributePath=currentArch
  // services.post('/build',params).then(res=>{
  //   inputResult.value=res
  //   console.log(res)
  // })
  services.get('/JSJ.json').then(res => {     // 获取public下的test.json文件数据
    inputResult.value=res
  })

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
