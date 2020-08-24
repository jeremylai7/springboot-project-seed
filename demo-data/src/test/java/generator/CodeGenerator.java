package generator;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import generator.plugin.RenameSqlMapperPlugin;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static generator.ProjectConstant.*;

/**
 * @Author: laizc
 * @Date: Created in 13:58 2020-03-03
 * @desc: 代码生成器,根据数据名生成对应Model、Mapper、Service、Controller
 */
@Slf4j
public class CodeGenerator {

	//JDBC配置
	private static final String JDBC_URL = "jdbc:mysql://47.98.202.133:3306/blog?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&serverTimezone=UTC";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "1234561";
	private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	private static final String PROJECT_PATH = System.getProperty("user.dir");

	private static final String JAVA_PATH = "/src/main/java"; //java文件路径

	private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

	private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/demo-data/src/test/resources/template";


	private static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	private static final String AUTHOR = "Jeremy";

	private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);

	private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);


	public static void main(String[] args) {
		//可以使用多个数据表配置，或者单个数据表配置genCodeByCustomModeName
		genCodeByCustomModeName("t_aa",null,null);
	}

	public static void genCodeByCustomModeName(String tableName,String modelName,String packageName){
		if (modelName == null){
			modelName = tableNameConvertModel(tableName);
		}
		if (packageName == null){
			packageName = modelName.toLowerCase();
		}
		genModelAndMapper(tableName,modelName,packageName);
		genService(modelName,packageName);
		genController(modelName,packageName);
	}

	public static void genModelAndMapper(String tableName,String modelName,String packageName){
		String projectName = "/demo-data";
		Context context = new Context(ModelType.FLAT);
		context.setId("Jeremy");
		//不生成example相关内容
		context.setTargetRuntime("MyBatis3Simple");

		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
		jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
		jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
		jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		//dao继承通用mapper
		PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
		pluginConfiguration.addProperty("mappers",MAPPER_INTERFACE_REFERENCE);
		context.addPluginConfiguration(pluginConfiguration);

		//todo dao层重命名 暂时未解决
		RenameSqlMapperPlugin renameSqlMapperPlugin = new RenameSqlMapperPlugin();
		Properties properties = new Properties();
		properties.setProperty("searchString","Mapper");
		properties.setProperty("replaceString","Dao");
		renameSqlMapperPlugin.setProperties(properties);
		//context.addPluginConfiguration(renameSqlMapperPlugin);


		//todo 不生成注释
		CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
		commentGeneratorConfiguration.addProperty("suppressDate","true");
		commentGeneratorConfiguration.addProperty("suppressAllComments","true");
		context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

		String targetProject = PROJECT_PATH + projectName + JAVA_PATH;
		//model
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetProject(targetProject);
		javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE.replace("${modelName}",packageName));
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		String resourceProject = PROJECT_PATH + projectName + RESOURCES_PATH;
		//mapper
		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetProject(resourceProject);
		sqlMapGeneratorConfiguration.setTargetPackage("mapper");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		//dao
		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setTargetProject(targetProject);
		javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE.replace("${modelName}",packageName));
		javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName(tableName);
		if (modelName == null){
			modelName = tableNameConvertModel(tableName);
		}
		tableConfiguration.setDomainObjectName(modelName);

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
			throw new RuntimeException("生成Model和Mapper失败:"+warnings);
		}
		log.info(modelName + ".java 生成成功");
		log.info(modelName + "Mapper.java 生成成功");
		log.info(modelName + "Mapper.xml 生成成功");
	}

	public static void genService(String modelName,String packageName){
		String projectName = "demo-service";
		try {
			freemarker.template.Configuration cfg = getConfiguration();
			Map<String,Object> map = new HashMap<>();
			map.put("date",DATE);
			map.put("author",AUTHOR);
			map.put("basePackage",BASE_PACKAGE);
			map.put("modelNameUpperCamel",modelName);
			map.put("modelNameLowerCamel",tableNameConvertLowerCamel(modelName));
			map.put("packageName",packageName);
			String packagePathService = PACKAGE_PATH_SERVICE.replace("${modelName}",packageName);
			File file = new File(PROJECT_PATH + "/" + projectName + JAVA_PATH + packagePathService + modelName + "Service.java");
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(map,new FileWriter(file));
			System.out.println(modelName +"Service文件生成成功");

			File fileImpl = new File(PROJECT_PATH + "/" + projectName + JAVA_PATH + packagePathService + modelName + "ServiceImpl.java");
			if (!fileImpl.getParentFile().exists()){
				fileImpl.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(map,new FileWriter(fileImpl));
			log.info(modelName + "ServiceImpl文件生成成功");
		} catch (Exception e) {
			System.out.println("Service生成失败");
			e.printStackTrace();
		}
	}

	public static void genController(String modelName,String packageName){
		String projectName = "demo-admin";
		try {
			freemarker.template.Configuration cfg = getConfiguration();
			Map<String, Object> data = new HashMap<>();
			data.put("date", DATE);
			data.put("author", AUTHOR);
			data.put("baseRequestMapping", modelNameConvertMappingPath(modelName));
			data.put("modelNameUpperCamel", modelName);
			data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName));
			data.put("basePackage", BASE_PACKAGE);
			data.put("packageName",packageName);

			File file = new File(PROJECT_PATH + "/" + projectName + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelName + "Controller.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			//cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
			cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

			System.out.println(modelName + "Controller.java 生成成功");
		} catch (Exception e) {
			throw new RuntimeException("生成Controller失败", e);
		}

	}

	private static freemarker.template.Configuration getConfiguration() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private static String tableNameConvertModel(String tableName){
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableName.replace("t","").toLowerCase());
	}

	private static String tableNameConvertLowerCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
	}

	private static String tableNameConvertMappingPath(String tableName) {
		tableName = tableName.toLowerCase();//兼容使用大写的表名
		return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
	}

	private static String modelNameConvertMappingPath(String modelName) {
		String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
		return tableNameConvertMappingPath(tableName);
	}

	private static String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}

}
