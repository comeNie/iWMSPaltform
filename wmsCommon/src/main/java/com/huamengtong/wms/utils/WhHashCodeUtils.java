package com.huamengtong.wms.utils;

public class WhHashCodeUtils {
    public static final int dbTable = 256;

    public static void main(String[] args) {

        String index = "908";
        System.out.println( Math.abs(index.hashCode()) % dbTable );

        index = "909";
        System.out.println( Math.abs(index.hashCode()) % dbTable );

        index = "1059";
        System.out.println( Math.abs(index.hashCode()) % dbTable );

        for(int i= 0;i<10000 ;i++ ){
            if(Math.abs(String.valueOf(i).hashCode()) % dbTable == 3){
                System.out.println(i+"  hashCode-->" + String.valueOf(i).hashCode());
                break;
            }
        }
    }
}
