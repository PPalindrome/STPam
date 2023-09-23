import axios from "./request";

export function Login(data){
    return axios({
        method:'post',
        url:'login',
        data
    })
}

export function UserSetting(data){
    return axios({
        method:'post',
        url:'setting/userSetting',
        data
    })
}

export function Register(data){
    return axios({
        method:'post',
        url:'register',
        data
    })
}


export function getMapdata(){
    return axios({
        method:'get',
        url:'map_data',
    })
}

