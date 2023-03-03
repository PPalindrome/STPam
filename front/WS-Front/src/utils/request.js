import axios from 'axios'
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: 'http://localhost:8088/',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    timeout: 6000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // config.headers['token'] = user.token;  // 设置请求头
    //如果存在token，就让请求头携带这个token
    if(window.sessionStorage.getItem('tokenStr')){
        // @ts-ignore
        config.headers['Authorization']=window.sessionStorage.getItem('tokenStr');
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    // response => {
    //     let res = response.data;
    //     // 如果是返回的文件
    //     if (response.config.responseType === 'blob') {
    //         return res
    //     }
    //     // 兼容服务端返回的字符串数据
    //     if (typeof res === 'string') {
    //         res = res ? JSON.parse(res) : res
    //     }
    //     return res;
    // },
    // error => {
    //     console.log('err' + error) // for debug
    //     return Promise.reject(error)
    success => {
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
    }
)


export default request

