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
                    <el-button type="primary" round @click="edit(scope.row.id)">编辑</el-button>
                    <el-button type="danger" round @click="remove(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <!-- 分页条 -->
    <div class="container">
        <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
            :page-sizes="[5, 10, 20, 30, 40, 50, 75, 100]" :background="background"
            layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />
    </div>

    <!-- 用户编辑模态框 -->
    <el-dialog v-model="dialogFormVisible" title="用户编辑" width="500" :lock-scroll="false">
        <el-form :model="user" ref="userFormRef">
            <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input v-model="user.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="密码" :label-width="formLabelWidth">
                <el-input v-model="user.password" autocomplete="off" />
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
                <el-button type="primary" @click="saveUser">
                    确认修改
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { deleteUserApi, queryUserByIdApi, queryUsersApi, updateUserApi } from '@/api/user'
import { ElMessage, ElMessageBox } from "element-plus";

const searchUser = reactive({
    username: '',
    name: '',
    gender: '',
    role: '',
    page: 1,
    pageSize: 10
})
const userInfo = ref([

])

// 分页相关数据
const currentPage = ref(1) // 当前页码
const pageSize = ref(10) // 每页条数
const background = ref(true) // 是否显示背景色
const total = ref(0) // 总条数
//页码变化时触发
const search = async () => {
    const res = await queryUsersApi(searchUser)
    console.log(res)
    if (res.code === 1) {
        total.value = res.data.total
        userInfo.value = res.data.rows
        console.log("查询到用户列表", userInfo.value);
    }
};
const handleCurrentChange = (val) => {
    searchUser.page = val; // 更新当前页码
    search(); // 重新查询数据
    console.log(`当前页码: ${val}`)
}

const handleSizeChange = (val) => {
    searchUser.pageSize = val; // 更新每页条数
    search();
    console.log(`每页条数: ${val}`)
}
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
const user = ref({
    userName: '',
    password: '',
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
    const result = await queryUserByIdApi(id);
    if (result.code === 1) {
        console.log("查询用户信息成功", result.data);
        user.value = result.data;
    }
}

// 编辑用户：确认修改（更新信息）
const saveUser = async () => {
    let result;
    dialogFormVisible.value = false;
    result = await updateUserApi(user.value);
    if (result.code === 1) {
        console.log("用户信息更新成功", result.data);
        ElMessage.success('用户信息更新成功');
    } else {
        console.error("用户信息更新失败", result.msg);
        ElMessage.error(result.msg);
        return;
    }
    search(); // 刷新用户列表
}



// 删除用户
const remove = (ids) => {
    ElMessageBox.confirm(
        '是否确认删除用户？',
        '警告',
        {
            confirmButtonText: '确认删除',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            const result = await deleteUserApi(ids);
            console.log("删除用户请求结果", result);
            if (result.code === 1) {
                ElMessage({
                    type: 'success',
                    message: '成功删除',
                })
            }
            else {
                ElMessage({
                    type: 'error',
                    message: result.data,
                })
            }
            search();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
}


onMounted(() => {
    search()
});
</script>
<style scoped>
.container {
    margin: 20px;
}
</style>