package ${basePackageServiceImpl};

import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageService}.${modelNameUpperCamel}Service;
import ${basePackage}.core.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ${modelNameUpperCamel}Service接口实现类
* @author ${author}
* @date ${date}
*/
@Service
public class ${modelNameUpperCamel}ServiceImpl extends BaseServiceImpl<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
}