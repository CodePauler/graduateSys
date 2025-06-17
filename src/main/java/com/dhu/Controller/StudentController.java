package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.*;
import com.dhu.Service.JobService;
import com.dhu.Service.StudentService;
import com.dhu.Utils.AliyunOSSOperator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @Autowired
    private JobService jobService;


    //    分页查询
    @RoleCheck({"admin"})
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("请求查询参数:{}",studentQueryParam);
        PageResult<StudentInfo> page = studentService.page(studentQueryParam);
        return Result.success(page);
    }


//    根据ID查询
    @RoleCheck({"student","admin"})
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        log.info("查询学生ID: {}", id);
        StudentInfo studentInfo = studentService.getStudentById(id);
        if (studentInfo != null) {
            return Result.success(studentInfo);
        } else {
            return Result.error("学生不存在");
        }
    }

//    更新学生信息
    @PutMapping
    @RoleCheck({"student","admin"})
    public Result updateStudent(@RequestBody Student student) {
        log.info("修改学生信息: {}", student);
        studentService.updateByStudentId(student);
        return Result.success("修改成功");
    }

//    删除学生
    @RoleCheck({"admin"})
    @DeleteMapping
    public Result deleteStudents(@RequestParam List<Integer>ids) {
        log.info("删除学生ID列表: {}", ids);
        studentService.deleteStudent(ids);
        return Result.success("删除成功");
    }

//    上传简历
    @RoleCheck({"student","admin"})
    @PostMapping("/resume/{id}")
    public Result uploadResume(@PathVariable Integer id, @RequestParam("file")MultipartFile resume) throws Exception {
        log.info("上传学生ID: {} 的简历: {}", id, resume);
        if (resume == null || resume.isEmpty()) {
            throw new IllegalArgumentException("上传的简历文件不能为空");
        }
        String originalFileName = resume.getOriginalFilename();
        String extraName = originalFileName.substring(originalFileName.lastIndexOf(".")); //后缀名
        // 简历保存到resume/目录下，文件名为UUID生成的唯一标识符
        String uniqueFileName = "resume/"+UUID.randomUUID().toString().replace("-", "") + extraName;
        // 上传简历到阿里云OSS
        String resumeUrl = aliyunOSSOperator.upload(resume.getBytes(), uniqueFileName);
        studentService.updateResumeById(id, resumeUrl);
        return Result.success(resumeUrl);
    }

//    获取简历
    @GetMapping("/resume/{id}")
    public void getResume(@PathVariable Integer id, HttpServletResponse response) {
        log.info("查询学生ID: {} 的简历", id);
        String resumeUrl = studentService.getResumeById(id);
        if(resumeUrl == null || resumeUrl.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try (InputStream in = new URL(resumeUrl).openStream();
             OutputStream out = response.getOutputStream()) {

            response.setHeader("Content-Disposition", "inline; filename=resume.pdf");

            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

//    获取自己申请的岗位
    @RoleCheck({"student","admin"})
    @GetMapping("/applications")
    public Result getMyApplicationJob(@RequestParam Integer studentId) {
        log.info("查询学生ID: {} 的申请信息", studentId);
        if (studentId == null) {
            return Result.error("学生ID不能为空");
        }
        List<JobInfo> applications = jobService.getMyApplicationJob(studentId);
        return Result.success(applications);
    }
}
