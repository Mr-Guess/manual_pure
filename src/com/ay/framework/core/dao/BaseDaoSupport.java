package com.ay.framework.core.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ay.framework.core.dao.pagination.PaginationSqlExecutor;
import com.ay.framework.core.dao.pagination.ReflectUtil;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;


/**
 * Dao基础支持类
 *
 * @DateTime: 2012-9-10
 * @author lushigai
 * @version 1.0
 */
@SuppressWarnings("all")
public abstract class BaseDaoSupport extends SqlMapClientDaoSupport
{
    /** sql执行者 */
    private SqlExecutor sqlExecutor;

    /**
     * 实体名称
     * 
     * @return String
     */
    public abstract String getEntityName();

    /**
     * 函数初始化iBatisSQL分页重组
     * 
     * @throws Exception 系统异常
     */
    @SuppressWarnings(
    {
        "deprecation"
    })
    public void initialize() throws Exception
    {
        if (sqlExecutor != null)
        {
            SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
            if (sqlMapClient instanceof ExtendedSqlMapClient)
            {
                ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(), "sqlExecutor",
                                          SqlExecutor.class, sqlExecutor);
            }
        }
    }
    
    /**
     * 设置分页标志
     * 
     * @param enableLimit 是否物理分页
     */
    public void setEnableLimit(boolean enableLimit)
    {
        if (sqlExecutor instanceof PaginationSqlExecutor)
        {
            ((PaginationSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);
        }
    }

    /**
     * 
     * @param id id
     * @return MappedStatement
     */
    public MappedStatement getMappedStatement(String id)
    {
        return ((SqlMapClientImpl) this.getSqlMapClient()).getMappedStatement(id);
    }

    public SqlExecutor getSqlExecutor()
    {
        return sqlExecutor;
    }

    public void setSqlExecutor(SqlExecutor sqlExecutor)
    {
        this.sqlExecutor = sqlExecutor;
    }
}
