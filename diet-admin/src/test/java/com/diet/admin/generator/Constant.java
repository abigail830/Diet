package com.diet.admin.generator;

/**
 * @author LiuYu
 * @date 2018/4/27
 */
public class Constant {
    // 项目基础包名称
    public static final String BASE_PACKAGE = "com.diet.admin";

    // Model所在包
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".entity";

    // Model父类
    public static final String BASE_MODEL = BASE_PACKAGE + ".core.BaseEntity";

    // Mapper所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";

    // Mapper插件基础接口
    public static final String BASE_MAPPER = BASE_PACKAGE + ".core.BaseMapper";

    // Mapper XML路径
    public static final String MAPPER_XML = BASE_PACKAGE + ".mapper";

    // Service所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";

    // ServiceImpl所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";

    // Controller所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";
}
