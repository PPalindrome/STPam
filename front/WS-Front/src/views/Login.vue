<template>
  <div>
    <div style="width: 400px;margin: 150px auto;">
      <h1 style="text-align: center">登录</h1>
      <br>
      <el-form
          ref="ruleLoginFormRef"
          element-loading-text="正在登录..."
          :model="user"
          size="large"
          :rules="rules"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" :prefix-icon="User"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" :prefix-icon="Lock" show-password/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="submitLoginForm(ruleLoginFormRef)">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="width: 100px;margin: -120px auto;text-align: center">
      <el-button @click="dialogVisible = true" type="text"
      >还没有账户？点击注册。
      </el-button
      >

      <el-dialog
          v-model="dialogVisible"
          title="注册"
          width="30%"
          :before-close="handleClose"
      >
      <span>
          <el-form
              ref="ruleFormRef"
              :model="userRegister"
              status-icon
              :rules="registerRules"
              label-width="120px"
              class="demo-ruleForm"

          >
            <el-form-item label="用户名" prop="username">
              <el-input v-model="userRegister.username" placeholder="请输入用户名" clearable/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="userRegister.password" type="password" placeholder="请输入密码" clearable/>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input v-model="userRegister.checkPass" type="password" placeholder="请再次输入密码" clearable/>
            </el-form-item>
          </el-form>
      </span>
        <template #footer>

        <el-button type="primary" @click="userInfoRegister(ruleFormRef)">注册</el-button>
        <el-button @click="resetUserInfoForm(ruleFormRef)">重置</el-button>

        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, reactive} from "vue";
import {User, Lock} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";
import type {FormInstance} from 'element-plus'
import request from "../utils/request.js";
import {useRouter} from "vue-router";


const router = useRouter();
const ruleLoginFormRef = ref()
const user = reactive({
  username: "admin",
  password: "admin",
})

const rules = reactive({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
})

const submitLoginForm = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/userLogin', user).then(res => {
        console.log("返回码：", res.message)
        console.log("fields:", fields)
        // 后台返回数据格式：{“code”：“200”，“msg”；“后台返回信息”，“data”：“后台返回数据”}
        if (res.code == 200) {
          sessionStorage.setItem("loginedUser", user.username);
          router.replace({path: '/home'});
          ElMessage({
            showClose: true,
            type: "success",
            message: res.message
          })
        } else {
          ElMessage({
            showClose: true,
            type: "error",
            message: res.message
          })
        }
      })
      console.log('submit!')
    } else {
      ElMessage({
        showClose: true,
        type: "error",
        message: "用户名或密码错误"
      })
      console.log('error submit!', fields)
    }
  })
}

const resetLoginForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

// 注册
const ruleFormRef = ref<FormInstance>()

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (userRegister.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== userRegister.password) {
    callback(new Error("两次密码不匹配!"))
  } else {
    callback()
  }
}
const dialogVisible = ref(false)
const handleClose = (done: () => void) => {
  ElMessageBox.confirm('您确定要取消注册吗?')
      .then(() => {
        done()
      })
      .catch(() => {
        console.log("error")
      });
}
const userRegister = reactive({
  username: "",
  password: "",
  checkPass: '',
})
const registerRules = reactive({
  username: [{required: true, trigger: 'blur'}],
  password: [{required: true, validator: validatePass, trigger: 'blur'}],
  checkPass: [{required: true, validator: validatePass2, trigger: 'blur'}],
  mail: [{required: true, trigger: 'blur'}],
})
const userInfoRegister = (formEl) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      request.post('/userRegister', userRegister).then(res => {
        console.log("拿到后端返回！",res)
        if (res.code == '200') {
          console.log("register success!!")
          ElMessage({
            showClose: true,
            type: "success",
            message: "用户"+res.obj+"注册成功！"
          })
          dialogVisible.value = false
        }
        else {
          console.log("register errror!!")
          ElMessage({
            showClose: true,
            type: "error",
            message: "用户名"+res.obj+"已存在！"
          })
          dialogVisible.value = false
        }
      })
    } else {
      console.log('失败!')
      ElMessage({
        showClose: true,
        type: "error",
        message: "信息格式验证失败！"
      })
    }
  })
}
const resetUserInfoForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style scoped>

</style>
