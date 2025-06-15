<template>
    <div v-if="resumeUrl">
        <iframe :src="resumeUrl" width="100%" height="600px" style="border: none;"></iframe>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request'
const resumeUrl = ref('')

onMounted(async () => {
    const token = localStorage.getItem('token')
    console.log('Token:', token)
    const studentId = localStorage.getItem('studentId')
    const res = await request.get(`/students/resume/70127`, {
        responseType: 'blob',
        headers: {
            Token: token
        }
    })
    const blob = new Blob([res], { type: 'application/pdf' })
    resumeUrl.value = URL.createObjectURL(blob)
    window.open(resumeUrl.value)
})
</script>