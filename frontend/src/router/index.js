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
import Applications from '@/views/Public/Applications.vue';
import Approvals from '@/views/Admin/Approvals.vue';
const routes = [
  { path: '/',
    name: 'index',
    component:Layout, // 所有页面的父组件，包含导航栏和侧边栏
    children: [
      { path: '/home', name:'home',component:Users, },                      //*all-   未完成-首页，展示公告和毕业生统计情况(/graduates似乎没有什么必要)
      { path:'/users', name: 'users', component: Users,},                     //*admin-  用户管理
      { path:'/students',name:'students',component:Students,},                //*admin- 学生管理
      { path:'/companies',name:'companies',component:Companies},                 //*admin- 企业管理
      { path: '/jobs', name: 'jobs', component: Jobs },                       //*all-   岗位展示 学生可查看、申请；企业可发布、修改
      { path: '/applications', name: 'applications', component: Applications },     //*student-   未完成-投递管理 学生可管理自己的申请
      {path: '/hires', name: 'hires', component: Users},                     //*company-  未完成-发布、修改、删除招聘，审核应聘简历
      { path: '/resume',  name: 'resume', component: Users },                //student-未完成-简历管理
      { path: '/announcements', name: 'announcements', component: Announcements },   //admin- 公告管理
      // { path: '/approvals', name: 'approval', component: Approvals },             //admin-  未完成-岗位审批 考虑合并到/jobs
      { path: '/departments', name: 'departments', component: Users },       //admin-  未完成-院系管理
      { path: '/majors', name: 'majors', component: Users },                 //admin-  #未完成-专业管理 不确定是否合并到院系管理
      { path: '/profile', name: 'profile', component: Users },               //all-    未完成-个人信息修改
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
