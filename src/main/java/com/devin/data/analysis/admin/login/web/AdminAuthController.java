package com.devin.data.analysis.admin.login.web;

import com.devin.data.analysis.admin.core.util.JacksonUtil;
import com.devin.data.analysis.admin.core.util.ResponseUtil;
import com.devin.data.analysis.admin.core.util.bcrypt.BCryptPasswordEncoder;
import com.devin.data.analysis.admin.login.annotation.LoginAdmin;
import com.devin.data.analysis.admin.login.biz.DaAdminService;
import com.devin.data.analysis.admin.login.biz.impl.AdminTokenManager;
import com.devin.data.analysis.admin.login.dto.DaAdmin;
import com.devin.data.analysis.admin.login.integration.impl.AdminToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/login")
public class AdminAuthController {

    private final static Logger logger = LoggerFactory.getLogger(AdminAuthController.class);

    @Autowired
    private DaAdminService adminService;

    /*
     *  { username : value, password : value }
     */
    @PostMapping("/login")
    public Object login(@RequestBody String body){
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        logger.debug(" input username is {}",username);
        logger.debug(" input password is {}",password);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return ResponseUtil.badArgument();
        }

        List<DaAdmin> adminList = adminService.findAdmin(username);
        System.out.println("userName = " + adminList.size());
        Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
        if(adminList.size() == 0){
            return ResponseUtil.badArgumentValue();
        }
        DaAdmin admin = adminList.get(0);
        /*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password, admin.getPassword())){
            return ResponseUtil.fail(403, "账号密码不对");
        }*/
        logger.debug("password is {}",password);
        logger.debug("query password is {}",admin.getPassword());

        if(!password.equals(admin.getPassword())){
            return ResponseUtil.fail(403, "账号密码不对");
        }

        String adminId = admin.getIdDaAdmin();
        // token
        AdminToken adminToken = AdminTokenManager.generateToken(adminId);

        return ResponseUtil.ok(adminToken.getToken());
    }


    @PostMapping("/logout")
    public Object login(@LoginAdmin Integer adminId){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        return ResponseUtil.ok();
    }
}
