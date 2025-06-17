// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import Users from '@/views/Admin/Users.vue';
import Students from '@/views/Admin/Students.vue';
import Layout from '@/views/Layout/Layout.vue';
import Jobs from '@/views/Public/Jobs.vue';
import Companies from '@/views/Admin/Companies.vue';
import Announcements from '@/views/Admin/Announcements.vue';
import Applications from '@/views/Student/Applications.vue';
import Resume from '@/views/Student/Resume.vue';
import Hires from '@/views/Company/Hires.vue';
import Home from '@/views/Public/Home.vue';
import Faculties from '@/views/Admin/Faculties.vue';
import Profile from '@/views/Public/Profile.vue';
const routes = [
  { 
    path: '/',
    name: 'index',
    component: Layout, // 所有页面的父组件，包含导航栏和侧边栏
    redirect: '/home', // 添加默认重定向到首页
    children: [
      { path: '/home', name:'home',component:Home, },                      //*all-   首页，展示公告和毕业生统计情况(/graduates似乎没有什么必要)
      { path:'/users', name: 'users', component: Users,},                     //*admin-  用户管理
      { path:'/students',name:'students',component:Students,},                //*admin- 学生管理
      { path:'/companies',name:'companies',component:Companies},                 //*admin- 企业管理
      { path: '/jobs', name: 'jobs', component: Jobs },                       //*all-   岗位展示 学生可查看、申请；企业可查看 admin-岗位审批
      { path: '/applications', name: 'applications', component: Applications },     //*student-   投递管理 学生可管理自己的offer
      {path: '/hires', name: 'hires', component: Hires},                     //*company-  发布、修改、删除招聘，审核应聘简历
      { path: '/resume',  name: 'resume', component: Resume },                //student-简历管理
      { path: '/announcements', name: 'announcements', component: Announcements },   //admin- 公告管理
      { path: '/faculties', name: 'faculties', component: Faculties },       //admin-  未完成-院系管理
      { path: '/profile', name: 'profile', component: Profile },               //all-    未完成-个人信息修改
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
