<!-- Layout.vue -->
<template>
  <div>
    <!-- 如果是登录/注册页面，直接显示内容 -->
    <router-view v-if="isAuthPage" />

    <!-- 否则，显示主框架 -->
    <el-container v-else class="layout-container">
      <el-header
        style="background-color: #409EFF; color: #fff; display: flex; justify-content: space-between; align-items: center;">
        <div style="display: flex; align-items: center;">
          <el-icon color="#fff" style="margin-right: 8px;">
            <House />
          </el-icon>
          <h2 style="color: #fff; margin: 0;">东华大学毕业生智能管理系统</h2>
        </div>
        <el-button type="primary" plain @click="logout">退出登录</el-button>
      </el-header>
      <el-container style:="height: 500px;">
        <el-aside width="256px" class="sidebar">
          <el-scrollbar>
            <el-menu :default-active="activeMenu" class="el-menu-vertical-demo" router>
              <el-menu-item index="/home">
                <el-icon>
                  <HomeFilled />
                </el-icon>
                <span>首页</span>
              </el-menu-item>
              <!-- 学生：可查看自己的简历、自己的投递 -->
              <template v-if="role === 'student'">
                <el-menu-item index="/jobs">
                  <el-icon>
                    <Briefcase />
                  </el-icon>
                  <span>岗位列表</span>
                </el-menu-item>
                <el-menu-item index="/resume">
                  <el-icon>
                    <List />
                  </el-icon>
                  <span>我的简历</span>
                </el-menu-item>
                <el-menu-item index="/applications">
                  <el-icon>
                    <Document />
                  </el-icon>
                  <span>我的Offer</span>
                </el-menu-item>
              </template>
              <!-- 企业：可发布、查看、修改自己的招聘 -->
              <template v-if="role === 'company'">
                <el-menu-item index="/jobs">
                  <el-icon>
                    <Briefcase />
                  </el-icon>
                  <span>岗位列表</span>
                </el-menu-item>
                <el-menu-item index="/hires">
                  <el-icon>
                    <Document />
                  </el-icon>
                  <span>我的招聘</span>
                </el-menu-item>
              </template>
              <!-- 管理员：用户管理、公告管理 -->
              <template v-if="role === 'admin'">
                <el-menu-item index="/users">
                  <el-icon>
                    <User />
                  </el-icon>
                  <span>用户管理</span>
                </el-menu-item>
                <el-menu-item index="/students">
                  <el-icon>
                    <User />
                  </el-icon>
                  <span>毕业生管理</span>
                </el-menu-item>
                <el-menu-item index="/companies">
                  <el-icon>
                    <User />
                  </el-icon>
                  <span>企业管理</span>
                </el-menu-item>
                <el-menu-item index="/jobs">
                  <el-icon>
                    <Setting />
                  </el-icon>
                  <span>岗位管理</span>
                </el-menu-item>
                <el-menu-item index="/announcements">
                  <el-icon>
                    <Check />
                  </el-icon>
                  <span>公告管理</span>
                </el-menu-item>
                <el-menu-item index="/faculties">
                  <el-icon>
                    <HelpFilled />
                  </el-icon>
                  <span>院系管理</span>
                </el-menu-item>
              </template>
              <el-menu-item index="/profile">
                <el-icon>
                  <User />
                </el-icon>
                <span>个人中心</span>
              </el-menu-item>
            </el-menu>
          </el-scrollbar>
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
.layout-container .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container .el-aside {
  color: var(--el-text-color-primary);
}

.layout-container .el-menu {
  border-right: none;
}

.layout-container .el-main {
  padding: 0;
  overflow: visible;
}

.layout-container {
  min-height: 100vh;
}

.layout-container .el-container {
  min-height: calc(100vh - 60px);
}

.layout-container .el-aside {
  height: calc(100vh - 60px);
  overflow-y: auto;
}

span {
  font-size: 16px;
  color: #333;
}
</style>
