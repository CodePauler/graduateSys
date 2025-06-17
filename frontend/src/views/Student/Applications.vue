<template>
    <div class="headerContent">我的Offer</div>
    
    <!-- 搜索栏 -->
    <div class="container">
        <SearchBar :fields="searchFields" :model="searchApplication" @search="filterApplications" @clear="clear" />
    </div>

    <div v-if="!applicationList.length" class="infoContent">还没有申请过岗位哦</div>
    <div v-if="!applicationList.length" class="empty-container">
        <el-empty description=" 暂无Offer">
            <el-button type="primary" size="large" @click="$router.push('/jobs')">
                去申请岗位
            </el-button>
        </el-empty>
    </div>

    <div class="card-list">
        <el-card v-for="app in applicationList" :key="app.id" shadow="hover" class="job-card">
            <div class="card-header">
                <div class="title">
                    <h1>{{ app.title }}</h1>
                    <el-tag class="tag big-tag" :type="getStatusType(app.applicationStatus)">
                        {{ app.applicationStatus }}
                    </el-tag>
                </div>
                <div class="salary">{{ app.salary }}</div>
            </div>

            <div class="card-body">
                <div class="info-tags">
                    <el-tag class="tag big-tag" size="small">{{ app.location }}</el-tag>
                </div>
                <div class="description">{{ app.description }}</div>
                <div v-if="app.applicationStatus === '已录用'" class="info-tags">
                    <el-button-group>
                        <el-button class="big-button" type="danger" plain size="small"
                            @click="refuseOffer(app.jobId, studentId)">拒绝</el-button>
                    </el-button-group>
                </div>
            </div>

            <div class="card-footer">
                <el-tag class="tag big-tag" type="primary">{{ app.companyName }}</el-tag>
                <el-tag class="tag big-tag" size="small" type="info" style="margin-left: auto;">
                    {{ app.applyDate }}
                </el-tag>
            </div>
        </el-card>
    </div>

    <!-- 分页组件 -->
    <div class="pagination-container">
        <el-pagination
            v-model:current-page="pagination.page"
            v-model:page-size="pagination.pageSize"
            :page-sizes="[5, 10, 20, 50]"
            :total="pagination.total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
    </div>
</template>

<script setup>
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref, reactive } from 'vue'
import { queryMyApplicationsApi, deleteEmploymentApi } from '@/api/student/Jobs';
import SearchBar from '@/components/SearchBar.vue';

const studentId = localStorage.getItem('studentId');
const applicationList = ref([]);

// 搜索栏字段配置
const searchFields = [
    { label: '岗位名称', prop: 'title', component: 'el-input', props: { placeholder: '请输入岗位名称', clearable: true } },
    { label: '公司名称', prop: 'companyName', component: 'el-input', props: { placeholder: '请输入公司名称', clearable: true } },
    { label: '工作地点', prop: 'location', component: 'el-input', props: { placeholder: '请输入工作地点', clearable: true } },
    {
        label: '申请状态', prop: 'applicationStatus', component: 'el-select', props: { placeholder: '请选择申请状态', clearable: true },
        options: [
            { label: '待审核', value: '待审核' },
            { label: '已录用', value: '已录用' },
            { label: '不录用', value: '不录用' }
        ]
    }
]

// 搜索条件
const searchApplication = reactive({
    title: '',
    companyName: '',
    location: '',
    applicationStatus: '',
    studentId: studentId,
    page: 1,
    pageSize: 10
})

// 分页配置
const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
})

const refuseOffer = async (jobId, studentId) => {
    ElMessageBox.confirm('您确定要拒绝此Offer吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        const res = await deleteEmploymentApi(jobId, studentId);
        if (res.code === 1) {
            ElMessage.success("拒绝Offer成功");
            search();
        } else {
            ElMessage.error("刷新申请列表失败: " + refreshRes.message);
        }
    }).catch(() => {
        ElMessage.info('已取消操作');
    });

}

const getStatusType = (status) => {
    switch (status) {
        case '已通过':
        case '已录用':
            return 'success'
        case '不通过':
        case '不录用':
            return 'danger'
        case '待审核': return 'primary'
        default: return 'info'
    }
}

// 原始数据
const allApplications = ref([]);

const search = async () => {
    // 始终使用原来的API调用方式
    const res = await queryMyApplicationsApi(studentId);
    if (res.code === 1) {
        console.log("获取申请列表成功", res.data);
        allApplications.value = res.data || [];
        
        // 前端筛选
        filterApplications();
    }
    else {
        console.error("获取申请列表失败", res.message);
        ElMessage.error("获取申请列表失败: " + res.message);
    }
}

// 前端筛选函数
const filterApplications = () => {
    let filtered = [...allApplications.value];
    
    // 按岗位名称筛选
    if (searchApplication.title) {
        filtered = filtered.filter(app => 
            app.title && app.title.toLowerCase().includes(searchApplication.title.toLowerCase())
        );
    }
    
    // 按公司名称筛选
    if (searchApplication.companyName) {
        filtered = filtered.filter(app => 
            app.companyName && app.companyName.toLowerCase().includes(searchApplication.companyName.toLowerCase())
        );
    }
    
    // 按工作地点筛选
    if (searchApplication.location) {
        filtered = filtered.filter(app => 
            app.location && app.location.toLowerCase().includes(searchApplication.location.toLowerCase())
        );
    }
    
    // 按申请状态筛选
    if (searchApplication.applicationStatus) {
        filtered = filtered.filter(app => 
            app.applicationStatus === searchApplication.applicationStatus
        );
    }
    
    // 更新总数
    pagination.total = filtered.length;
    
    // 分页处理
    const startIndex = (pagination.page - 1) * pagination.pageSize;
    const endIndex = startIndex + pagination.pageSize;
    applicationList.value = filtered.slice(startIndex, endIndex);
}

// 清空搜索条件
const clear = () => {
    searchApplication.title = '';
    searchApplication.companyName = '';
    searchApplication.location = '';
    searchApplication.applicationStatus = '';
    pagination.page = 1;
    filterApplications();
}

// 分页处理
const handleCurrentChange = (page) => {
    pagination.page = page;
    filterApplications();
}

const handleSizeChange = (size) => {
    pagination.pageSize = size;
    pagination.page = 1;
    filterApplications();
}
onMounted(async () => {
    search()
})
</script>

<style scoped>
.title {
    display: flex;
    gap: 12px;
    align-items: center;
}

.card-list {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    font-size: 18px;
}

.big-tag {
    font-size: 18px !important;
}

.big-button {
    font-size: 18px !important;
}

.card-body {
    font-size: 18px;
}

.job-card {
    width: 48%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border-radius: 10px;
    color: #666;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
}


.salary {
    color: #f56c6c;
    font-weight: bold;
    font-size: 32px;
}

.info-tags {
    display: flex;
    font-size: 18px;
    gap: 8px;
    margin: 12px;
}

.card-footer {
    display: flex;
    align-items: center;
    border-top: 1px solid #f0f0f0;
    padding-top: 8px;
    margin-top: 8px;
    font-size: 13px;
    color: #666;
}

.empty-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 300px;
}

.infoContent {
    text-align: center;
    font-size: 72px;
    margin-top: 120px;
    color: #666
}

.container {
    margin: 20px;
}

.pagination-container {
    display: flex;
    justify-content: center;
    margin: 20px 0;
}
</style>
