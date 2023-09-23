import axios from 'axios'
import {ElMessage} from "element-plus";

const services = axios.create({
    baseURL: 'http://47.92.157.59:8070/',
})

// 请求拦截
services.interceptors.request.use(config => {
    //如果存在token，就让请求头携带这个token
    if(window.sessionStorage.getItem('tokenStr')){
        // @ts-ignore
        config.headers['Authorization']=window.sessionStorage.getItem('tokenStr');
    }
    return config
}, error => {
    console.log(error);
})

// 响应拦截
services.interceptors.response.use(success => {
    //成功调用后端接口，但后端不允许调用时,返回业务逻辑错误
    //先判断拦截器调用了接口并且为200
    if (success.status && success.status === 200) {
        //后端返回响应码：逻辑错误、未登录、权限禁止
        if (success.data.code === 500 || success.data.code === 401 || success.data.code === 403) {
            //响应码、相应信息、后端传来的数据
            // ElMessage.error({message: success.data.message});
            ElMessage({
                showClose: true,
                message: success.data.message,
                type: "error"
            })
            return;
        }
        //剩下的都是成功的
        if (success.data.message) {
            // ElMessage.success({message: success.data.message});
            ElMessage({
                showClose: true,
                message: success.data.message,
                type: "success"
            })
        }
    }
    //返回后端传来的json对象
    return success.data;
}, error => {
    //拦截器调用接口也失败了
    if (error.response.code === 504 || error.response.code === 404) {
        // ElMessage.error({message: '服务器跑路了！！'});
        ElMessage({
            showClose: true,
            message: '服务器跑路了！！',
            type: "error"
        })
    } else if (error.response.code === 403) {
        // ElMessage.error({message: '权限不足，请联系管理员！'});
        ElMessage({
            showClose: true,
            message: '权限不足，请联系管理员！',
            type: "error"
        })
    } else if (error.response.code === 401) {
        // ElMessage.error({message: '请登录！'});
        ElMessage({
            showClose: true,
            message: '请登录！',
            type: "error"
        })
    } else {
        //判断有没有返回的信息，有就发出去
        if (error.response.data.message) {
            // ElMessage.error({message: error.response.data.message});
            ElMessage({
                showClose: true,
                message: error.response.data.message,
                type: "error"
            })
        } else {
            //没有信息且未知错误
            // ElMessage.error({message: '未知错误！'});
            ElMessage({
                showClose: true,
                message: '未知错误！',
                type: "error"
            })
        }
    }
    //错误了就都返回空
    // return;
})

export default services
