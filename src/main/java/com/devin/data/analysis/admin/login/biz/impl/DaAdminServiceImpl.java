package com.devin.data.analysis.admin.login.biz.impl;

import com.devin.data.analysis.admin.login.biz.DaAdminService;
import com.devin.data.analysis.admin.login.dto.DaAdmin;
import com.devin.data.analysis.admin.login.dto.DaAdmin.Column;
import com.devin.data.analysis.admin.login.dto.example.DaAdminExample;
import com.devin.data.analysis.admin.login.integration.DaAdminDAO;
import com.devin.data.analysis.admin.login.integration.DaAdminMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DaAdminServiceImpl implements DaAdminService {

    @Resource
    private DaAdminMapper adminMapper;

    @Autowired
    private DaAdminDAO daAdminDAO;

    public void addDaAdmin(DaAdmin daAdmin){
        adminMapper.insert(daAdmin);
    }

    public void deleteByIdDaAdmin(String IdDaAdmin){
        daAdminDAO.deleteByIdDaAdmin(IdDaAdmin);
    }

    public List<DaAdmin> findAdmin(String username) {
        DaAdminExample example = new DaAdminExample();
        example.or().andUserNameEqualTo(username).andStatusEqualTo("Y");
        return adminMapper.selectByExample(example);
    }

    public DaAdmin findByIdDaAdmin(String idDaAdmin){
        return adminMapper.selectByPrimaryKeySelective(idDaAdmin,result);
    }

    public DaAdmin findAdminByIdDaAdmin(String IdDaAdmin){
        return daAdminDAO.findAdminByIdDaAdmin(IdDaAdmin);
    }

    private final Column[] result = new Column[]{Column.idDaAdmin, Column.userName, Column.avatar};

    public List<DaAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        DaAdminExample example = new DaAdminExample();
        DaAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUserNameLike("%" + username + "%");
        }
        criteria.andStatusEqualTo("Y");

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int countSelective(String username, Integer page, Integer size, String sort, String order) {
        DaAdminExample example = new DaAdminExample();
        DaAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUserNameLike("%" + username + "%");
        }
        criteria.andStatusEqualTo("Y");

        return (int)adminMapper.countByExample(example);
    }
}
