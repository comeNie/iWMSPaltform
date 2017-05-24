package com.huamengtong.wms.core.context;

import java.util.HashMap;
import java.util.Map;

public class CoreContextContainer {

    private static ThreadLocal container = new ThreadLocal();

    public static CoreContext getContext() {
        if (container.get() != null){
            Map map = (Map)container.get();
            return (CoreContext)map.get("coreContext");
        }else{
            return null;
        }
    }

    public static void setContext(CoreContext context) {
        Map map = (Map)container.get();
        if (map == null){
            map = new HashMap();
        }
        map.put("coreContext",context);

        container.set(map);
    }


    public static NeedLogContext getNeedLogContext() {
        if (container.get() != null){
            Map map = (Map)container.get();
            return (NeedLogContext)map.get("needLogContext");
        }else{
            return null;
        }
    }

    public static void setNeedLogContext(NeedLogContext context) {
        Map map = (Map)container.get();
        if (map == null){
            map = new HashMap();
        }
        if(map.get("needLogContext") != null){
            NeedLogContext contextOld = (NeedLogContext) map.get("needLogContext");
            contextOld.addAll(context.getEntityList());
            map.put("needLogContext",contextOld);
        }else{
            map.put("needLogContext",context);
        }
        container.set(map);
    }

    public static void clearNeedLogContext(){
        Map map = (Map)container.get();
        if (map == null){
            map = new HashMap();
        }
        map.remove("needLogContext");
    }

}
