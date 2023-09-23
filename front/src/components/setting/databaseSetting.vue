<template>
  <el-form :model="databaseForm" label-width="120px" style="margin-left: 5%">
    <h2>{{$t('menu.dataAnalysis.adapt.datasourceSetting')}}</h2>
    <el-space fill>
      <el-form-item :label="$t('menu.dataAnalysis.adapt.datasource')" style="font-size: 20px">
        <el-select v-model="databaseForm.databasetype" placeholder=" ">
          <el-option label="mysql" value="mysql"/>
          <el-option label="oracle" value="oracle"/>
          <el-option label="postgresql" value="postgresql"/>
          <!--          <el-option label="SQLServer" value="SQLServer"/>-->
          <el-option label="HDFS" value="HDFS"/>
        </el-select>
      </el-form-item>
      <el-alert style="margin-left: 20%;margin-top:-3%;height: 40px" type="info" show-icon :closable="false">
        <p>{{$t('menu.dataAnalysis.adapt.warning1')}}</p>
      </el-alert>
    </el-space>
    <div>
      <h3>{{$t('menu.dataAnalysis.adapt.databaseSetting')}}</h3>
      <el-divider/>
      <el-space fill style="width: 50%">
        <el-form-item :label="$t('menu.dataAnalysis.adapt.connectionName')">
          <el-input v-model="databaseForm.datasourceID"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-space fill style="margin-top: 1%;width: 25%">
        <el-form-item :label="$t('menu.dataAnalysis.adapt.host')">
          <el-input v-model="databaseForm.localhost"></el-input>
        </el-form-item>
      </el-space>
      <el-space fill style="margin-top: 1%;width: 25%">
        <el-form-item :label="$t('menu.dataAnalysis.adapt.port')">
          <el-input v-model="databaseForm.port"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-alert style="margin-left: 8%;width: 30%;height: 40px" type="info" show-icon :closable="false">
        <p>{{$t('menu.dataAnalysis.adapt.warning2')}}</p>
      </el-alert>
      <el-space fill style="margin-top: 1%;width: 30%">
        <el-form-item :label="databaseForm.databasetype === 'oracle' ? this.$t('menu.dataAnalysis.adapt.databaseName2') : (databaseForm.databasetype === 'HDFS' ? this.$t('menu.dataAnalysis.adapt.databaseName3') : this.$t('menu.dataAnalysis.adapt.databaseName1'))">
          <el-input v-model="databaseForm.database"></el-input>
        </el-form-item>
        <el-form-item :label="$t('menu.dataAnalysis.adapt.userName')" v-if="databaseForm.databasetype!='HDFS'">
          <el-input v-model="databaseForm.username"></el-input>
        </el-form-item>
        <el-form-item :label="$t('menu.dataAnalysis.adapt.password')" v-if="databaseForm.databasetype!='HDFS'">
          <el-input v-model="databaseForm.password" type="password" show-password></el-input>
        </el-form-item>
      </el-space>
      <el-alert style="margin-left: 8%;width: 30%;height: 40px" type="info" show-icon :closable="false">
        <p>{{$t('menu.dataAnalysis.adapt.warning3')}}</p>
      </el-alert>
    </div>
    <div>
      <h3>{{$t('menu.dataAnalysis.adapt.Advance')}}</h3>
      <el-divider/>
      <el-space fill style="width: 50%" v-if="databaseForm.databasetype!='HDFS'">
        <el-form-item label="useSSL">
          <el-input v-model="databaseForm.ssl" style="margin-left: 10px"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-space fill style="margin-top: 1%;width: 50%" v-if="databaseForm.databasetype!='HDFS'">
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
      <el-space fill style="width: 50%" v-if="databaseForm.databasetype=='HDFS'">
        <el-form-item label="replication">
          <el-input v-model="databaseForm.replication" style="margin-left: 10px"></el-input>
        </el-form-item>
      </el-space>
      <br>
      <el-space fill style="margin-top: 1%;width: 50%" v-if="databaseForm.databasetype=='HDFS'">
        <el-form-item label="blockSize">
          <el-input v-model="databaseForm.blockSize" style="margin-left: 10px"></el-input>
        </el-form-item>
        <el-form-item label="permissionsEnabled">
          <el-input v-model="databaseForm.permissionsEnabled" style="margin-left: 30px"></el-input>
        </el-form-item>
        <el-form-item label="namenodeHandlerCount">
          <el-input v-model="databaseForm.namenodeHandlerCount" style="margin-left: 60px"></el-input>
        </el-form-item>
        <el-form-item label="clientBlockWriteRetries">
          <el-input v-model="databaseForm.clientBlockWriteRetries" style="margin-left: 50px"></el-input>
        </el-form-item>
        <el-form-item label="clientUseDatanodeHostname">
          <el-input v-model="databaseForm.clientUseDatanodeHostname" style="margin-left: 90px"></el-input>
        </el-form-item>
      </el-space>
    </div>
    <el-button @click="submitDatabaseSetting" style="margin-top: 2%;margin-left: 40%" type="primary">确认</el-button>
  </el-form>
</template>

<script setup lang="ts">
import {reactive} from 'vue'
import services from "../../utils/request";
import router from "../../router";

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
  replication:'3',
  blockSize:'128M',
  permissionsEnabled:'true',
  namenodeHandlerCount:'10',
  clientBlockWriteRetries:'3',
  clientUseDatanodeHostname:'false'
})
const datasource={
  databasetype: '',
  datasourceID:'',
  url:'',
  username: '',
  password: '',
}
const submitDatabaseSetting = () => {
  if(databaseForm.databasetype!="HDFS")
  {
    datasource.databasetype=databaseForm.databasetype
    datasource.datasourceID=databaseForm.datasourceID
    console.log(databaseForm.databasetype=="oracle")
    if(databaseForm.databasetype=="mysql"){
      datasource.url="jdbc:"+datasource.databasetype+"://"+databaseForm.localhost+":"+databaseForm.port+"/"+databaseForm.database+
          "?useSSL="+databaseForm.ssl+"&useUnicode="+databaseForm.unicode+"&characterEncoding="+databaseForm.encoding+"&serverTimezone="+databaseForm.serverTimezone+
          "&zeroDateTimeBehavior="+databaseForm.zeroDateTime
      datasource.databasetype="mysql"
    }
    else if(databaseForm.databasetype=="oracle")
    {
      datasource.url="jdbc:oracle:thin:@//"+databaseForm.localhost+":"+databaseForm.port+"/"+databaseForm.database
      datasource.databasetype="oracle"
    }
    else if(databaseForm.databasetype=="postgresql")
    {
      datasource.url="jdbc:postgresql://"+databaseForm.localhost+":"+databaseForm.port+"/"+databaseForm.database+
          "?useSSL="+databaseForm.ssl+"&useUnicode="+databaseForm.unicode+"&characterEncoding="+databaseForm.encoding+"&serverTimezone="+databaseForm.serverTimezone+
          "&zeroDateTimeBehavior="+databaseForm.zeroDateTime
      datasource.databasetype="postgresql"
    }
    datasource.username=databaseForm.username
    datasource.password=databaseForm.password
    services.post('/data/setting',datasource).then(res=>{
      if(res.code==200){
        const databaseName=databaseForm.database
        const datasourceID=databaseForm.datasourceID
        router.push({path:'/setting/dataTableSetting',state:{databaseName,datasourceID}});
      }
    })
  }
  else if(databaseForm.databasetype=="HDFS")
  {
    datasource.databasetype=databaseForm.databasetype
    datasource.datasourceID=databaseForm.datasourceID
    datasource.url="hdfs://"+databaseForm.localhost+":"+databaseForm.port
        +"?"+databaseForm.database
        +"?"+databaseForm.replication
        +"?"+databaseForm.blockSize
        +"?"+databaseForm.permissionsEnabled
        +"?"+databaseForm.namenodeHandlerCount
        +"?"+databaseForm.clientBlockWriteRetries
        +"?"+databaseForm.clientUseDatanodeHostname
    services.post('/data/setting',datasource).then(res=>{
      if(res.code==200){
        const databaseName=databaseForm.database
        const datasourceID=databaseForm.datasourceID
        router.push({path:'/setting/dataTableSetting',state:{databaseName,datasourceID}});
      }
    })
  }

}
</script>

<style scoped>

</style>
