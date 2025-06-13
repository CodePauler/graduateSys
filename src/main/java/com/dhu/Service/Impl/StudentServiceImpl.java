package com.dhu.Service.Impl;

import com.dhu.Mapper.StudentMapper;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Student;
import com.dhu.Pojo.StudentInfo;
import com.dhu.Pojo.StudentQueryParam;
import com.dhu.Service.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public PageResult<StudentInfo> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPageNum(),studentQueryParam.getPageSize());
        Page<StudentInfo> p = (Page<StudentInfo>) studentMapper.list(studentQueryParam);
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Override
    public StudentInfo getStudentById(Integer studentId) {
        return studentMapper.getById(studentId);
    }

    @Override
    public void deleteStudent(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("删除学生ID列表不能为空");
        }
        studentMapper.deleteByIds(ids);
    }

    @Override
    public void updateByStudentId(Student student) {
        studentMapper.updateByStudentId(student);
    }

}
