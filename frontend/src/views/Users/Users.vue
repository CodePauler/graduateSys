<template>
    <h1>用户管理</h1>
    <!-- 搜索栏 -->
    <div class="container">
        <el-form :inline="true" :model="searchUser" class="demo-form-inline">
            <el-form-item label="用户名">
                <el-input v-model="searchUser.username" placeholder="请输入用户名" clearable />
            </el-form-item>

            <el-form-item label="姓名">
                <el-input v-model="searchUser.name" placeholder="请输入姓名" clearable />
            </el-form-item>

            <el-form-item label="身份">
                <el-select v-model="searchUser.role" placeholder="请选择学生身份">
                    <el-option label="管理员" value="admin" />
                    <el-option label="教师" value="teacher" />
                    <el-option label="企业" value="company" />
                </el-select>
            </el-form-item>

            <el-form-item label="性别">
                <el-select v-model="searchUser.gender" placeholder="请选择学生性别">
                    <el-option label="男" value="1" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="search">查询</el-button>
                <el-button type="info" @click="clear">清空</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div class="container">
        <el-table :data="userInfo" style="width: 100%">
            <el-table-column prop="username" label="用户名" width="180" />
            <el-table-column prop="name" label="姓名" width="180" />
            <el-table-column prop="role" label="身份" />
            <el-table-column prop="gender" label="性别" />
            <el-table-column prop="email" label="邮箱" />
            <el-table-column prop="phone" label="电话" />
            <el-table-column label="操作" width="180">
                <template #default="scope">
                    <el-button size="small" @click="edit(scope.row.id)">编辑</el-button>
                    <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>


    <!-- 用户编辑模态框 -->
    <el-dialog v-model="dialogFormVisible" title="用户编辑" width="500">
        <el-form :model="user">
            <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input v-model="user.userName" autocomplete="off" />
            </el-form-item>
            <el-form-item label="姓名" :label-width="formLabelWidth">
                <el-input v-model="user.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="身份" :label-width="formLabelWidth">
                <el-select v-model="user.role" placeholder="请选择身份">
                    <el-option label="学生" value="admin" />
                    <el-option label="企业" value="company" />
                    <el-option label="管理员" value="admin" />
                </el-select>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth">
                <el-select v-model="user.gender" placeholder="请选择性别">
                    <el-option label="男" value="男" />
                    <el-option label="女" value="女" />
                </el-select>
            </el-form-item>
            <el-form-item label="邮箱" :label-width="formLabelWidth">
                <el-input v-model="user.email" autocomplete="off" />
            </el-form-item>
            <el-form-item label="电话" :label-width="formLabelWidth">
                <el-input v-model="user.phone" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">
                    确认修改
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { queryUsersApi } from '@/api/user'
const searchUser = reactive({
    username: '',
    name: '',
    gender: '',
    role: '',
    page: 1,
    pageSize: 10
})
const userInfo = ref([])
const total = ref(0)
const search = async () => {
    const res = await queryUsersApi(searchUser)
    console.log(res)
    if (res.code === 1) {
        userInfo.value = res.data.rows
        total.value = res.data.total
        console.log(userInfo.value);

    }
};
const clear = () => {
    searchUser.username = ''
    searchUser.name = ''
    searchUser.gender = ''
    searchUser.role = ''
    searchUser.page = 1
    searchUser.pageSize = 10
    search()
}


// 编辑用户 对话框
const user = reactive({
    userName: '',
    name: '',
    role: '',
    gender: '',
    email: '',
    phone: ''
})
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
const edit = async (id) => {
    dialogFormVisible.value = true;
    const result = await queryUsersApi({ id });
    console.log("编辑用户：", result);
}

onMounted(() => {
    search()
});
</script>
