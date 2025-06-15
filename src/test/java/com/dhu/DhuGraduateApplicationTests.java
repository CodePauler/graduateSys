package com.dhu;

import com.dhu.Mapper.AnnouncementMapper;
import com.dhu.Mapper.StudentMapper;
import com.dhu.Pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.lang.Long.valueOf;

@SpringBootTest
class DhuGraduateApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Test
    public void testStudentMapper(){
        StudentQueryParam studentQueryParam;
        studentQueryParam=new StudentQueryParam();
        studentQueryParam.setStudentId(1); // 直接用Integer
        List<StudentInfo> list = studentMapper.list(studentQueryParam);
        for (StudentInfo studentInfo : list) {
            System.out.println(studentInfo);
        }
    }

    @Test
    public void testAnnouncementMapper(){
        // 测试获取所有公告
        AnnouncementQueryParam announcementQueryParam = new AnnouncementQueryParam();
        List<Announcement> announcements = announcementMapper.getAllAnnouncements(announcementQueryParam);
        for (Announcement announcement : announcements) {
            System.out.println(announcement);
        }
    }
}
