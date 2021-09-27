package com.jtexplorer.util;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

@Service
public class TransactionalUtils {
    @Resource(name="transactionManager")
    private  DataSourceTransactionManager transactionManager;
    /**
     * 获取事务定义
     */
    private  DefaultTransactionDefinition def;
    private  TransactionStatus status;
    /**
     * 事务开始
     */

    public  void start(){
        def= new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        status = transactionManager.getTransaction(def);
    }

    /**
     * 事务回滚（不能和事务提交同时使用，要么提交要么回滚)
     */
    public  void rollBack(){
        transactionManager.rollback(status);
    }

    /**
     * 事务提交（不能喝回滚同时使用，要么提交要么回滚）
     */
    public  void commit(){
        transactionManager.commit(status);
    }

}
