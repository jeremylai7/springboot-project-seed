package generator;

/**
 * @Author: laizc
 * @Date: Created in 14:54 2020-03-03
 * @desc: 项目常量
 */
public class ProjectConstant {
	//生成代码所在的基础包名称，
	public static final String BASE_PACKAGE = "com.jeremy";

	//生成的Model所在包
	public static final String MODEL_PACKAGE = BASE_PACKAGE + ".data.${modelName}.model";

	//生成的Mapper所在包
	public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".data.${modelName}.dao";

	//生成的Service所在包
	public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service.${modelName}";


	//生成的Controller所在包
	public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".admin.controller";

	//Mapper插件基础接口的完全限定名
	public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".data.utils.MyMapper";

}
