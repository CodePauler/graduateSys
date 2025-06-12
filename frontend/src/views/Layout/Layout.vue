<!-- Layout.vue -->
<template>
  <div>
    <!-- 如果是登录/注册页面，直接显示内容 -->
    <router-view v-if="isAuthPage" />

    <!-- 否则，显示主框架 -->
    <el-container v-else>
      <el-header
        style="background-color: #409EFF; color: #fff; display: flex; justify-content: space-between; align-items: center;">
        <div style="display: flex; align-items: center;">
          <el-icon color="#fff" style="margin-right: 8px;">
            <House />
          </el-icon>
          <h2 style="color: #fff; margin: 0;">智能管理系统</h2>
        </div>
        <el-button type="primary" plain @click="logout">退出登录</el-button>
      </el-header>
      <el-container>
        <el-aside width="200px" class="sidebar">
          <el-menu :default-active="activeMenu" class="el-menu-vertical-demo" router>
            <el-menu-item index="/">
              <el-icon>
                <HomeFilled />
              </el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-menu-item index="/jobs">
              <el-icon>
                <Briefcase />
              </el-icon>
              <span>岗位列表</span>
            </el-menu-item>
            <el-menu-item index="/announcements">
              <el-icon>
                <User />
              </el-icon>
              <span>公告列表</span>
            </el-menu-item>
            <!-- 学生：可查看自己的简历、自己的投递 -->
            <template v-if="role === 'student'">
              <el-menu-item index="/student/resume">
                <el-icon>
                  <User />
                </el-icon>
                <span>我的简历</span>
              </el-menu-item>
              <el-menu-item index="/student/jobs">
                <el-icon>
                  <Briefcase />
                </el-icon>
                <span>我的投递</span>
              </el-menu-item>
            </template>
            <!-- 教师：岗位审核、岗位管理 -->
            <template v-if="role === 'teacher'">
              <el-menu-item index="/announcements">
                <el-icon>
                  <Briefcase />
                </el-icon>
                <span>发布公告</span>
              </el-menu-item>
              <el-menu-item index="/teacher/audit">
                <el-icon>
                  <Check />
                </el-icon>
                <span>岗位审核</span>
              </el-menu-item>
            </template>
            <!-- 企业：发布岗位、收到简历 -->
            <template v-if="role === 'company'">
              <el-menu-item index="/company/jobs">
                <el-icon>
                  <Briefcase />
                </el-icon>
                <span>发布岗位</span>
              </el-menu-item>
              <el-menu-item index="/company/resumes">
                <el-icon>
                  <User />
                </el-icon>
                <span>简历审核</span>
              </el-menu-item>
            </template>
            <!-- 管理员：用户管理、公告管理 -->
            <template v-if="role === 'admin'">
              <el-menu-item index="/users">
                <el-icon>
                  <Briefcase />
                </el-icon>
                <span>用户管理</span>
              </el-menu-item>
              <el-menu-item index="/graduates">
                <el-icon>
                  <User />
                </el-icon>
                <span>毕业生统计</span>
              </el-menu-item>

            </template>
            <el-menu-item index="/profile">
              <el-icon>
                <User />
              </el-icon>
              <span>个人中心</span>
            </el-menu-item>
          </el-menu>
        </el-aside>


        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, watch, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { HomeFilled, User, Briefcase, Check, Setting } from '@element-plus/icons-vue';

const route = useRoute();
const router = useRouter();

const activeMenu = ref(route.path);
const role = ref(localStorage.getItem('role'));

const isAuthPage = ref(['login', 'register'].includes(route.name));

// 监听路由变化，更新是否是登录/注册页
watch(() => route.name, (newVal) => {
  isAuthPage.value = ['login', 'register'].includes(newVal);
});

watch(() => route.path, (newPath) => {
  activeMenu.value = newPath;
});

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  ElMessage.success('已退出登录');
  router.push('/login');
};
onMounted(() => {
  activeMenu.value = route.path;
});
</script>
<style scoped>
.header {
  background-image: linear-gradient(to right, #00547d, #007fa4, #00aaa0, #00d072, #a8eb12);
}

.right_tool {
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}

</style>
