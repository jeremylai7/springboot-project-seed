import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.springbootredis.util.ProjectConstant.*;

/**
 * @Author: laizc
 * @Date: Created in 13:58 2020-03-03
 * @desc: 代码生成器,根据数据名生成对应Model、Mapper、Service、Controller
 */
@Slf4j
public class CodeGenerator {

	//JDBC配置，请修改为你项目的实际配置
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&serverTimezone=UTC";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "123456";
	private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	private static final String PROJECT_PATH = System.getProperty("user.dir");

	private static final String JAVA_PATH = "/src/main/java"; //java文件路径
	private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

	private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resource/generator/template";

	private static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	private static final String AUTHOR = "Jeremy";

	private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		genCode("t_user");
	}

	public static void genCode(String... tableNames){
		for(String tableName : tableNames){
			genCodeByCustomModeName(tableName,null);
		}
	}

	public static void genCodeByCustomModeName(String tableName,String modelName){
		genModelAndMapper(tableName,modelName);
		//genService(tableName);
		//genController(tableName);
	}

	public static void genModelAndMapper(String tableName,String modelName){
		Context context = new Context(ModelType.FLAT);
		context.setId("jeremy");
		context.setTargetRuntime("MyBatis3Simple");

		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
		jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
		jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
		jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		/*PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
		pluginConfiguration.addProperty("mappers",MAPPER_INTERFACE_REFERENCE);
		context.addPluginConfiguration(pluginConfiguration);*/

		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
		javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		/*SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
		sqlMapGeneratorConfiguration.setTargetPackage("mapper");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
		javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
		javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);*/

		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName(tableName);

		if (StringUtils.isBlank(modelName)){
			modelName = tableName.replace("t_","");
		}else {

		}
		tableConfiguration.setDomainObjectName(modelName);
		//tableConfiguration.setGeneratedKey(new GeneratedKey("id","Mysql",true,null));
		context.addTableConfiguration(tableConfiguration);
		MyBatisGenerator generator;
		List<String> warnings;
		try {
			Configuration configuration = new Configuration();
			configuration.addContext(context);
			configuration.validate();

			boolean overwrite = true;
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			warnings = new ArrayList<>();
			generator = new MyBatisGenerator(configuration,callback,warnings);
			generator.generate(null);
		} catch (Exception e) {
			throw new RuntimeException("生成Model和Mapper失败",e);
		}
		if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()){
			//throw new RuntimeException("生成Model和Mapper失败:"+warnings);
		}
		if (StringUtils.isEmpty(modelName)){
			//modelName = tableNameConvertUpperCamel(tableName);
		}
		log.info(modelName + ".java 生成成功");
		log.info(modelName + "Mapper.java 生成成功");
		log.info(modelName + "Mapper.xml 生成成功");


	}

	public static void genService(String tableName){
		try {
			freemarker.template.Configuration cfg = getConfiguration();
			Map<String,Object> map = new HashMap<>();
			map.put("date",DATE);
			map.put("author",AUTHOR);
			map.put("basePackage",BASE_PACKAGE);
			String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
			map.put("modelNameUpperCamel",modelNameUpperCamel);
			File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE +
					modelNameUpperCamel + "Service.java");
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(map,new FileWriter(file));
			System.out.println(modelNameUpperCamel +"Service文件生成成功");
		} catch (Exception e) {
			System.out.println("Service生成失败");
			e.printStackTrace();
		}
	}

	public static void genController(String tableName){

	}

	private static freemarker.template.Configuration getConfiguration() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private static String tableNameConvertUpperCamel(String tableName){
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableName.toLowerCase());
	}

	private static String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}

}
