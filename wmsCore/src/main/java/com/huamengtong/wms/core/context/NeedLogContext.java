package com.huamengtong.wms.core.context;

import java.util.ArrayList;
import java.util.List;

public class NeedLogContext<T> {

    List<T> arrayList = new ArrayList<T>();

    public void add(T entity){
        this.arrayList.add(entity);
    }

    public void addAll(List innerList){
        this.arrayList.addAll(innerList);
    }

    public List<T> getEntityList(){
        return arrayList;
    }
}
