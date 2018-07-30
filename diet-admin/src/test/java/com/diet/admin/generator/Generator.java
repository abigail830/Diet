package com.diet.admin.generator;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiuYu
 * @date 2018/4/27
 */
public class Generator {
    // JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/diet";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static final String PROJECT_PATH = Generator.class.getClass().getResource("/").getPath().split("/target")[0] + "/";
    // 模板位置
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "src/test/java/resources/template/generator";
    private static final String JAVA_PATH = PROJECT_PATH + "src/main/java"; // java文件路径
    private static final String RESOURCES_PATH = PROJECT_PATH + "src/main/resources";// 资源文件路径
    // 生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(Constant.SERVICE_PACKAGE);
    // 生成的Service实现存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(Constant.SERVICE_IMPL_PACKAGE);
    // 生成的Controller存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(Constant.CONTROLLER_PACKAGE);

    // @author
    private static final String AUTHOR = "LiuYu";
    // @date
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    /**
     * genCode("输入表名");
     *
     * @param args
     */
    public static void main(String[] args) {
        genCode("tb_wx_user_info_ext");
//        genCode("tb_cookbook");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成
     * TUserDetail、TUserDetailMapper、TUserDetailService ...
     *
     * @param tableNames 数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCode(tableName);
        }
    }

    /**
     * 通过数据表名称生成代码 如输入表名称 "user_info"
     * 将生成 UserInfo、UserInfoMapper、UserInfoService ...
     *
     * @param tableName 数据表名称
     */
    public static void genCode(String tableName) {
        genModelAndMapper(tableName);
//        genService(tableName);
//        genController(tableName);
    }

    public static void genModelAndMapper(String tableName) {
        Context context = getContext();

        JDBCConnectionConfiguration jdbcConnectionConfiguration = getJDBCConnectionConfiguration(context);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = getPluginConfiguration();
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = getJavaModelGeneratorConfiguration(context);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = getSqlMapGeneratorConfiguration(context);
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = getJavaClientGeneratorConfiguration(context);
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = context.getTableConfigurations().isEmpty() ?
                new TableConfiguration(context) : context.getTableConfigurations().get(0);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.addProperty(PropertyRegistry.TABLE_USE_ACTUAL_COLUMN_NAMES, "true");
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "MySql", true, ""));
        tableConfiguration.setDomainObjectName(null);
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        String modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    public static void genService(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            //模板所需要的参数
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", Constant.BASE_PACKAGE);
            data.put("basePackageService", Constant.SERVICE_PACKAGE);
            data.put("basePackageServiceImpl", Constant.SERVICE_IMPL_PACKAGE);
            data.put("basePackageModel", Constant.MODEL_PACKAGE);
            data.put("basePackageDao", Constant.MAPPER_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genController(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("baseRequestMapping", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", Constant.BASE_PACKAGE);
            data.put("basePackageController", Constant.CONTROLLER_PACKAGE);
            data.put("basePackageService", Constant.SERVICE_PACKAGE);
            data.put("basePackageModel", Constant.MODEL_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    private static Context getContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("id");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        return context;
    }

    private static JDBCConnectionConfiguration getJDBCConnectionConfiguration(Context context) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = context.getJdbcConnectionConfiguration();
        if(jdbcConnectionConfiguration == null){
            jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        }
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        return jdbcConnectionConfiguration;
    }

    private static PluginConfiguration getPluginConfiguration() {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", Constant.BASE_MAPPER);
        return pluginConfiguration;
    }

    private static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration(Context context) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        if(javaModelGeneratorConfiguration == null){
            javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        }
        javaModelGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(Constant.MODEL_PACKAGE);
        javaModelGeneratorConfiguration.addProperty("rootClass", Constant.BASE_MODEL);
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        return javaModelGeneratorConfiguration;
    }

    private static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration(Context context) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = context.getSqlMapGeneratorConfiguration();
        if (sqlMapGeneratorConfiguration == null) {
            sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        }
        sqlMapGeneratorConfiguration.setTargetProject(RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage(Constant.MAPPER_XML);
        return sqlMapGeneratorConfiguration;
    }

    private static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration(Context context) {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = context.getJavaClientGeneratorConfiguration();
        if(javaClientGeneratorConfiguration == null){
            javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        }
        javaClientGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(Constant.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        return javaClientGeneratorConfiguration;
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        File file = new File(TEMPLATE_FILE_PATH);
        cfg.setDirectoryForTemplateLoading(file);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
