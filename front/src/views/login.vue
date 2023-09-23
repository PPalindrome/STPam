<template>
  <br><br><br>
  <img alt="Vue logo" src="../assets/STPAM.png" style="margin-left:45%"/>
  <h1 style="text-align: center">基于多源大数据的时空过程智能分析挖掘软件</h1>
  <br><br><br>
  <div
      class="inline-flex"
      style="width: 50%;height: 360px;margin: auto auto;"
      :style="{
          boxShadow: `var(${'--el-box-shadow-dark'})`,
        }"
  >
    <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        v-loading="loading"
        element-loading-text="正在登录..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0,0,0,0.8)"
        label-width="120px"
        class="loginContainer"
        style="margin: auto;width: 60%"
    >
      <br>
      <h2 style="text-align: center">管理员登录</h2>
      <br>
      <el-form-item label="用户名" prop="username" >
        <el-input v-model="ruleForm.username" placeholder="请输入用户名" clearable/>
      </el-form-item>
      <el-form-item label="密码" prop="password" style="margin-top: 5%">
        <el-input v-model="ruleForm.password" type="password" placeholder="请输入密码" clearable/>
      </el-form-item>

      <el-form-item label="验证码" prop="code" style="margin-top: 0%">
        <el-input
            type="text"
            v-model="ruleForm.code"
            auto-complete="false"
            placeholder="点击图片更换验证码"
            style="width: 53%;margin-top: -2%"></el-input>
        <VerifyCode style="margin-left:5%;width: 30%" @el-child="verifyCoding"/>
      </el-form-item>
      <el-form-item style="margin-top: 5%;margin-left:10px">
        <el-checkbox class="loginRemember" v-model="checked">记住我</el-checkbox>
        <el-button type="primary" @click="submitForm(ruleFormRef)" style="margin-left:20px" :plain="true">登录</el-button>
        <el-button @click="toRegister()">注册</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script setup>
window.localStorage.clear()
window.sessionStorage.clear()
import {ref, reactive} from "vue"
import 'element-plus/es/components/message/style/css'
import {useRouter} from 'vue-router'
import VerifyCode from '../components/verify/verifyCode.vue'
import {ElMessage} from 'element-plus'
import {Login} from "../utils/api";
import { JSEncrypt } from 'jsencrypt';
import axios from 'axios'
const showMessageError = (messages) => {
  ElMessage({
    showClose: true,
    message: messages,
    type: "error",
  })
}

let verifyMessage = ref('');
const verifyCoding = (val) => {
  verifyMessage = val;
  console.log(verifyMessage)

}

const ruleForm = reactive({
  username: '',
  password: '',
  code:'',

})

const router=useRouter();
const loading=ref( false);
const checked= ref(true);
const rules = reactive({
  username: [
    {
      required: true,
      message: '请输入用户名！',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 12,
      message: '长度需要在3-12位之间！',
      trigger: 'blur'
    },
  ],
  password: [
    {
      required: true,
      message: '请输入密码！',
      trigger: 'change',
    },
    {
      min: 3,
      max: 18,
      message: '长度需要在3-18位之间！',
      trigger: 'change'
    },
  ],
  code: [
    {
      required: true,
      message: '请输入验证码',
      trigger: 'blur'
    }],
})
const ruleFormRef=ref('');

const submitForm = async (formEl) => {
  if (ruleForm.code === verifyMessage) {
    // console.log(ruleForm.code, verifyMessage)
    if (!formEl) return
    await formEl.validate((valid, fields) => {
      if (valid) {
        let publicKey =""
        axios.post("/encrypt").then(res => {
          publicKey=res.data.obj
          let encryptor = new JSEncrypt();
          // 设置公钥
          encryptor.setPublicKey(publicKey);
          ruleForm.username=encryptor.encrypt(ruleForm.username);
          ruleForm.password=encryptor.encrypt(ruleForm.password);
          rules.username[1].max=ruleForm.username.length;
          rules.password[1].max=ruleForm.password.length;
          Login(ruleForm).then(res => {
            if (res) {
              loading.value = false;
              //存储当前用户名
              const s = res.obj.token
              const l = s.length;
              let name = '';
              let i = 1;
              for (; i < l; i++) {
                if (s[i] != "?") {
                  name += s[i];
                } else break;
              }
              window.localStorage.setItem('name', name);
              //存储用户token
              // alert(JSON.stringify(res.obj))
              const tokenStr = res.obj.tokenHead + res.obj.token;
              window.sessionStorage.setItem('tokenStr', tokenStr);
              //获取用户角色
              axios.post("/getRole",name).then(res2 => {
                if(res2){
                  localStorage.setItem('role',res2.data.obj)
                  if(res2.data.obj===0){
                    router.replace('/deepModel');
                  }else if(res2.data.obj===1){
                    router.replace('/setting/modelSetting');
                  }else router.replace('/dataAnalysis/view');
                }
              })
            }

            loading.value = false;
          })

        })

      } else {
        console.log('error submit!', fields)
        return false;
      }
    })
  }else {
    console.log(ruleForm.code, verifyMessage)
    showMessageError('验证码错误！')
  }
}

const toRegister=()=>{
  router.push('/register');
}
// 加密
// 加密

</script>

<style scoped>

</style>