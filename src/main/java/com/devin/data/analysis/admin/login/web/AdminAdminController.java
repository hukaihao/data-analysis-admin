package com.devin.data.analysis.admin.login.web;

import com.devin.data.analysis.admin.core.util.ResponseUtil;
import com.devin.data.analysis.admin.core.util.bcrypt.BCryptPasswordEncoder;
import com.devin.data.analysis.admin.login.annotation.LoginAdmin;
import com.devin.data.analysis.admin.login.biz.DaAdminService;
import com.devin.data.analysis.admin.login.biz.impl.AdminTokenManager;
import com.devin.data.analysis.admin.login.dto.DaAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/admin")
public class AdminAdminController {
    @Autowired
    private DaAdminService adminService;

    @GetMapping("/info")
    public Object info(String token){
        String idDaAdmin = AdminTokenManager.getIdDaAdmin(token);
        if(idDaAdmin == null){
            return ResponseUtil.badArgumentValue();
        }
        DaAdmin admin = adminService.findByIdDaAdmin(idDaAdmin);
        if(admin == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUserName());
        data.put("avatar", admin.getAvatar());

        // 目前roles不支持，这里简单设置admin
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        data.put("roles", roles);
        data.put("introduction", "admin introduction");
        return ResponseUtil.ok(data);
    }

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String username,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<DaAdmin> adminList = adminService.querySelective(username, page, limit, sort, order);
        int total = adminService.countSelective(username, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adminList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody DaAdmin admin){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);

        adminService.addDaAdmin(admin);
        return ResponseUtil.ok(admin);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin String adminId, String id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        DaAdmin admin = adminService.findAdminByIdDaAdmin(id);
        return ResponseUtil.ok(admin);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin String adminId, @RequestBody DaAdmin admin){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        String anotherAdminId = admin.getIdDaAdmin();
        if("000000".equals(anotherAdminId)){
            return ResponseUtil.fail(403, "超级管理员不能修改");
        }

        adminService.addDaAdmin(admin);
        return ResponseUtil.ok(admin);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin String adminId, @RequestBody DaAdmin admin){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        String anotherAdminId = admin.getIdDaAdmin();
        if("000000".equals(anotherAdminId)){
            return ResponseUtil.fail(403, "超级管理员不能删除");
        }
        adminService.deleteByIdDaAdmin(anotherAdminId);
        return ResponseUtil.ok();
    }
}
