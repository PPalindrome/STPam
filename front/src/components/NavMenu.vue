<template>
  <el-menu
      :default-active="$route.path"
      active-text-color="#ffd04b"
      background-color="#545c64"
      text-color="#fff"
      exact
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      style="width: 15%;margin-top: 3%;min-height: calc(100vh - 120px);word-break: break-all;white-space: normal;"
      router
  >


    <NavItem v-for="v in items" :show="v.showItem" :key="v.url" :item="v" :baseurl="v.url" :flag="v.flag"/>
  </el-menu>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import {
  Setting,Download,DataBoard,Flag,
  Cpu,
  DataAnalysis,
  Cloudy,
  LocationInformation,
  Histogram, Tickets,
  Switch, MostlyCloudy,MagicStick,User,Money,Share,SetUp
} from '@element-plus/icons-vue'
import NavItem from './NavItem.vue'

//注意：showItem是false显示菜单，是true不显示菜单
//游客 显示数据和产品
//用户u 显示数据、模型、计算和产品
//管理员a 显示设置

const u=localStorage.getItem('name')&&localStorage.getItem('role')=='0'
const a=localStorage.getItem('name')&&localStorage.getItem('role')=='1'

const items = reactive([
  {
    title: '多源时空数据', url: '/dataAnalysis', flag: DataAnalysis,showItem:a,
    child: [
      {title: '数据查看', url: '/dataAnalysis/view', flag: Tickets,},
      // {title: '数据分析', url: '/dataAnalysis/analysis', flag: Histogram,showItem:false,},
      {title: '数据预处理', url: '/dataAnalysis/pretreatment', flag: MagicStick,},
      // {title: '数据共享', url: '/dataAnalysis/dataSharing', flag: Share,showItem:false,},
      {title: '数据库适配', url: '/setting/databaseSetting', flag: SetUp,},
    ]
  },
  {title: '在线建模', url: '/modeling', flag: DataBoard,showItem: !u,},
  {title: '时空过程任务发布', url: '/deepModel', flag: Cpu,showItem: !u,},
  {
    title: '分布式计算', url: '/distributedComputing', flag: Cloudy,showItem: !u,
    child: [
      {title: '分布式计算', url: '/distributedComputing/computing', flag: MostlyCloudy,},
      {title: '增量学习', url: '/distributedComputing/incremental', flag: Switch,}
    ]
  },
  {title: '产品示范', url: '/application', flag: Flag ,showItem: a,
    child: [
      {title: '示范应用', url: '/application/productApplication', flag: LocationInformation,},
      {title: '产品展示', url: '/application/applicationShow', flag: DataBoard,},
      {title: '产品下载', url: '/application/applicationDownload', flag: Download,},
    ]},
  {title: '设置', url: '/setting', flag: Setting,showItem: !a,
    child: [
      {title: '模型设置', url: '/setting/modelSetting', flag: Money,},
      {title: '分布式计算设置', url: '/setting/distributeSetting', flag: Money,},
      {title: '用户设置', url: '/setting/userSetting', flag: User,},

    ]
  },
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
