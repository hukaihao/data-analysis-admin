package com.devin.data.analysis.admin.core.component;

public interface Action<T> {
    public void doExecute(T object) throws Exception;
}
