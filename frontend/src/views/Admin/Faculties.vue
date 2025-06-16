<template>
    <el-tabs v-if="role === 'admin'" v-model="adminTab" class="demo-tabs">
        <el-tab-pane label="学院管理" name="departments">学院管理</el-tab-pane>
        <el-tab-pane label="专业管理" name="majors">专业管理</el-tab-pane>
    </el-tabs>
    <Departments v-if="adminTab === 'departments'" />
    <Majors v-if="adminTab === 'majors'" />
</template>
<script setup>
import { ref } from 'vue'
import Departments from '@/components/Admin/Departments.vue'
import Majors from '@/components/Admin/Majors.vue'
const role = localStorage.getItem('role')
const adminTab = ref('departments')
</script>
