<template>
  <div
      style="height:200%;width:102%;margin-left:-18px;line-height:60px;border-bottom: 1px solid #ccc;display: flex;background: rgba(231,238,248, 1)">
    <img alt="STPAM logo" src="../assets/STPAM.png" style="height: 70%;margin-top: 1%;size: 4px;margin-left: 80px"/>
    <div style="width: 50%;padding-left: 30px;margin-top: 1.5%;font-size:36px;font-weight: bold;color: dodgerblue">
      STPAM-基于多源大数据的时空过程智能分析挖掘软件
    </div>

    <div style="flex: 1"></div>
    <div style="width: 300px">
      <div class="demo-type" style="margin:10% auto;font-weight: bold;font-size:20px;">
        <span>欢迎您：
          <span style="color: #ff1ee1">{{ name === null ? '游客' : name }} </span>&nbsp &nbsp &nbsp
          <el-button type="danger" @click="goBack()" round>{{ name === null ? '登录' : '退出' }}</el-button>
          <el-button v-if="name===null" type="primary" @click="toRegister" round>注册</el-button>
        </span>
      </div>
    </div>
  </div>

</template>

<script setup>

const name = localStorage.getItem("name")

import {useRouter} from 'vue-router'

const router = useRouter();

const goBack = () => {
  router.push('../views/login')
}

const toRegister = () => {
  router.push('/register');
}
</script>

<style scoped>

</style>
