package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    Integer studentId;          // 学号
    Integer userId;             // 外键，关联用户id
    Integer majorId;            // 外键，关联专业id
    String graduationYear;      // 毕业年份
    String employmentStatus;   //待业（默认）/就业
}
