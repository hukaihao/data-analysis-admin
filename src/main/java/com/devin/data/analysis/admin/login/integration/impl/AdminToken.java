package com.devin.data.analysis.admin.login.integration.impl;

import java.time.LocalDateTime;

public class AdminToken {
    private String idDaAdmin;
    private String token;
    private LocalDateTime expireTime;
    private LocalDateTime updateTime;

    public String getIdDaAdmin() {
        return idDaAdmin;
    }

    public void setIdDaAdmin(String idDaAdmin) {
        this.idDaAdmin = idDaAdmin;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
