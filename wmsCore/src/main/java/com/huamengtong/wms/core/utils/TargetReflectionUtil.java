package com.huamengtong.wms.core.utils;

import com.huamengtong.wms.core.annotation.ShardTable;
import java.lang.reflect.Method;

public class TargetReflectionUtil {

	public static String getDbShareFieldTarget(String classFullName){
		String dbShareField = "";
		try {
			Class cls = TargetReflectionUtil.class.getClassLoader().loadClass(classFullName);
			for(Method method : cls.getMethods()){
                if(method.isAnnotationPresent(ShardTable.class)){
					ShardTable shardTable = method.getAnnotation(ShardTable.class);
					dbShareField = shardTable.dbShareField().getValue();
					break;
				}
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return  dbShareField;
	}
}
