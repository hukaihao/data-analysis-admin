package com.devin.data.analysis.admin.login.integration;

import com.devin.data.analysis.admin.login.dto.DaAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DaAdminDAO {

    DaAdmin findAdminByIdDaAdmin(String IdDaAdmin);

    void deleteByIdDaAdmin(String IdDaAdmin);
}
