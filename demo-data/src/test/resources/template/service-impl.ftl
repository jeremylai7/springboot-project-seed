package ${basePackage}.service.${packageName};

import ${basePackage}.data.${packageName}.model.${modelNameUpperCamel};
import ${basePackage}.data.${packageName}.dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Auther: ${author}
 * @Date: ${date}
 */
@Service
public class ${modelNameUpperCamel}ServiceImpl extends BaseServiceImpl<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service{

       @Resource
       private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
