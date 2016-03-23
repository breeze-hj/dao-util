# dao-util
dao开发和单元测试工具

mybatis-generator生成的文件信息较多,TableBeanGenerator仅生成整洁的java bean和insert SQL。

使用JUnit4Spring时,准备测试数据比较麻烦。
JunitInsertGenerator从数据库提取一条insert SQL,Easy Dao Junit.html中可视化地批量生成、修改SQL,极大地方便单元测试。
