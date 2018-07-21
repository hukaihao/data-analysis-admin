package com.devin.data.analysis.admin.core.util;

import com.devin.data.analysis.admin.core.validation.BaseValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.File;
import java.io.IOException;


public class ObjectMapperUtil {
    private static final DefaultObjectMapper objectMapper = new DefaultObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String writeValueAsString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            BaseValidation.throwBaseKnownException(12345, "响应信息转换异常");
        }
        return null;
    }

    public static <T> T readValue(String content, TypeReference valueTypeRef) {
        try {
            return getObjectMapper().readValue(content, valueTypeRef);
        } catch (IOException e) {
            BaseValidation.throwBaseKnownException(12345, e.getMessage());
        }
        return null;
    }

    public static <T> T readValue(String content, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(content, clazz);
        } catch (IOException e) {
            BaseValidation.throwBaseKnownException(12345, e.getMessage());
        }
        return null;
    }

    public static <T> T readValue(File content, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(content, clazz);
        } catch (IOException e) {
            BaseValidation.throwBaseKnownException(12345, e.getMessage());
        }
        return null;
    }
    public static <T> T convertValue(Object content, Class<T> clazz) {
        return getObjectMapper().convertValue(content, clazz);
    }

    private static class DefaultObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = -4825118229728577535L;

        public DefaultObjectMapper() {
            super();
            // 只序列化非空的字段
            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 支持单引号作为key
            this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 忽略不认识的属性名
            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 将驼峰式命名改为带下划线的
            this.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        }
    }
}
