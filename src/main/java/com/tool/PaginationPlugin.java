package com.tool;


import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

/**
 * mybatis代码生成器
 *
 * @author cait
 */
public class PaginationPlugin extends PluginAdapter {
    /**
     * 生成dao
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("IParentDAO<" + introspectedTable.getBaseRecordType() + ", Integer>");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.cctread.dao.modules.IParentDAO");
        /**
         * 添加 extends BaseDao<Object>
         */
        interfaze.addSuperInterface(fqjt);
        /**
         *  添加import com.cctread.Dao.modules.IParentDao
         */
        interfaze.addImportedType(imp);
        interfaze.getMethods().clear();
        return true;
    }

    /**
     * 生成实体中每个属性
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return true;
    }

    /**
     * 生成实体
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加序列号
        //addSerialVersionUID(topLevelClass, introspectedTable);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 生成mapping
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

    /**
     * 生成mapping 添加自定义sql
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {


        System.out.println(introspectedTable.getNonBLOBColumnCount());

        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        XmlElement parentElement = document.getRootElement();
        List<Attribute> attributes = parentElement.getAttributes();
        //重新定义新RootElement,清空原先的方法
        parentElement = new XmlElement(parentElement.getName());
        for (Attribute attribute : attributes) {
            //将原先的Attribute添加回parentElement
            parentElement.addAttribute(attribute);
        }
        //用新的RootElement替换原先的RootElement,重新输出新的xml
        document.setRootElement(parentElement);
        //判断数据库中是否含有删除标记设计，判断用户生成删除方法时选择物理删除还是逻辑删除
        String delFlag = "";
        //逗号分隔的字段集
        StringBuilder baseColumnList = new StringBuilder();
        //逗号分隔的java字段集
        StringBuilder javaBaseColumnList = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : columns) {
            if (introspectedColumn.getActualColumnName().equals("del_flag") || introspectedColumn.getActualColumnName().equals("delflag")) {
                delFlag = introspectedColumn.getActualColumnName();
            }
            if (baseColumnList.length() > 0) {
                baseColumnList.append(",");
                javaBaseColumnList.append(",");
            }
            javaBaseColumnList.append("#{");
            javaBaseColumnList.append(introspectedColumn.getJavaProperty());
            javaBaseColumnList.append('}');
            baseColumnList.append(introspectedColumn.getActualColumnName());
            System.out.println(introspectedColumn.toString());
        }

        //set判断代码块
        XmlElement set = getSetXml(columns);
        //where判断代码块
        XmlElement where = getWhereXml(columns, delFlag);
        //截取类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        //获取java主键
        String JavaPrimaryKey = introspectedTable.getAllColumns().get(0).getJavaProperty();
        //添加基本get方法
        addMapperMethodGet(parentElement, baseColumnList.toString(), className, tableName, primaryKey, JavaPrimaryKey);
        //添加基本getAll方法
        addMapperMethodGetAll(parentElement, baseColumnList.toString(), delFlag, className, tableName);
        //添加基本create方法
        addMapperMethodCreate(parentElement, baseColumnList.toString(), className, tableName, primaryKey, javaBaseColumnList.toString());
        //添加基本update方法
        addMapperMethodUpdate(parentElement, className, tableName, primaryKey, JavaPrimaryKey, set);
        //添加基本delete方法
        addMapperMethodDelete(parentElement, tableName, primaryKey, JavaPrimaryKey, delFlag);
        //添加基本multiDelete方法
        addMapperMethodMultiDelete(parentElement, tableName, primaryKey, delFlag);
        //添加基本count方法
        addMapperMethodCount(parentElement, tableName);
        //添加基本getByWhere方法
        addMapperMethodFindByWhere(parentElement, where, baseColumnList.toString(), className, primaryKey);
        //添加基本getCountByWhere方法
        addMapperMethodGetCountByWhere(parentElement, where, className);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 添加mapper文件get方法
     *
     * @param parentElement
     * @param baseColumnList 字段集合
     * @param className      类名
     * @param tableName      数据库表名
     * @param primaryKey     数据库主键
     * @param JavaPrimaryKey java主键
     */
    private void addMapperMethodGet(XmlElement parentElement, String baseColumnList, String className, String tableName, String primaryKey, String JavaPrimaryKey) {
        XmlElement get = new XmlElement("select");
        get.addAttribute(new Attribute("id", "get"));
        get.addAttribute(new Attribute("resultMap", className));
        get.addAttribute(new Attribute("parameterType", "Long"));
        get.addElement(new TextElement(" select " + baseColumnList + " from " + tableName + " where " + primaryKey + "=#{" + JavaPrimaryKey + "}"));
        parentElement.addElement(get);
    }

    /**
     * 添加mapper文件getAll方法
     *
     * @param parentElement
     * @param baseColumnList 字段集合
     * @param className
     * @param tableName
     * @param delFlag        逻辑删除标识
     */
    private void addMapperMethodGetAll(XmlElement parentElement, String baseColumnList, String className, String tableName, String delFlag) {
        XmlElement getAll = new XmlElement("select");
        getAll.addAttribute(new Attribute("id", "getAll"));
        getAll.addAttribute(new Attribute("resultMap", className));
        String sql = " select " + baseColumnList + " from " + tableName;
        if (delFlag.length() > 0) {
            sql += delFlag + " = 0 ";
        }
        getAll.addElement(new TextElement(sql));
        parentElement.addElement(getAll);
    }

    /**
     * 添加mapper文件delete方法
     */
    private void addMapperMethodDelete(XmlElement parentElement, String tableName, String primaryKey, String JavaPrimaryKey, String delFlag) {
        XmlElement delete = null;
        String sql = "";
        if (delFlag.length() > 0) {
            delete = new XmlElement("update");
            sql = " update " + tableName + " set " + delFlag + " = 1 where " + primaryKey + " =#{" + JavaPrimaryKey + "}";
        } else {
            delete = new XmlElement("delete");
            sql = " delete from " + tableName + "  where " + primaryKey + " =#{" + JavaPrimaryKey + "}";
        }
        delete.addAttribute(new Attribute("id", "delete"));
        delete.addAttribute(new Attribute("parameterType", "long"));
        delete.addElement(new TextElement(sql));
        parentElement.addElement(delete);
    }

    /**
     * 添加mapper文件multiDelete方法
     */
    private void addMapperMethodMultiDelete(XmlElement parentElement, String tableName, String primaryKey, String delFlag) {
        XmlElement multiDelete = null;
        String sql = "";
        if (delFlag.length() > 0) {
            multiDelete = new XmlElement("update");
            sql = " update " + tableName + " set " + delFlag + " = 1 where " + primaryKey + " in (${ids})";
        } else {
            multiDelete = new XmlElement("delete");
            sql = " delete from " + tableName + "  where " + primaryKey + " in (${ids})";

        }
        multiDelete.addAttribute(new Attribute("id", "multiDelete"));
        multiDelete.addAttribute(new Attribute("parameterType", "Map"));
        multiDelete.addElement(new TextElement(sql));
        parentElement.addElement(multiDelete);
    }

    /**
     * 添加mapper文件count方法
     */
    private void addMapperMethodCount(XmlElement parentElement, String tableName) {
        XmlElement count = new XmlElement("select");
        count.addAttribute(new Attribute("id", "count"));
        count.addAttribute(new Attribute("resultType", "Map"));
        count.addElement(new TextElement(" select count(*) from" + tableName));
        parentElement.addElement(count);
    }

    /**
     * 添加mapper文件create方法
     */
    private void addMapperMethodCreate(XmlElement parentElement, String baseColumnList, String className, String tableName, String primaryKey, String javaBaseColumnList) {
        XmlElement create = new XmlElement("insert");
        create.addAttribute(new Attribute("id", "create"));
        create.addAttribute(new Attribute("parameterType", className));
        create.addAttribute(new Attribute("useGeneratedKeys", "true"));
        create.addAttribute(new Attribute("keyProperty", primaryKey));
        create.addElement(new TextElement("insert into " + tableName + " ( " + baseColumnList + " ) values (" + javaBaseColumnList + ")"));
        parentElement.addElement(create);
    }


    /**
     * 添加mapper文件update方法
     */
    private void addMapperMethodUpdate(XmlElement parentElement, String className, String tableName, String primaryKey, String JavaPrimaryKey, XmlElement set) {
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", "update"));
        update.addAttribute(new Attribute("parameterType", className));
        update.addElement(new TextElement("update " + tableName));
        update.addElement(set);
        update.addElement(new TextElement("where " + primaryKey + " =#{" + JavaPrimaryKey + "}"));
        parentElement.addElement(update);

    }

    /**
     * findByWhere
     */
    private void addMapperMethodFindByWhere(XmlElement parentElement, XmlElement where, String baseColumnList, String className, String primaryKey) {
        XmlElement findByWhere = new XmlElement("select");
        findByWhere.addAttribute(new Attribute("id", "findByWhere"));
        findByWhere.addAttribute(new Attribute("parameterType", "Map"));
        findByWhere.addAttribute(new Attribute("resultType", className));
        findByWhere.addElement(new TextElement("select " + baseColumnList + " from " + className));
        findByWhere.addElement(where);
        findByWhere.addElement(new TextElement("ORDER BY " + primaryKey + " desc"));
        findByWhere.addElement(new TextElement(" LIMIT #{lowerLimit},#{upperLimit}"));
        parentElement.addElement(findByWhere);

    }

    /**
     * GetCountByWhere
     */
    private void addMapperMethodGetCountByWhere(XmlElement parentElement, XmlElement where, String className) {
        XmlElement getCountByWhere = new XmlElement("select");
        getCountByWhere.addAttribute(new Attribute("id", "getCountByWhere"));
        getCountByWhere.addAttribute(new Attribute("parameterType", "Map"));
        getCountByWhere.addAttribute(new Attribute("resultType", "long"));
        getCountByWhere.addElement(new TextElement("select count(*)  from " + className));
        getCountByWhere.addElement(where);
        parentElement.addElement(getCountByWhere);

    }

    /**
     * 获取where代码块
     *
     * @return
     */
    public XmlElement getWhereXml(List<IntrospectedColumn> columns, String delFlag) {
        XmlElement where = new XmlElement("where");
        if (delFlag.length() > 0) {
            where.addElement(new TextElement(delFlag + " = 0"));
        }
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : columns) {
            XmlElement isNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            where.addElement(isNotNullElement);
            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = #{");
            //获取java属性name
            sb.append(introspectedColumn.getJavaProperty(null));
            sb.append("}");
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        return where;
    }

    /**
     * 获取set代码块
     *
     * @return
     */
    public XmlElement getSetXml(List<IntrospectedColumn> columns) {
        XmlElement set = new XmlElement("set");
        StringBuilder setStr = new StringBuilder();
        for (IntrospectedColumn column : columns) {
            XmlElement isNotNullElement = new XmlElement("if");
            setStr.setLength(0);
            setStr.append(column.getActualColumnName());
            setStr.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", setStr.toString()));
            isNotNullElement.addElement(new TextElement(column.getActualColumnName() + " = #{" + column.getJavaProperty(null) + "},"));
            set.addElement(isNotNullElement);
        }
        return set;
    }



    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    /**
     * mapping中添加方法
     */
    // @Override
    public boolean sqlMapDocumentGenerated2(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();// 数据库表名
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        // 添加sql
        XmlElement sql = new XmlElement("select");

        XmlElement parentElement = document.getRootElement();
        XmlElement deleteLogicByIdsElement = new XmlElement("update");
        deleteLogicByIdsElement.addAttribute(new Attribute("id", "deleteLogicByIds"));
        deleteLogicByIdsElement.addElement(new TextElement("update " + tableName + " set deleteFlag = #{deleteFlag,jdbcType=INTEGER} where id in "
                + " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach> "));

        parentElement.addElement(deleteLogicByIdsElement);
        XmlElement queryPage = new XmlElement("select");
        queryPage.addAttribute(new Attribute("id", "queryPage"));
        queryPage.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        queryPage.addElement(new TextElement("select "));

        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Base_Column_List"));

        queryPage.addElement(include);
        queryPage.addElement(new TextElement(" from " + tableName + " ${sql}"));
        parentElement.addElement(queryPage);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    //    <select id="getCountByWhere" parameterType="Map" resultType="int">
    //    select count(*)
    //    from cct_rawler_task
    //    <where>
    //            and del_flag = 0
    //            <if test="id!=null and id!=''">and id=#{id}</if>
    //            <if test="bookName!=null and bookName!=''">and book_name=#{bookName}</if>
    //            <if test="author!=null and author!=''">and author=#{author}</if>
    //            <if test="startChapter!=null and startChapter!=''">and start_chapter=#{startChapter}</if>
    //            <if test="endChapter!=null and endChapter!=''">and end_chapter=#{endChapter}</if>
    //            <if test="status!=null and status!=''">and status=#{status}</if>
    //            <if test="delFlag!=null and delFlag!=''">and del_flag=#{delFlag}</if>
    //            <if test="version!=null and version!=''">and version=#{version}</if>
    //            <if test="createDate!=null and createDate!=''">and create_date=#{createDate}</if>
    //            <if test="updateDate!=null and updateDate!=''">and update_date=#{updateDate}</if>
    //
    //        </where>
    //    </select>

    /**
     * 添加mapper文件方法GetCountByWhere
     *
     * @param document
     * @param introspectedTable
     * @return
     */
    public boolean addMapperMethodGetCountByWhere(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        XmlElement parentElement = document.getRootElement();
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "sql_where"));
        XmlElement where = new XmlElement("where");
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            where.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        sql.addElement(where);
        parentElement.addElement(sql);

        //parentElement.addElement(deleteLogicByIdsElement);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 添加序列号（新增其他属性同理）
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void addSerialVersionUID(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType("long"));
        field.setStatic(true);
        field.setFinal(true);
        field.setName("serialVersionUID");
        field.setInitializationString("1L");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
    }

    /*
     * Dao中添加方法
     */
    private Method generateDeleteLogicByIds(Method method, IntrospectedTable introspectedTable) {
        Method m = new Method("deleteLogicByIds");
        m.setVisibility(method.getVisibility());
        m.setReturnType(FullyQualifiedJavaType.getIntInstance());
        m.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "deleteFlag", "@Param(\"deleteFlag\")"));
        m.addParameter(new Parameter(new FullyQualifiedJavaType("Integer[]"), "ids", "@Param(\"ids\")"));
        context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
        return m;
    }

    /*
     * 实体中添加属性
     */
    private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getIntInstance());
        field.setName(name);
        field.setInitializationString("-1");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    @Override
    public boolean validate(List<String> arg0) {
        return true;
    }

    public static void generate() {
        String config = PaginationPlugin.class.getClassLoader().getResource("conf/xml/generator-config.xml").getFile();
        String[] arg = {"-configfile", config, "-overwrite"};
        ShellRunner.main(arg);
    }

    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        generate();
    }
}