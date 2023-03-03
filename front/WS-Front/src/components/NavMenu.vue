<template>
  <el-menu
      active-text-color="#ffd04b"
      background-color="#545c64"
      text-color="#fff"
      exact
      class="el-menu-vertical-demo"
      default-active="1"
      @open="handleOpen"
      @close="handleClose"
      style="width: 250px;min-height: calc(100vh - 50px);word-break: break-all;white-space: normal;"
      router
  >
    <NavItem v-for="v in items" :key="v.url" :item="v" :baseurl="v.url" :flag="v.flag"/>
  </el-menu>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue'
import {DocumentAdd, Service,Setting,} from '@element-plus/icons-vue'
import NavItem from './NavItem.vue'

const items = reactive([
  {title: 'WPS算法服务添加', url: '/wpsServiceAdd', flag: DocumentAdd,
    child: [
          {title: 'WPS算法服务添加', url: '/wpsServiceAdd', flag: DocumentAdd},
    ]
  },
  {title: 'WPS算法服务使用', url: '/wpsServiceUse', flag: Service,},
  {title: 'WPS算法服务管理', url: '/wpsServiceManage', flag: Setting,},

])


const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}


</script>

<style scoped>

</style>
