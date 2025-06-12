// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import Users from '@/views/Users/Users.vue';
import Layout from '@/views/Layout/Layout.vue';
import jobs from '@/views/student/jobs.vue';

const routes = [
  { path: '/',
    name: 'index',
    component:Layout, // 默认重定向到登录页面
    children: [
      { path:'/users', name: 'users', component: Users,},
      { path: '/jobs', name: 'jobs', component: jobs }
    ]
  },
  { path: '/login', name: 'login', component: Login },
  { path: '/register', name: 'register', component: Register },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  // 允许直接访问登录和注册
  if (to.path === '/login' || to.path === '/register') {
    return next();
  }
  // 判断本地有无 token
  const token = localStorage.getItem('token');
  if (!token) {
    // 没有token，强制跳转登录
    return next('/login');
  }
  next();
});

export default router;
