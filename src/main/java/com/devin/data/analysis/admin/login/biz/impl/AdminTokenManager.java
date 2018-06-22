package com.devin.data.analysis.admin.login.biz.impl;


import com.devin.data.analysis.admin.core.util.CharUtil;
import com.devin.data.analysis.admin.login.integration.impl.AdminToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AdminTokenManager {
    private static Map<String, AdminToken> tokenMap = new HashMap<>();
    private static Map<String, AdminToken> idMap = new HashMap<>();

    public static String getIdDaAdmin(String token) {

        AdminToken userToken = tokenMap.get(token);
        if (userToken == null) {
            return null;
        }

        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            tokenMap.remove(token);
            idMap.remove(userToken.getIdDaAdmin());
            return null;
        }

        return userToken.getIdDaAdmin();
    }


    public static AdminToken generateToken(String idDaAdmin) {
        AdminToken userToken = null;

//        userToken = idMap.get(id);
//        if(userToken != null) {
//            tokenMap.remove(userToken.getToken());
//            idMap.remove(id);
//        }

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusDays(1);

        userToken = new AdminToken();
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        userToken.setIdDaAdmin(idDaAdmin);
        tokenMap.put(token, userToken);
        idMap.put(idDaAdmin, userToken);

        return userToken;
    }
}
