<template>
    <h1>用户管理</h1>
    <!-- 搜索栏 -->
    <div class="container">
        <SearchBar :fields="searchFields" :model="searchUser" @search="search" @clear="clear" />
    </div>
    <div class="container">
        <!-- 数据表格 -->
        <DataTable :data="userInfo" :columns="tableColumns" :actions="tableActions" :pagination="pagination"
            @page-change="handleCurrentChange" @size-change="handleSizeChange" />"
    </div>

    <!-- 编辑弹窗 -->
    <EditDialog v-model="dialogFormVisible" :title="'用户编辑'" :model="user" :fields="editFields" @submit="saveUser" />
</template>
<script setup>
import DataTable from '@/components/DataTable.vue';
import SearchBar from '@/components/SearchBar.vue';
import EditDialog from '@/components/EditDialog.vue';
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { queryUsersApi, queryUserByIdApi, updateUserApi, deleteUserApi } from '@/api/admin/users';
// 搜索栏字段配置
const searchFields = [
    { label: '用户名', prop: 'username', component: 'el-input', props: { placeholder: '请输入用户名', clearable: true } },
    { label: '姓名', prop: 'name', component: 'el-input', props: { placeholder: '请输入姓名', clearable: true } },
    {
        label: '身份', prop: 'role', component: 'el-select', props: { placeholder: '请选择身份', clearable: true },
        options: [
            { label: '管理员', value: 'admin' },
            { label: '学生', value: 'student' },
            { label: '企业', value: 'company' }
        ]
    },
    {
        label: '性别', prop: 'gender', component: 'el-select', props: { placeholder: '请选择性别', clearable: true },
        options: [
            { label: '男', value: '男' },
            { label: '女', value: '女' }
        ]
    }
]
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})
// 分页处理
const handleCurrentChange = (page) => {
    pagination.page = page;
    searchUser.page = page;
    search();
    console.log(`当前页码: ${page}`)
}
const handleSizeChange = (size) => {
    pagination.pageSize = size;
    searchUser.pageSize = size;
    search();
    console.log(`每条页数: ${size}`)
}
const searchUser = reactive({
    username: '',
    name: '',
    gender: '',
    role: '',
    page: 1,
    pageSize: 10
})
// 搜索用户列表
const search = async () => {
    const res = await queryUsersApi(searchUser);
    if (res.code === 1) {
        console.log("查询用户列表成功", res.data);
        userInfo.value = res.data.rows;
        pagination.total = res.data.total;
    } else {
        console.error("查询用户列表失败", res.data);
    }
}
// 清空搜索条件
const clear = () => {
    searchUser.username = '';
    searchUser.name = '';
    searchUser.gender = '';
    searchUser.role = '';
    searchUser.page = 1;
    searchUser.pageSize = 10;
    search();
}


// 表格列配置
const userInfo = ref([])
const tableColumns = [
    { prop: 'username', label: '用户名' },
    { prop: 'name', label: '姓名' },
    { prop: 'role', label: '身份' },
    { prop: 'gender', label: '性别' },
    { prop: 'email', label: '邮箱' },
    { prop: 'phone', label: '电话' }
]

// 表格操作按钮
const tableActions = [
    {
        label: '编辑',
        type: 'primary',
        onClick: async (row) => {
            const result = await queryUserByIdApi(row.id)
            if (result.code === 1) {
                user.value = result.data
                dialogFormVisible.value = true
            }
        }
    },
    {
        label: '删除',
        type: 'danger',
        onClick: (row) => {
            ElMessageBox.confirm('是否确认删除用户？', '警告', {
                confirmButtonText: '确认删除',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(async () => {
                const result = await deleteUserApi(row.id)
                if (result.code === 1) {
                    ElMessage.success('成功删除')
                    search()
                } else {
                    ElMessage.error(result.msg)
                }
            }).catch(() => {
                ElMessage.info('取消删除')
            })
        }
    }
]
// 编辑用户信息（查询回显） 以及弹窗
const dialogFormVisible = ref(false)
const user = ref({})
const editFields = [
    { label: '用户名', prop: 'username', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '密码', prop: 'password', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '姓名', prop: 'name', component: 'el-input', props: { autocomplete: 'off' } },
    {
        label: '身份', prop: 'role', component: 'el-select', props: { placeholder: '请选择身份' }, options: [
            { label: '管理员', value: 'admin' },
            { label: '学生', value: 'teacher' },
            { label: '企业', value: 'company' }
        ]
    },
    {
        label: '性别', prop: 'gender', component: 'el-select', props: { placeholder: '请选择性别' }, options: [
            { label: '男', value: '男' },
            { label: '女', value: '女' }
        ]
    },
    { label: '邮箱', prop: 'email', component: 'el-input', props: { autocomplete: 'off' } },
    { label: '电话', prop: 'phone', component: 'el-input', props: { autocomplete: 'off' } }
]
// 保存修改
const saveUser = async () => {
    dialogFormVisible.value = false;
    const result = await updateUserApi(user.value);
    if (result.code === 1) {
        ElMessage.success('用户信息更新成功')
        search()
    } else {
        ElMessage.error(result.msg)
    }
}

onMounted(() => {
    search();
});
</script>
<style scoped>
.container {
    margin: 20px;
}
</style>