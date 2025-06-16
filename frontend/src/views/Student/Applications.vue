<template>
    <div class="headerContent">我的Offer</div>
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
</template>

<script setup>
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref } from 'vue'
import { queryMyApplicationsApi,deleteEmploymentApi } from '@/api/student/Jobs';
const studentId = localStorage.getItem('studentId');
const applicationList = ref([]);
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

const search = async () => {
    const res = await queryMyApplicationsApi(studentId);
    if (res.code === 1) {
        console.log("获取申请列表成功", res.data);
        applicationList.value = res.data;
    }
    else {
        console.error("获取申请列表失败", res.message);
        ElMessage.error("获取申请列表失败: " + res.message);
    }
}
onMounted(async () => {
    search(studentId)
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
</style>
