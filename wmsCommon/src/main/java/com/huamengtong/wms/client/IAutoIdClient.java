package com.huamengtong.wms.client;

public interface IAutoIdClient {

	Long getAutoId(int key);

	Long getAutoId(int key, int count);

	Long getAutoId(int key, int count, int targetTableIndex);
}
