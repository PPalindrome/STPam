<template>

  <div>
    <!--无子级-->
    <el-menu-item :index="baseurl" v-if="!item.child">
      <el-icon>
        <Component :is="flag"/>
      </el-icon>
      <span>{{ item.title }}</span>
    </el-menu-item>
    <!--有子级-->
    <el-sub-menu :index="baseurl" v-else>
      <template #title>
        <el-icon>
          <Component :is="flag"/>
        </el-icon>
        <span>{{ item.title }}</span>
      </template>
      <NavItem v-for="sub in item.child" :key="sub.url" :item="sub" :baseurl="sub.url" :flag="sub.flag"/>
    </el-sub-menu>
  </div>
</template>

<script setup>

const props = defineProps(['item', 'baseurl', 'flag'])


</script>

<style scoped>

</style>
