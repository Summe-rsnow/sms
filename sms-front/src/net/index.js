import axios from 'axios'
import {useUserStore} from '../stores/index.js'

const userStore = useUserStore()
const defaultFailure = (msg) => alert(msg)
const defaultError = (error) => console.log(error)

axios.interceptors.request.use(
    (config) => {
        //基础url
        config.baseURL = '/api';
        config.withCredentials = true;

        if (userStore.token) { //如果存在令牌
            // 添加通用的请求头
            config.headers['smstoken'] = userStore.token;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

//所有请求底层都为post
function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data)
        .then(({data}) => {
            if (data.code === 1) success(data.data, data.msg)
            else failure(data.msg)
        })
        .catch(error)
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.post(url)
        .then(({data}) => {
            if (data.code === 1) success(data.data, data.msg)
            else failure(data.msg)
        })
        .catch(error)
}

function del(url, success, failure = defaultFailure, error = defaultError) {
    axios.post(url)
        .then(({data}) => {
            if (data.code === 1) success(data.data, data.msg)
            else failure(data.msg)
        })
        .catch(error)
}

export {get, post, del}