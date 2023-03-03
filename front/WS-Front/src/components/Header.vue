<template>
  <div
      style="height:100%;width:102%;margin-left:-18px;line-height:60px;border-bottom: 1px solid #ccc;display: flex;background: rgba(231,238,248, 1)">
    <div style="width: 30%;padding-left: 30px;font-size:20px;font-weight: bold;color: dodgerblue">
      STPam——WPS服务共享模块
    </div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown trigger="click">
        <div class="demo-type" style="margin:2px auto;cursor:pointer;">
          <span>
          <el-avatar :size="40"
                     src="https://img2.baidu.com/it/u=1992622197,1004475034&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1659718800&t=976e3344ebf0e4f4f3af903036f79844"
                     @error="errorHandler">
            <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"/>
          </el-avatar>
            <div>{{ username }}</div>
            </span>
        </div>
        <!--        <el-button type="primary" style="margin-top:18px;">-->
        <!--          用户<el-icon class="el-icon&#45;&#45;right"><arrow-down /></el-icon>-->
        <!--        </el-button>-->
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item :icon="User">个人信息</el-dropdown-item>
            <el-dropdown-item :icon="Setting">设置</el-dropdown-item>
            <div></div>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

</template>

<script setup lang="ts">
import {User, Setting, ArrowDown} from "@element-plus/icons-vue"
import {ref} from "vue";


const username = ref(sessionStorage.getItem("loginedUser"))
const errorHandler = () => true
</script>

<style scoped>

</style>
