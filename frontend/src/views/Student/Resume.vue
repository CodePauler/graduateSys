<template>
    <div class="headerContent" v-if="role === 'student' && showUpload">
        上传简历，寻找你的伯乐
    </div>
    <!-- 上传成功后显示PDF预览 -->
    <div v-if="resumeUrl && !showUpload" style="margin-top: 24px; height: auto;">
        <iframe :src="resumeUrl" width="100%" height="600px" style="border: none;"></iframe>
    </div>
    <el-upload v-if="role === 'student' && showUpload" class="upload-demo" drag :http-request="axiosUpload"
        :befor-upload="beforeUpload">
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
            拖拽至此或<em>点击上传</em>
        </div>
        <template #tip>
            <div class="el-upload__tip">
                只能上传 PDF 格式的文件，且不超过 5MB
            </div>
        </template>
    </el-upload>

</template>

<script setup>
import { uploadConsumeApi, queryStudentResumeApi } from '@/api/student/consume';
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';
const beforeUpload = (file) => {
    const isJpgOrPngOrPdf =
        file.type === 'application/pdf'
    const isLt5MB = file.size / 1024 / 1024 < 5

    if (!isJpgOrPngOrPdf) {
        ElMessage.error('只能上传 PDF 格式的文件!')
        return false
    }
    if (!isLt5MB) {
        ElMessage.error('上传简历大小不能超过 5Mb!');
        return false;
    }
    return isJpgOrPng && isLt500K;
}
const role = localStorage.getItem('role');
const showUpload = ref(role === 'student' && localStorage.getItem('studentId') !== null)
const resumeUrl = ref('');
const studentId = localStorage.getItem('studentId');
const axiosUpload = async (file) => {
    const formData = new FormData();
    formData.append('file', file.file);
    formData.append('studentId', studentId);
    const res = await uploadConsumeApi(formData, studentId);
    if (res.code === 1) {
        ElMessage.success('简历上传成功！');
        showUpload.value = false; // 上传成功后隐藏上传组件
        resumeUrl.value = res.data; // 保存PDF的url
        search(); // 重新查询简历
    } else {
        ElMessage.error('简历上传失败，请重试！');
    }
}
const search = async () => {
    try {
        const res = await queryStudentResumeApi(studentId);
        if (res) {
            showUpload.value = false; // 隐藏上传组件
            resumeUrl.value = res.data; // 如果有已上传的简历，设置预览链接
            const blob = new Blob([res], { type: 'application/pdf' })
            resumeUrl.value = URL.createObjectURL(blob); // 创建预览链接
        }
        else {
            showUpload.value = true; // 如果没有已上传的简历，显示上传组件
        }
    }
    catch (error) {
        console.error('查询简历失败:', error);
        ElMessage.info('请先上传简历');
    }
}
onMounted(async () => {
    search();
});
</script>

<style scoped>
.headerContent {
    text-align: center;
    font-size: 72px;
    margin-top: 120px;
}
</style>