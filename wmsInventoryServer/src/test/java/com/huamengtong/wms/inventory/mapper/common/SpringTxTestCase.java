package com.huamengtong.wms.inventory.mapper.common;

import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import org.junit.runner.RunWith;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/inventory/spring-core.xml"})
@TransactionConfiguration(defaultRollback = false)
@ActiveProfiles(value = "develop")
public abstract class SpringTxTestCase extends AbstractJUnit4SpringContextTests {
    @org.junit.Before
    public void init(){
        Environment env = applicationContext.getEnvironment();
        String profile = env.getActiveProfiles()[0];
        System.setProperty("spring.profiles.active",profile);
    }

    public DbShardVO getDbshardVO(){
        DbShardVO dbShardVO = new DbShardVO();
        CurrentUserEntity user = new CurrentUserEntity();
        user.setUserName("admin");
        dbShardVO.setCurrentUser(user);
        dbShardVO.setSource(DbShareField.IN_WH);
        //88取模为0，读取无后缀的Table; 89 取模为1，读取后缀为1的table  Eg:t_smart_sku (88)  t_smart_sku_1 (89)
        dbShardVO.setShardDbId("88");
        dbShardVO.setShardTableId("88");
        dbShardVO.setWarehouseId(88L);
        return dbShardVO;
    }

}