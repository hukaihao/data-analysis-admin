package com.devin.data.analysis.admin.core.component;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
@Component
public class BaseBatchExecutor<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseBatchExecutor.class);

    public BaseBatchExecutor(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private DataSource dataSource;

    public void execute(List<T> list,Action<T> action) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        SqlSession batchSqlSession =getSqlSession();
        try {
            int batchCount = 500;
            int size = list.size();
            for (int index = 0; index < size; index++) {
                logger.debug("批量处理第【{}】条",index);
                T object = list.get(index);
                action.doExecute(object);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                    //清理缓存，防止溢出
                    batchSqlSession.clearCache();
                }
            }
            batchSqlSession.commit();
        } catch (Exception e) {
            logger.info("批量插入报错，回滚数据 错误原因 exception :{}",e);
            batchSqlSession.rollback();
            throw e;
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
    }
    private SqlSession getSqlSession() throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //关闭session的自动提交
        return sessionFactory.getObject().openSession(ExecutorType.BATCH, false);
    }
}
