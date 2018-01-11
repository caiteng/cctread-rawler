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
public class PaginationPlugin2 extends PluginAdapter {


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

    /**
     * 生成实体中每个属性
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


        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        System.out.println(document.getRootElement());

        XmlElement parentElement = document.getRootElement();
        List<Attribute> attributes = parentElement.getAttributes();
        //重新定义新RootElement,清空原先的方法
        parentElement = new XmlElement(parentElement.getName());
        for (Attribute attribute : attributes) {
            //将原先的Attribute添加回parentElement
            parentElement.addAttribute(attribute);
        }
        //用新的RootElement替换原先的RootElement
        document.setRootElement(parentElement);

        //数据库字段
        for (IntrospectedColumn introspectedColumn : columns) {
            System.out.println(introspectedColumn.toString());
        }
        //逗号分隔的字段集
        StringBuilder baseColumnList = new StringBuilder();
        //判断set代码块
        XmlElement set = new XmlElement("set");
        StringBuilder setStringBuilder = new StringBuilder();
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            if (baseColumnList.length() > 0) {
                baseColumnList.append(",");
            }
            baseColumnList.append(column.getActualColumnName());
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            setStringBuilder.setLength(0);
            setStringBuilder.append(column.getActualColumnName());
            setStringBuilder.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", setStringBuilder.toString())); //$NON-NLS-1$
            isNotNullElement.addElement(new TextElement(column.getActualColumnName() + " = #{" + column.getActualColumnName() + "},"));
            set.addElement(isNotNullElement);
        }
        //判断where代码块
        XmlElement where = new XmlElement("where");
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            System.out.println(introspectedColumn.toString());
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            where.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        //截取类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();


        //添加基本get方法
        addMapperMethodGet(parentElement, introspectedTable, baseColumnList.toString());
        //添加基本update方法
        addMapperMethodUpdate(parentElement, introspectedTable, set);


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
        XmlElement get = new XmlElement("select");
        get.addAttribute(new Attribute("id", "get"));
        get.addAttribute(new Attribute("resultMap", className));
        get.addAttribute(new Attribute("parameterType", "Long"));
        get.addElement(new TextElement(" select " + baseColumnList + " from " + tableName + " where id=#{id}"));
        parentElement.addElement(get);
    }

    /**
     * 添加mapper文件getAll方法
     */
    private void addMapperMethodGetAll(XmlElement parentElement, IntrospectedTable introspectedTable, String baseColumnList) {
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        XmlElement get = new XmlElement("select");
        get.addAttribute(new Attribute("id", "getAll"));
        get.addAttribute(new Attribute("resultMap", className));
        get.addElement(new TextElement(" select " + baseColumnList + " from " + tableName + " where del_flag = 0"));
        parentElement.addElement(get);
    }

    /**
     * 添加mapper文件create方法
     */
    private void addMapperMethodCreate(XmlElement parentElement, IntrospectedTable introspectedTable, String baseColumnList) {
//        	<insert id="create" parameterType="ArtCategory" useGeneratedKeys="true" keyProperty="categ_id">
//                insert into pm_art_category ( categ_id,parent_id,name,image,description,sort,del_flag,CREATED_BY,CREATED_DATE,supp_id)
//        values ( #{categ_id},#{parent_id},#{name},#{image},#{description},#{sort},#{del_flag},#{createdBy},#{createdDate},#{supp_id})
//	</insert>
        //类名
        String className = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // 数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        XmlElement get = new XmlElement("insert");
        get.addAttribute(new Attribute("id", "create"));
        get.addAttribute(new Attribute("parameterType", className));
        get.addAttribute(new Attribute("useGeneratedKeys", "true"));
        get.addAttribute(new Attribute("keyProperty", "true"));
        get.addElement(new TextElement(" select " + baseColumnList + " from " + tableName + " where del_flag = 0"));
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
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", "update"));
        update.addAttribute(new Attribute("parameterType", className));
        update.addElement(new TextElement("update " + tableName));
        update.addElement(set);
        update.addElement(new TextElement("where id =#{id}"));
        parentElement.addElement(update);

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