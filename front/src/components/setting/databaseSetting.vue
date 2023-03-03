<template>
  <el-form :model="databaseForm" label-width="120px" style="margin-left: 5%">
    <h2>数据源配置</h2>
    <el-space fill>
      <el-form-item label="数据源" style="font-size: 20px">
        <el-select v-model="databaseForm.databasetype" placeholder=" ">
          <el-option label="mysql" value="mysql"/>
          <el-option label="oracle" value="oracle"/>
          <el-option label="postgresql" value="postgresql"/>
          <el-option label="SQLServer" value="SQLServer"/>
          <el-option label="HDFS" value="HDFS"/>
        </el-select>
      </el-form-item>
      <el-alert style="margin-left: 20%;margin-top:-3%;height: 40px" type="info" show-icon :closable="false">
        <p>选择要用于STPam的数据源</p>
      </el-alert>
    </el-space>
    <div>
      <h3>数据库配置</h3>
      <el-divider/>
      <el-space fill style="width: 50%">
        <el-form-item label="连接名">
          <el-input v-model="databaseForm.datasourceID"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-space fill style="margin-top: 1%;width: 25%">
        <el-form-item label="主机">
          <el-input v-model="databaseForm.localhost"></el-input>
        </el-form-item>
      </el-space>
      <el-space fill style="margin-top: 1%;width: 25%">
        <el-form-item label="端口号">
          <el-input v-model="databaseForm.port"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-alert style="margin-left: 8%;width: 30%;height: 40px" type="info" show-icon :closable="false">
      <p>请确保地址和端口号可用</p>
      </el-alert>
      <el-space fill style="margin-top: 1%;width: 30%">
        <el-form-item label="数据库名称">
          <el-input v-model="databaseForm.database"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="databaseForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="databaseForm.password" type="password" show-password></el-input>
        </el-form-item>
      </el-space>
      <el-alert style="margin-left: 8%;width: 30%;height: 40px" type="info" show-icon :closable="false">
      <p>请选择可供外部连接的非root用户</p>
    </el-alert>
    </div>
    <div>
      <h3>高级配置</h3>
      <el-divider/>
      <el-space fill style="width: 50%">
        <el-form-item label="useSSL">
          <el-input v-model="databaseForm.ssl" style="margin-left: 10px"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-space fill style="margin-top: 1%;width: 50%">
        <el-form-item label="useUnicode">
          <el-input v-model="databaseForm.unicode" style="margin-left: 10px"></el-input>
        </el-form-item>
        <el-form-item label="characterEncoding">
          <el-input v-model="databaseForm.encoding" style="margin-left: 10px"></el-input>
        </el-form-item>
        <el-form-item label="serverTimezone">
          <el-input v-model="databaseForm.serverTimezone" style="margin-left: 10px"></el-input>
        </el-form-item>
        <el-form-item label="zeroDateTime">
          <el-input v-model="databaseForm.zeroDateTime" style="margin-left: 10px"></el-input>
        </el-form-item>
      </el-space>
    </div>
    <el-button @click="submitDatabaseSetting" style="margin-top: 2%;margin-left: 40%" type="primary">确认</el-button>
  </el-form>
</template>

<script setup lang="ts">
import {reactive} from 'vue'
import services from "../../utils/request";

// do not use same name with ref
const databaseForm = reactive({
  databasetype: '',
  datasourceID:'',
  localhost:'',
  port:'',
  database:'',
  username: '',
  password: '',
  ssl:'false',
  unicode:'true',
  encoding:'UTF-8',
  serverTimezone:'Asia/Shanghai',
  zeroDateTime:'convertToNull',
})
const datasource={
  databasetype: '',
  datasourceID:'',
  url:'',
  username: '',
  password: '',
}
const submitDatabaseSetting = () => {
  datasource.databasetype=databaseForm.databasetype
  datasource.datasourceID=databaseForm.datasourceID
  datasource.url="jdbc:"+datasource.databasetype+"://"+databaseForm.localhost+":"+databaseForm.port+"/"+databaseForm.database+
      "?useSSL="+databaseForm.ssl+"&useUnicode="+databaseForm.unicode+"&characterEncoding="+databaseForm.encoding+"&serverTimezone="+databaseForm.serverTimezone+
      "&zeroDateTimeBehavior="+databaseForm.zeroDateTime
  datasource.username=databaseForm.username
  datasource.password=databaseForm.password
  services.post('/data/setting',datasource).then(res=>{
    console.log(res)
  })
}
</script>

<style scoped>

</style>