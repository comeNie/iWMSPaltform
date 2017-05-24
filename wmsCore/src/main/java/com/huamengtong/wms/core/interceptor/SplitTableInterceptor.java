package com.huamengtong.wms.core.interceptor;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.splitdb.ShardTableUtil;
import com.huamengtong.wms.utils.ReflectionUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 分表拦截器，对执行的sql进行拦截,通过传入的splitTableKey标示切换到目的table
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class }) })
public class
SplitTableInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            //通过反射获取到当前RoutingStatementHandler对象的delegate属性,RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法
            StatementHandler delegate = (StatementHandler) ReflectionUtils.getFieldValue(handler, "delegate");
            //获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
            BoundSql boundSql = delegate.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject instanceof Map){
                Map mapObj = (Map)parameterObject;
                //判断是否存在分表标示GlobalConstants.SPLIT_TABLE_KEY = "splitTableKey"
                if(mapObj.containsKey(GlobalConstants.SPLIT_TABLE_KEY)) {
                    String splitTable = (String)mapObj.get(GlobalConstants.SPLIT_TABLE_KEY);
                    //解析sql
                    String newSql = ShardTableUtil.parseSql(boundSql.getSql(), splitTable);
                    //利用反射设置当前BoundSql对应的sql属性为我们修改最终执行的Sql
                    ReflectionUtils.setFieldValue(boundSql, "sql", newSql);
                } else {
                    return invocation.proceed();
                }
            }
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
