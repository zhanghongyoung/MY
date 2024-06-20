# 框架说明

### 功能说明

#### 2023-03-14

完成若依多模块knife4j的接口显示。

#### 2022-11-01
优化：
* 白名单接口路径添加demo示例。用于外部非认证接口访问设置
* o2o-new-retail-api模块spring.factories配置优化为启动类中配置扫描。
#### 2022-10-26
优化：
* 优化框架common-datascope模块的dataScopeFilter方法，调整后更适用于我们的业务场景。参见docs文件夹
* 添加CSV格式的导出工具类


#### 2022-10-20
优化：
* o2o-new-retail-modules-gen模块调整Mapper.xml.vm模板，调整后生成的insert、update语句不再包含createTime、updateTime字段。
  （注：createTime、updateTime需在MySQL中设置更新）

#### 2022-10-11
新增功能：
* 框架模块名称优化，便于快速构建新项目。
* 新增业务系统升级版本说明功能。
* 新增单点登录(sso)功能配置，并配有截图使用说明。参见docs文件夹
* 添加rsa加解密工具类

### 优化：
* o2o-new-retail-modules-basic模块仅保留demo以及junit示例，正式开发需要将该模块删除，重新进行创建。
* 优化单点登录jwk字符串配置方式，改为nacos模式，便于进行更改处理，测试通过。

### 已完成：

### 待处理：
骨架生成的前端部分(o2o-new-retail-ui模块)缺少完整的文件信息，需要完善。
Excel导入功能优化为easyExcel模式
对于一些服务的封装与使用，mq等等

### 使用说明

使用该项目骨架生成所需项目之后，参考若依官网进行项目安装部署即可。

### 注意

* sql文件夹的日期文件夹的SQL为版本更新产生的SQL，必须同步执行，方可使项目正常启动。
* 如果common模块的主包路径进行了路径更改，则需要在PreAuthorizeAspect.java中检查POINTCUT_SIGN字符串变量是否同时进行了变更。