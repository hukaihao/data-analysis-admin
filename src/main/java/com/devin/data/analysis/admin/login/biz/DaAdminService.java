package com.devin.data.analysis.admin.login.biz;


import com.devin.data.analysis.admin.login.dto.DaAdmin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DaAdminService {

    public void addDaAdmin(DaAdmin daAdmin);

    public void deleteByIdDaAdmin(String IdDaAdmin);

    public List<DaAdmin> findAdmin(String username);

    public DaAdmin findByIdDaAdmin(String idDaAdmin);

    public DaAdmin findAdminByIdDaAdmin(String IdDaAdmin);

    public List<DaAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order);

    public int countSelective(String username, Integer page, Integer size, String sort, String order);
}
