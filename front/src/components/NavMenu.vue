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
      style="width:250px;margin-top:50px;min-height: calc(100vh - 120px);word-break: break-all;white-space: normal;"
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
    title: 'menu.dataAnalysis.name', url: '/dataAnalysis', flag: DataAnalysis,showItem:a,
    child: [
      {title: 'menu.dataAnalysis.view.name', url: '/dataAnalysis/view', flag: Tickets,},
      // {title: '数据分析', url: '/dataAnalysis/analysis', flag: Histogram,showItem:false,},
      {title: 'menu.dataAnalysis.pretreatment.name', url: '/dataAnalysis/pretreatment', flag: MagicStick,},
      // {title: '数据共享', url: '/dataAnalysis/dataSharing', flag: Share,showItem:false,},
      {title: 'menu.dataAnalysis.adapt.name', url: '/setting/databaseSetting', flag: SetUp,},
    ]
  },
  {title: 'menu.modeling.name', url: '/modeling', flag: DataBoard,showItem: !u,},
  {title: 'menu.mission.name', url: '/deepModel', flag: Cpu,showItem: !u,},
  {
    title: 'menu.compute.name', url: '/distributedComputing', flag: Cloudy,showItem: !u,
    child: [
      {title: 'menu.compute.distribute', url: '/distributedComputing/computing', flag: MostlyCloudy,},
      {title: 'menu.compute.incremental', url: '/distributedComputing/incremental', flag: Switch,}
    ]
  },
  {title: 'menu.product.name', url: '/application', flag: Flag ,showItem: a,
    child: [
      {title: 'menu.product.application', url: '/application/productApplication', flag: LocationInformation,},
      {title: 'menu.product.show.name', url: '/application/applicationShow', flag: DataBoard,},
    ]},
  {title: 'menu.setting.name', url: '/setting', flag: Setting,showItem: !a,
    child: [
      {title: 'menu.setting.model', url: '/setting/modelSetting', flag: Money,},
      {title: 'menu.setting.compute', url: '/setting/distributeSetting', flag: Money,},
      {title: 'menu.setting.user', url: '/setting/userSetting', flag: User,},

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
