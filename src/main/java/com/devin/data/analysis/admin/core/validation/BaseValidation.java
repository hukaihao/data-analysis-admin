package com.devin.data.analysis.admin.core.validation;


import com.devin.data.analysis.admin.core.exception.BaseKnownException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;


public class BaseValidation {
    private static Logger log = LoggerFactory.getLogger(BaseValidation.class);

    public static void throwBaseKnownExceptionIfEmpty(Object obj, String param) {
        String errorMessage = MessageFormat.format("\"参数错误，参数名：{0}，原因：{1}\";", param, param + " 不能为空");
        throwBaseKnownExceptionIfEmpty(obj, 180027, errorMessage);
    }

    public static void throwBaseKnownExceptionIfEmpty(Object obj, Integer errorCode, String errorMessage) {
        if (isEmpty(obj)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownExceptionIfNotEmpty(Object obj, Integer errorCode, String errorMessage) {
        if (!isEmpty(obj)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownExceptionIfEquals(Object obj, Object obj2, Integer errorCode, String errorMessage) {
        if (!isEmpty(obj) && !isEmpty(obj2) && obj.equals(obj2)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownExceptionIfNotEquals(Object obj, Object obj2, Integer errorCode, String errorMessage) {
        if (!obj.equals(obj2)) {
            throwBaseKnownException(errorCode, errorMessage);
        }
    }

    public static void throwBaseKnownException(Integer errorCode, String errorMessage) {
        log.error(errorMessage);
        throw new BaseKnownException(errorCode, errorMessage);
    }

    public static boolean isEmpty(Object obj) {
        if ((obj instanceof Collection && CollectionUtils.isEmpty((Collection<?>) obj)) ||  StringUtils.isEmpty((String)obj)
                || obj instanceof Map && MapUtils.isEmpty((Map<?, ?>) obj)) {
            return true;
        }
        return false;
    }

    public static <T> boolean isContains(Collection<T> collection, T value) {
        if (!BaseValidation.isEmpty(collection) && collection.contains(value)) {
            return true;
        }
        return false;
    }

}
