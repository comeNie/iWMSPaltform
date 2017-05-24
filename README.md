# WMSPlatform

# 参考规范


### maven依赖管理

应用的依赖继承最外层parent，maven管理规则如下：

* 所有依赖的版本号都要从主pom的dependencyManagement中指定，原则上不允许在子模块中直接指定依赖版本号。基础依赖比如Spring、dubbo等中间件的版本号提取成property；

* 需要新添加依赖的时候，先从主pom和当前pom中进行搜索，以免重复添加；添加到主pom的dependencyManagement中，在子pom的dependencies进行依赖；

* 建议：修改现有依赖的版本号时，比较修改前后lib文件夹的依赖包数量和版本号，尽量避免运行时的包冲突。

* 环境参数 JDK 8 , Dubbo 2.5.3  ,RocketMQ 3.2.6




        
        
