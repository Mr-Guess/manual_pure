给实体类加上注解@ChineseName("模块名")，属性名上加上对应的中文名注解@ChineseName("属性名")
实体类所在的最后一层的packaqe如果是(pojo或者model或者entity)则分层生成，否则统一生成到同一包下
配置文件路径存放于generate.properties文件中
JUnit运行Generate类中的generate方法来进行自动生成
生成完成后建议将实体类上的注解注释掉

另外的两个注解@MultiLineField和@PersistenceIgnore分别表示多行文本域和不进行持久化的属性