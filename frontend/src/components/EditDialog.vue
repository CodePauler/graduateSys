<template>
    <el-dialog v-model="visible" :title="title" width="500">
        <el-form :model="model">
            <el-form-item v-for="field in fields" :key="field.prop" :label="field.label">
                <component :is="field.component" v-model="model[field.prop]" v-bind="field.props">
                    <template v-if="field.options" #default>
                        <el-option v-for="option in field.options" :key="option.value" :label="option.label"
                            :value="option.value" />
                    </template>
                </component>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="$emit('submit')">确认修改</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
    model: Object,
    fields: Array,   // [{ label, prop, component, props }]
    title: String,
    modelValue: Boolean
})
const emit = defineEmits(['update:modelValue', 'submit'])

const visible = computed({
    get: () => props.modelValue,
    set: (val) => emit('update:modelValue', val)
})
</script>