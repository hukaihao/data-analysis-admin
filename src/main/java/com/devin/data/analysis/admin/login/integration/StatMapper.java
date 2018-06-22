package com.devin.data.analysis.admin.login.integration;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatMapper {
    List<Map> statUser();
}