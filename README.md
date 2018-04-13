# graphServer

resources下的applicationContext-graph.xml原理与解析：

 原理：
- 通过查询语句得到查询结果。
- 然后通过配置将查询结果封装成想要的数据结构交给图计算引擎。
- 从图计算引擎中得到json结果返回给前台。

 配置：

    <bean class="com.hisign.common.resource.GraphRelation">
        <!--将会在edges数组下的元素中存在的参数-->
        <property name="relationProperties">
            <props>
                <prop key="name">涉及案件</prop>
                <prop key="tableName">T_AJGROUP</prop>
                <prop key="code">group2aj</prop>
            </props>
        </property>
        <!--关系类型假如重复则后台会合并统计两种关系-->
        <property name="relationLabel" value="group2aj"/>
        <!--从out到in，发出节点-->
        <!--发出节点类型-->
        <property name="outLabel" ref="groupid"/>
        <!--发出节点id，数据库找不到则直接返回VALUE。-->
        <property name="outId" value="GROUPID"/>
        <!--将会在nodes数组下的元素中存在的参数-->
        <property name="outProperties">
            <props>
                <!--1，数据库找不到则直接返回VALUE。-->
                <prop key="name">GROUPNAME</prop>
            </props>
        </property>
        <!--从out到in，到达节点-->
        <!--到达节点类型-->
        <property name="inLabel" ref="ajid"/>
        <!--到达节点id，数据库找不到则直接返回VALUE。-->
        <property name="inId" value="AJID"/>
        <!--将会在nodes数组下的元素中存在的参数-->
        <property name="inProperties">
            <props>
                <!--1，数据库找不到则直接返回VALUE。-->
                <prop key="name">AJMC</prop>
            </props>
        </property>
        <!--关系语句-->
        <property name="relationSql">
            <value>select * from(select t.group_id as GROUPID,
                t.case_id as AJID,
                (select g.group_name from T_GROUP g where g.id = t.group_id) as GROUPNAME,
                (select AJMC from T_CASE a where a.id = t.case_id) as AJMC
                from T_CASE_GROUP t
                where 1 = 1) a where 1=1
            </value>
        </property>
    </bean>
