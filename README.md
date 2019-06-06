# springboot-learn
spring boot 学习示例

## 参考文档

## spring boot 基础

### 使用idea新建spring boot 项目
- [ ] maven 配置多模块
> * 新建子模块,选中父项目>new module>spring Initializr >输入模块名> 选中 devtools,lombok,web 依赖> 右键启动main方法就可以了
> * 根据[Spring指南 Getting Started guides,](https://spring.io/guides) 编写hello world
> * application.properties  配置端口号,server.port=8888(**spring-boot-autoconfigure 包下查看模块对应的xxProperties类,可以找到配置项** )
> * 访问url: http://localhost:8888/greeting
> * webmvc mock 单元测试

     
# 专题     
## spring boot auth2