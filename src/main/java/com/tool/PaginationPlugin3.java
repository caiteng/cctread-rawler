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
public class PaginationPlugin3 extends PluginAdapter {


    /**
     * 生成实体中每个属性的get方法
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        System.out.println("modelGetterMethodGenerated");
        //因为id在父类为Long类型无法识别从中过滤掉
        if (introspectedColumn.getActualColumnName().equals("id")) {
            return false;
        }
//        else if (introspectedColumn.getActualColumnName().equals("createdby")) {
//            introspectedColumn.setJavaProperty("createdBy");
//            return false;
//        }

//        private String createdby;
//
//        private Date createddate;
//
//        private String updatedby;
//
//        private Date updateddate;
//
//        protected String createdBy = "";
//        protected Date createdDate = new Date();
//        protected String updatedBy = "";
//        protected Date updatedDate;


        return true;
    }

    /**
     * 生成实体中每个属性的set方法
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if (introspectedColumn.getActualColumnName().equals("id")) {
            return false;
        }
        return true;
    }

    /**
     * 生成实体
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        CommentGenerator commentGenerator = context.getCommentGenerator();
//        Field field = new Field();
//        field.setVisibility(JavaVisibility.PROTECTED);
//        field.setType(FullyQualifiedJavaType.getIntInstance());
//        field.setName(name);
//        field.setInitializationString("-1");
//        commentGenerator.addFieldComment(field, introspectedTable);
        List<Field> fields = topLevelClass.getFields();
        for (Field field : fields) {
            //System.out.println(field.getName());
        }
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
//    CommentGenerator commentGenerator = context.getCommentGenerator();
//    Field field = new Field();
//        field.setVisibility(JavaVisibility.PROTECTED);
//        field.setType(FullyQualifiedJavaType.getIntInstance());
//        field.setName(name);
//        field.setInitializationString("-1");
//        commentGenerator.addFieldComment(field, introspectedTable);
//        topLevelClass.addField(field);
//    char c = name.charAt(0);
//    String camel = Character.toUpperCase(c) + name.substring(1);
//    Method method = new Method();
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setName("set" + camel);
//        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
//        method.addBodyLine("this." + name + "=" + name + ";");
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(method);
//    method = new Method();
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
//        method.setName("get" + camel);
//        method.addBodyLine("return " + name + ";");
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(method);

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

        System.out.println("----------------------------------------------------");
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        XmlElement parentElement = document.getRootElement();
        List<Attribute> attributes = parentElement.getAttributes();
        //重新定义新RootElement,清空原先的方法
        parentElement = new XmlElement(parentElement.getName());
        for (Attribute attribute : attributes) {
            //将原先的Attribute添加回parentElement
            parentElement.addAttribute(attribute);
        }
        //用新的RootElement替换原先的RootElement，下面就是新的mapper部分
        document.setRootElement(parentElement);
        //判断数据库中是否含有删除标记设计，用户生成删除方法时选择物理删除还是逻辑删除
        String delFlag = "";
        //逗号分隔的字段集
        StringBuilder baseColumnList = new StringBuilder();
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

        //判断set代码块
        XmlElement set = getSetXml(columns);
        //获取where代码块
        XmlElement where = getWhereXml(columns, delFlag);

        //截取类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();


        //添加基本get方法
        addMapperMethodGet(parentElement, introspectedTable, baseColumnList.toString());
        //添加基本getAll方法
        addMapperMethodGetAll(parentElement, introspectedTable, baseColumnList.toString(), delFlag);
        //添加基本create方法
        addMapperMethodCreate(parentElement, introspectedTable, baseColumnList.toString(), javaBaseColumnList.toString());
        //添加基本update方法
        addMapperMethodUpdate(parentElement, introspectedTable, set);
        //添加基本delete方法
        addMapperMethodDelete(parentElement, introspectedTable, delFlag);
        //添加基本multiDelete方法
        addMapperMethodMultiDelete(parentElement, introspectedTable, delFlag);
        //添加基本multiDelete方法
        addMapperMethodCount(parentElement, introspectedTable);

        //添加基本getByWhere方法
        addMapperMethodFindByWhere(parentElement, introspectedTable, where, baseColumnList.toString());
        //添加基本getCountByWhere方法
        addMapperMethodGetCountByWhere(parentElement, introspectedTable, where);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 添加mapper文件get方法
     */
    private void addMapperMethodGet(XmlElement parentElement, IntrospectedTable introspectedTable, String baseColumnList) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        //获取java第一个字段作为主键
        String JavaPrimaryKey = introspectedTable.getAllColumns().get(0).getJavaProperty();
        XmlElement get = new XmlElement("select");
        get.addAttribute(new Attribute("id", "get"));
        get.addAttribute(new Attribute("resultMap", className));
        get.addAttribute(new Attribute("parameterType", "Long"));
        get.addElement(new TextElement(" select " + baseColumnList + " from " + tableName + " where " + primaryKey + "=#{" + JavaPrimaryKey + "}"));
        parentElement.addElement(get);
    }

    /**
     * 添加mapper文件getAll方法
     */
    private void addMapperMethodGetAll(XmlElement parentElement, IntrospectedTable introspectedTable, String baseColumnList, String delFlag) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        XmlElement get = new XmlElement("select");
        get.addAttribute(new Attribute("id", "getAll"));
        get.addAttribute(new Attribute("resultMap", className));

        String sql = " select " + baseColumnList + " from " + tableName;
        if (delFlag.length() > 0) {
            sql += delFlag + " = 0 ";
        }
        get.addElement(new TextElement(sql));
        parentElement.addElement(get);
    }

    /**
     * 添加mapper文件delete方法
     */
    private void addMapperMethodDelete(XmlElement parentElement, IntrospectedTable introspectedTable, String delFlag) {
        XmlElement delete = null;
        String sql = "";
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        //获取java第一个字段作为主键
        String JavaPrimaryKey = introspectedTable.getAllColumns().get(0).getJavaProperty();
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
    private void addMapperMethodMultiDelete(XmlElement parentElement, IntrospectedTable introspectedTable, String delFlag) {
        XmlElement delete = null;
        String sql = "";
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        if (delFlag.length() > 0) {
            delete = new XmlElement("update");
            sql = " update " + tableName + " set " + delFlag + " = 1 where " + primaryKey + " in (${ids})";
        } else {
            delete = new XmlElement("delete");
            sql = " delete from " + tableName + "  where " + primaryKey + " in (${ids})";

        }
        delete.addAttribute(new Attribute("id", "multiDelete"));
        delete.addAttribute(new Attribute("parameterType", "Map"));
        delete.addElement(new TextElement(sql));
        parentElement.addElement(delete);
    }

    /**
     * 添加mapper文件count方法
     */
    private void addMapperMethodCount(XmlElement parentElement, IntrospectedTable introspectedTable) {
        XmlElement delete = new XmlElement("select");
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        delete.addAttribute(new Attribute("id", "count"));
        delete.addAttribute(new Attribute("resultType", "Map"));
        delete.addElement(new TextElement(" select count(*) from" + tableName));
        parentElement.addElement(delete);
    }

    /**
     * 添加mapper文件create方法
     */
    private void addMapperMethodCreate(XmlElement parentElement, IntrospectedTable introspectedTable, String baseColumnList, String javaBaseColumnList) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        XmlElement get = new XmlElement("insert");
        get.addAttribute(new Attribute("id", "create"));
        get.addAttribute(new Attribute("parameterType", className));
        get.addAttribute(new Attribute("useGeneratedKeys", "true"));
        get.addAttribute(new Attribute("keyProperty", primaryKey));
        get.addElement(new TextElement("insert into " + tableName + " ( " + baseColumnList + " ) values (" + javaBaseColumnList + ")"));
        parentElement.addElement(get);
    }


    /**
     * 添加mapper文件update方法
     */
    private void addMapperMethodUpdate(XmlElement parentElement, IntrospectedTable introspectedTable, XmlElement set) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        //获取java第一个字段作为主键
        String JavaPrimaryKey = introspectedTable.getAllColumns().get(0).getJavaProperty();
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
    private void addMapperMethodFindByWhere(XmlElement parentElement, IntrospectedTable introspectedTable, XmlElement where, String baseColumnList) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        XmlElement update = new XmlElement("select");
        update.addAttribute(new Attribute("id", "findByWhere"));
        update.addAttribute(new Attribute("parameterType", "Map"));
        update.addAttribute(new Attribute("resultType", className));
        update.addElement(new TextElement("select " + baseColumnList + " from " + className));
        update.addElement(where);
        update.addElement(new TextElement("ORDER BY " + primaryKey + " desc"));
        update.addElement(new TextElement(" LIMIT #{lowerLimit},#{upperLimit}"));
        parentElement.addElement(update);

    }

    /**
     * findByWhere
     */
    private void addMapperMethodGetCountByWhere(XmlElement parentElement, IntrospectedTable introspectedTable, XmlElement where) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //获取第一个字段作为主键
        String primaryKey = introspectedTable.getAllColumns().get(0).getActualColumnName();
        XmlElement update = new XmlElement("select");
        update.addAttribute(new Attribute("id", "getCountByWhere"));
        update.addAttribute(new Attribute("parameterType", "Map"));
        update.addAttribute(new Attribute("resultType", "long"));
        update.addElement(new TextElement("select count(*)  from " + className));
        update.addElement(where);
        parentElement.addElement(update);

    }

    /**
     * 获取where代码块
     *
     * @return
     */
    public XmlElement getWhereXml(List<IntrospectedColumn> columns, String delFlag) {
        //判断where代码块
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
        //判断where代码块
        XmlElement set = new XmlElement("set");
        StringBuilder setStringBuilder = new StringBuilder();
        for (IntrospectedColumn column : columns) {

            XmlElement isNotNullElement = new XmlElement("if");
            setStringBuilder.setLength(0);
            setStringBuilder.append(column.getActualColumnName());
            setStringBuilder.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", setStringBuilder.toString()));
            isNotNullElement.addElement(new TextElement(column.getActualColumnName() + " = #{" + column.getJavaProperty(null) + "},"));
            set.addElement(isNotNullElement);
        }
        return set;
    }


    /**
     * 生成dao
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        System.out.println("clientGenerated");
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("IParentDAO<" + introspectedTable.getBaseRecordType() + ", Long>");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.dmp.modules.dao.IParentDAO");
        /**
         * 添加 extends IParentDAO<Object>
         */
        interfaze.addSuperInterface(fqjt);
        /**
         *  添加import com.dmp.modules.dao.IParentDAO
         */
        interfaze.addImportedType(imp);
        interfaze.getMethods().clear();
        return true;
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
     * 实体中添加属性
     *
     * @param topLevelClass
     * @param introspectedTable
     * @param name
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
        String config = PaginationPlugin.class.getClassLoader().getResource("generator-config.xml").getFile();
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