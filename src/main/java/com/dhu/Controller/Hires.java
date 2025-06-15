package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * company-我的招聘
 */
@Slf4j
@RestController
@RequestMapping("/hires")
public class Hires {

    /**
     * 查看自己发布的招聘
     */
//    @RoleCheck({"company,admin"})
//    public Result getHires(){
//    //  1.从jobs查询自己发布的岗位
//    //  2.返回岗位列表
//    }
}
