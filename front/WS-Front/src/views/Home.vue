<template>
  <div>
      <div class="common-layout">
        <el-container>
          <el-header><Header /></el-header>
          <el-container>
            <!--左侧导航-->
            <el-aside> <NavMenu /></el-aside>
            <!--中间内容-->
            <el-main><router-view/></el-main>
          </el-container>
        </el-container>
      </div>
  </div>
</template>

<script setup>
import NavMenu from "../components/NavMenu.vue";
import Header from "../components/Header.vue";
</script>

<style scoped>

</style>
