import com.google.common.base.CaseFormat;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.springbootredis.util.ProjectConstant.BASE_PACKAGE;
import static com.springbootredis.util.ProjectConstant.SERVICE_PACKAGE;

/**
 * @Author: laizc
 * @Date: Created in 13:58 2020-03-03
 * @desc: 代码生成器,根据数据名生成对应Model、Mapper、Service、Controller
 */
public class CodeGenerator {

	private static final String PROJECT_PATH = System.getProperty("user.dir");

	private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resource/generator/template";

	private static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	private static final String AUTHOR = "Jeremy";

	private static final String JAVA_PATH = "/src/main/java";

	private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		//genCode("table_info");
	}

	public static void genCode(String... tableNames){
		for(String tableName : tableNames){
			genCodeByCustomModeName(tableName);
		}
	}

	public static void genCodeByCustomModeName(String tableName){
		genModelAndMapper(tableName);
		genService(tableName);
		genController(tableName);
	}

	public static void genModelAndMapper(String tableName){

	}

	public static void genService(String tableName){
		try {
			Configuration cfg = getConfiguration();
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

	private static Configuration getConfiguration() throws IOException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
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
