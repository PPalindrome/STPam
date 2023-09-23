<template>
  <div
      style="height:120px;width:100vw;margin-left:-20px;line-height:60px;border-bottom: 1px solid #ccc;display: flex;background: rgba(231,238,248, 1)">
    <img alt="STPAM logo" src="../assets/STPAM.png" style="height: 70%;margin-top: 1%;size: 4px;margin-left: 50px"/>
    <div style="width: 65%;padding-left: 10px;margin-top: 1.5%;font-size:33px;font-weight: bold;color: dodgerblue">
      {{$t("title.name")}}
    </div>
    <el-container class="right">
      <el-header class="header">
        <div class="menu" >
          <el-menu
            default-active="1"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#E7EEF8"
            text-color="#000"
            active-text-color="dodgerblue"

          >
            <el-sub-menu index="1">
              <template #title style="font-size: 50px"><el-icon><User /></el-icon>
                {{$t("header.welcome")}}{{ name === null ? this.$t('header.visitor') : name }}</template>
              <el-menu-item index="1-1" @click="goBack()">{{ name === null ? this.$t('header.login') : this.$t('header.exit') }}</el-menu-item>
              <el-menu-item index="1-2" v-if="name===null" @click="toRegister">{{$t("header.register")}}</el-menu-item>
            </el-sub-menu>
            <el-menu-item index="2" @click="helpPage()">{{$t("header.help")}}</el-menu-item>
            <el-sub-menu index="3">
              <template #title >{{$t("header.language")}}</template>
              <el-menu-item index="3-1" @click="changeZh">简体中文</el-menu-item>
              <el-menu-item index="3-2" @click="changeEn">English</el-menu-item>
            </el-sub-menu>
          </el-menu>
        </div>
      </el-header>
    </el-container>
  </div>
</template>

<script setup>
import {User} from '@element-plus/icons-vue'
const name = localStorage.getItem("name")
// const login=$t("header.login")
// const exit=$t("header.exit")
import {useRouter} from 'vue-router'
const router = useRouter();

const goBack = () => {
  router.push('../views/login')
}
const toRegister = () => {
  router.push('/register');
}
const helpPage = () => {
  router.push('/help');
}

import {getCurrentInstance} from "vue";
//？表示前面如果返回null，后面不执行
let $this=getCurrentInstance()?.appContext.config.globalProperties
//切换中文
const changeZh=()=>{
  $this.$i18n.locale="zh"
}
//切换英文
const changeEn=()=>{
  $this.$i18n.locale="en"
}
</script>

<style scoped>
.el-menu-demo{
  border-right: none;
  border-bottom: #1E90FF;
}
.right{
  .header{
    height:60px;
    font-size: 50px;
    background-color: #E7EEF8;
    display: flex;
    justify-content: flex-end;
    .menu{
      width:600px;
      margin-top:40px;
    }
  }
  .main{
    padding:5px;
  }
}
</style>
