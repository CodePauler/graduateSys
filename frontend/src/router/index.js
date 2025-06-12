// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import Users from '@/views/Users/Users.vue';
import Layout from '@/views/Layout/Layout.vue';

const routes = [
  { path: '/',
    name: '',
    component:Layout, // 默认重定向到登录页面
    children: [
      { path:'/users', 
        name: 'userss', 
        component: Users,
      // children:[
      //   {path: 'students',
      //     na
      //   }
      // ]
    },
    ]
  },
  { path: '/login', name: 'login', component: Login },
  { path: '/register', name: 'register', component: Register },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
