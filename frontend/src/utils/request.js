// uitls包下的文件用于封装axios请求
// 该文件用于创建axios实例对象，设置请求的基本配置，添加请求拦截器和响应拦截器
import axios from 'axios'
import { ElMessage } from 'element-plus'
//创建axios实例对象
const request = axios.create({
  baseURL: '/api', //设置请求的基础URL
  timeout: 600000, //设置请求的超时时间，开发环境下设置为600000ms，生产环境下可以设置为30000ms
  headers: {
    'Content-Type': 'application/json', //设置请求头的Content-Type
  },
})

// axios的请求 request 拦截器 发送请求前执行
request.interceptors.request.use(
  (config) => { //成功回调
    // 在发送请求之前添加token
    const token = localStorage.getItem('token') //从本地存储中获取token
    if (token) {
      console.log('请求头中添加token:', token) //打印token到控制台
      config.headers['token'] = token //如果存在token，则将其添加到请求头中
    }
    return config //返回配置对象
  },
  (error) => { //失败回调
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error) //返回错误信息
  }
)

//axios的响应 response 拦截器 收到后端请求后执行
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    // 检查token过期或未授权
    if (error.response && error.response.status === 401) {
      ElMessage.error('登录已过期，请重新登录')
      localStorage.removeItem('token')
      localStorage.removeItem('role')
      localStorage.removeItem('userId')
      localStorage.removeItem('name')
      // 跳转到登录页
      window.location.href = '/login'
      return Promise.reject('登录已过期')
    }
    // 检查后端返回的jwt过期等信息
    if (error.response && error.response.data && typeof error.response.data === 'string' && error.response.data.includes('JWT expired')) {
      ElMessage.error('登录已过期，请重新登录')
      localStorage.clear()
      window.location.href = '/login'
      return Promise.reject('登录已过期')
    }
    // ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request