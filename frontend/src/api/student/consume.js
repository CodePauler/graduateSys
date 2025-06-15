import request from "@/utils/request";

// 上传简历
export const uploadConsumeApi = (formData, studentId) => {
    return request.post(`/students/resume/${studentId}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data' // 设置请求头为表单数据
        }
    });
}

// 获取简历
export const queryStudentResumeApi = (studentId) => {
    return request.get(`/students/resume/${studentId}`, {
        responseType: 'blob' // 设置响应类型为 Blob
    });
}