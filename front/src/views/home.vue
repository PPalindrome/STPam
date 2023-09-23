<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <Header />
      </el-header>


      <el-container>
<!--        左侧导航-->
        <NavMenu />

        <el-main style="background-color: azure;margin-top: 50px">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {ref} from "vue";
import NavMenu from "../components/NavMenu.vue";
import Header from "../components/header.vue"

//只要在一台客户端登录就会保持该用户的登录状态
// if(localStorage.getItem('name')!=='visitor'){
//   window.localStorage.clear()
//   window.sessionStorage.clear()
// }

</script>

<style scoped>

</style>
