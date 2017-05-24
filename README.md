# WMSPlatform

# 参考规范


### 1.maven依赖管理

应用的依赖继承最外层parent，maven管理规则如下：

* 所有依赖的版本号都要从主pom的dependencyManagement中指定，原则上不允许在子模块中直接指定依赖版本号。基础依赖比如Spring、dubbo等中间件的版本号提取成property；

* 需要新添加依赖的时候，先从主pom和当前pom中进行搜索，以免重复添加；添加到主pom的dependencyManagement中，在子pom的dependencies进行依赖；

* 建议：修改现有依赖的版本号时，比较修改前后lib文件夹的依赖包数量和版本号，尽量避免运行时的包冲突。

* 环境参数 JDK 8 , Dubbo 2.5.3  ,RocketMQ 3.2.6


### 2.模块划分和模块职责

wmsAppweb应用的功能是接收http请求，来进行路由和响应，其中尽量不要包含过多的业务，对业务逻辑是通过调用其他dubbo服务来进行的。

java应用的部署单元下面划分多个模块，以WMSPlatform为例，每个web工程由以下几个模块组成：

    WMSPlatform(parent module)  主模块
        |------wmsAppweb   : web请求处理和模板处理模块，接受web请求，转发调用service模块提供的接口；
        |------wmsCommon   ：通用工具类，常量定义、枚举、读取系统配置文件等基本工具类；
        |------wmsCore     ：formwork core 框架内核，提供分库分表，统一异常拦截处理等；
        |------wmsConfig   ：配置中心，配置所有的服务依赖及registry；
        |------wmsInterface  : 业务接口；
        |------wmsPOJO       ： POJO类(VO/PO/DTO/Entity)；
        |------wmsAutoIdServer : AutoIdServer服务,用来获取唯一id值，一般用作业务主键使用；
        |------wmsMainServer ：MainServer服务，基础服务，一般用来处理用户、基础数据维护服务等功能；
        |------wmsSkuServer ：SkuServer服务，商品Sku管理服务；
        |------wmsInventoryServer ：InventoryServer ，库存服务，库存模块服务化处理；
        |------wmsInwhServer ：InwhServer ，入库服务，入库模块服务化处理；
        |------wmsOutwhServer ：OutwhServer ，出库服务，出库模块服务化处理；
        |------wmsReportServer ：ReportServer ，表报服务，业务表报服务化处理；
        |------wmsMQConsumer ：MQConsumer ，消费服务(Consumer消费者)；
        |------wmsRocketMQServer ：MQServer，消息主服务( mqnamesrv服务)；
        |------wmsMQClient ：MQClient ，消息生产(producer生产者，客户端API引用依赖)；
        |------wmsApi  ：wmsapi 服务；
        ...
    服务启动顺序: AutoIdServer --> wmsMainServer --> wmsSkuServer --> --> wmsInventoryServer --> wmsInwhServer -->wmsOutwhServer --> wmsReportServer --> wmsMQConsumer -->wmsAppweb
--- 
 
### 3.开发说明        


#### POJO说明

目前定义3种类别: dto、entity、vo
* DTO（Data Transfer Object ） 数据传输对象，一般用来将标示层数据传输到服务层,例如 XXXController --> XXXService；
* Entity 也可以称为PO (Persistent Object) 持久化对象，用来与持久层的数据结构做映射，一般情况下与数据库字段属性一一对应；
* VO (View Object) 视图对象，用于展示层，一般用来封装数据对象,不做强制要求.

`注意：DTO 与 Entity需要继承公用实体类 BasePO,Controller层使用DTO对象传输信息到Service层` 

    BasePO.java
    ...
    private String orderBy;
    private Integer offset = 0;
    private Integer pageSize = 30;
    private Integer page = 1;
    ...



#### Controller说明

* HealthController  心跳检测Controller，用来监控服务健康度  
* BaseController 公用Controller
 所以业务相关的Controller需要继承BaseController，请求方式基于Spring的REST模式:GET/POST/PUT/DELETE等，
 统一返回ResponseResult对象,BaseController提供了一些通用的返回ResponseResult，可根据实际需求返回

    getSucMessage()   无参数返回成功;
    getSucMessage(String messagekey , Object ...params) 参数返回成功，参数对应资源文件里的占位符;
    getFaultMessage() 无参数返回;
    getFaultMessage(String messageKey , Object ...params) 参数返回失败.

* 分库分表标示 DbShareField  
  在BaseController调用getDbShardField方法用来传递DbShard标示，对需要进行分库分表的业务做处理.

    ...

    DbShardVO getDbShardVO(DbShareField ...source)

    ENUM --> DbShareField: DEFAULT("main") ,SKU("sku"),ORDER("order"),IN_WH("inwh"),OUT_WH("outwh")

    getDbShardVO(DbShareField.SKU)    -- 表示读取sku库 ;
    getDbShardVO(DbShareField.IN_WH)  -- 表示读取inwh库 ;
    getDbShardVO(DbShareField.OUT_WH) -- 表示读取outwh库 ;

    其它依次类推
    ...

   如果不传递DbShareField标示不需要分库分表，读取main库，如果传递根据对应的标示，最终落到对应的数据库节点上

   目前数据库拆分范围 0~256 ,理论上每个数据库可以拆分成256个share，每张表可以拆分成256张table

* 方法返回
查询统一调用getSucResultData方法返回数据集
新增、修改、删除统一调用getMessage方法返回具体执行信息




#### 消息国际化处理

消息统一定义在messages_zh_CN.properties文件中，系统启动时加载资源文件，然后通过key获取对应的信息，基本消息CODE

S0001 = 操作成功！
S0002 = 保存成功！
S0003 = 修改成功！

E0001 = 操作失败！

其它Key可以根据业务来定义




#### 方法及方法名前缀规范

* Controller层
controller层不做限定，一般情况符合REST风格即可，需要注意的是业务是否需要传递分库分表标示;
* Service层
service层方法名需要按照一定的规则书写，因为涉及到对Spring声明式事物的处理，一般情况先尽量使用动名词+类名，如:createUser、
editUser、modifyUser、removeUser、executeUser、addUser等
* Mapper层
Mapper层尽量使用sql语法关键字或者能标示实际意义的词作为前缀，如:insertUser、updateUser、deleteUser、selectUser、queryUser、findUser、getUserByPrimaryPk等

目前没有使用mysql通用分页拦截器，原因是在大批量数据下 order by 会有一定的性能影响，所以目前分页时手动写查询方法.

    ...
    
    List<XXX> xxxLists = queryXXXPages()
    Integer totalSize  = queryXXXPageCount()
    
    PageResponse<List<xxx>>  response = new PageResponse();
    response.setRows(xxxLists);
    response.setTotal(totalSize);
    return response;
    
    ...

由于前端框架数据结构需要total与rows属性，所以封装分页PageResponse，需要将查询的结果集放入PageResponse
{"total" : "","rows":[{""}]}

`注意：分页查询方法请尽量加Page后缀，方便其他人读取` 




#### 统一异常处理
系统使用ExceptionInterceptor异常处理拦截器对系统异常统一处理，一般来讲方法大部分业务只管把异常往上抛，不需要try...catch，特殊业务做特殊处理.



#### 资源文件读取
系统提供统一工具类SystemConfigUtil类 对系统配置文件进行读取
环境资源文件（wms_xxx_config.properties ） --> maven构建 -->  系统资源文件（wmsConfig.properties） 
 


#### 代码检测与规范
系统使用maven-pmd-plugin插件在maven构建时对代码进行统一检查处理，pmd规范可以查询wmsConfig模块的pmd.xml文件


	* 潜在的bug：空的try/catch/finally/switch语句
	* 未使用的代码：未使用的局部变量、参数、私有方法等
	* 可选的代码：String/StringBuffer的滥用
	* 复杂的表达式：不必须的if语句、可以使用while循环完成的for循环
	* 重复的代码：拷贝/粘贴代码意味着拷贝/粘贴bugs
	* 循环体创建新对象：尽量不要再for或while循环体内实例化一个新对象
	* 资源关闭：Connect，Result，Statement等使用之后确保关闭掉

   ...



#### 分库分表
系统使用前置通知CoreBeforeAdvice类和分表拦截器SplitTableInterceptor类对数据库进行进行分库分表处理.
CoreBeforeAdvice前置通知主要做动态数据源切换，及根据传递过来的DbShardVO对象定位到具体的业务数据库；SplitTableInterceptor用来做分表处理，根据传递过来的分表标示"splitTableKey"来判断业务是否需要分表，然后根据分表字段zoneId或者warehouseId取模定位到具体的表的下标数字范围0~256 如前缀+下标 t_smart_sku_1、t_smart_sku_2 ...t_smart_sku_256 具体可以根据业务设定.

 
 
#### 全局唯一Id生成
一般来讲业务表数据主键需要一套统一的生成全局唯一Id的机制，尤其是在高并发分布式系统中.
1）数据库自增Id
使用数据库自增Id虽然能在一定条件下满足，但在大表做水平分割时就显得不足了，会出现重复;其次在对表进行高并发单记录插入时需要加入事件机制，否则也会出现Id重复的问题;还有有些业务需要使用主从表，在写入数据时需要获取max(id)用于标示父子关系，若存在高并发获取max(id)的情况，会出线线程共用的现象.
2）使用Sequence
 mysql没有提供Sequence
3）使用GUID
GUID通常表示成32个16进制数字（0－9，A－F）组成的字符串，如：{56EC24021-6ABA-1069-A2DD-07902B30318F}，它实质上是一个128位长的二进制整数.生成的Id不够友好；占据了32位；索引效率较低. (GUID制定的算法中使用到用户的网卡MAC地址，以保证在计算机集群中生成唯一GUID)

所以目前提供一套统一的业务主键生成服务autoIdServer

    AutoIdClient
    ...
    getAutoId(int key)
    getAutoId(int key,int count)
    getAutoId(int key,int count,int targetTableIndex) 
    ... 

`注意：业务表如果需要使用autoIdServer服务生成唯一Id,需要先将key定义到AutoIdConstans类里，然后调用上面方法获取` 



#### 消息队列
系统现在使用阿里开源RocketMQ框架做消息处理，使用时先将wmsMQClient模块引入所需模块，然后编写再根据需要编写各自的producer和consumer.先定义Listener然后在register到MQConsumerMain启动服务即可.
`注意:使用时GroupName、Topic 和 tag 定义到MqConstans类里` 


--- 
### 4.应用部署和运维规范

初步定了一下路径的规则，可自定义（约定优于配置）

* 参数化构建 mvn clean package -DskipTests -P develop|test|online

* 部署基础文件

        /usr/local/
                 |------jdk1.8
                 |------nginx
                 |------tomcat

        /home/mapp/{app-name}
                        |------logs            应用的日志文件
                        |------bin             应用的启动脚本
                        |-----.default         应用运行文件夹,下面包括tomcat配置和war解压后的ROOT
                        |------target          应用发布时候的文件夹
                                |-----{app-name}.war   应用发布时候打出来的war包


* 应用日志文件位置

        /home/mapp/${app-name}/logs
        
        
        
