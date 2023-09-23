<template>

  <div>
<!--    无子级-->
    <el-menu-item :index="baseurl" v-if="!show && !item.child">
      <el-icon>
        <Component :is="flag"/>
      </el-icon>
      <span>{{$t(item.title)}}</span>
<!--      <div>{{typeof(show)}}</div>-->
    </el-menu-item>
<!--    有子级-->
    <el-sub-menu :index="baseurl" v-else-if="!show">
      <template #title>
      <el-icon>
        <Component :is="flag"/>
      </el-icon>
      <span>{{$t(item.title)}}</span>
      </template>
      <NavItem v-for="sub in item.child" :key="sub.url" :item="sub" :baseurl="sub.url" :flag="sub.flag" :show="sub.showItem"/>
    </el-sub-menu>
  </div>
</template>

<script setup>

const props=defineProps(['item','baseurl','flag','show'])
   3



</script>

<style scoped>

</style>
