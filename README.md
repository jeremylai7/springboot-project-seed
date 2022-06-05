# 简介
springboot-project-seed是基于springboot和mybatis的种子项目，用于项目快速构建
- 整合通用mapper
- 登陆拦截器
- 枚举转换（不使用通用mapper实现）
- 统一异常处理
- 通用mapper整合枚举  (配置文件添加 enum-as-simple-type: true 自动将枚举转成表中的字段)

# 入门

执行数据库：
```
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `top` tinyint(1) DEFAULT NULL COMMENT '是否超级管理员',
  `user_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `t_user` (`id`, `role_id`, `username`, `password`, `age`, `top`, `user_type`)
VALUES
	(2,X'31',X'6170706C6531',X'6665383935666332393839633366663061663134326236326433626533613864393861393233',19,0,'NL'),
	(3,X'31',X'6170706C6532',X'6665383935666332393839633366663061663134326236326433626533613864393861393233',21,0,'NL'),
	(9,X'31',X'6170706C6534',X'6665383935666332393839633366663061663134326236326433626533613864393861393233',23,0,'AL'),
	(10,X'38',X'6170706C6535',X'6665383935666332393839633366663061663134326236326433626533613864393861393233',24,0,'AL'),
	(11,X'38',X'3436343634',X'6665383935666332393839633366663061663134326236326433626533613864393861393233',25,0,X'SP')
```

## 生成 dao、mapper、service、controller 文件

找到demo-data项目下的[CodeGenerator](demo-data/src/test/java/generator/CodeGenerator.java),路径为 `demo-data/src/test/java/generator/CodeGenerator.java`。

1. 设置数据库配置：

```
        //JDBC配置
	private static final String JDBC_URL = "jdbc:mysql://sh-cynosdbmysql-grp-ar201mbc.sql.tencentcdb.com:21857/blog";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "xxxx";
	private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
```

2. 在main方法添加 table表名，model 名

比如添加 `holiday` 实体，表名是 `t_holiday`，`model` 名会自动去掉前面的 `t_`,所以 `model` 默认不写。

```
  public static void main(String[] args) {
		//可以使用多个数据表配置，或者单个数据表配置genCodeByCustomModeName
		genCodeByCustomModeName("t_holiday",null,null);
	}
```

3. 执行 main方法，控制台输出
```
Holiday.java 生成成功
17:44:00.540 [main] INFO generator.CodeGenerator - HolidayMapper.java 生成成功
17:44:00.540 [main] INFO generator.CodeGenerator - HolidayMapper.xml 生成成功
HolidayService文件生成成功
17:44:00.673 [main] INFO generator.CodeGenerator - HolidayServiceImpl文件生成成功
HolidayController.java 生成成功
```

`demo-data` 项目自动生成了 `dao`、`model` 以及 `mapper` 文件:

<img width="420" alt="image" src="https://user-images.githubusercontent.com/11553237/172045942-43e87053-f3be-40a2-a75a-23bd6e4fdc06.png">

`demo-service` 项目自动生成 service 以及 service 实现类：

<img width="420" alt="image" src="https://user-images.githubusercontent.com/11553237/172046023-347e4c24-08fb-4384-80be-ddfd1fae682a.png">

`demo-admin` 项目生成了 controller 文件：

<img width="393" alt="image" src="https://user-images.githubusercontent.com/11553237/172046086-acae51cc-2423-476a-937f-54b464c2355c.png">

 `controller` 有最基本的 `curd` 方法
 
 ```
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/add")
    public Result add(Holiday holiday) {
        holidayService.save(holiday);
        return OutUtil.success(null);
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        holidayService.deleteByPrimaryKey(id);
        return OutUtil.success(null);
    }

    @PostMapping("/update")
    public Result update(Holiday holiday) {
        holidayService.updateByPrimaryKey(holiday);
        return OutUtil.success(null);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Holiday holiday = holidayService.findById(id);
         return OutUtil.success(holiday);
    }

    @GetMapping("/list")
    public Result list(PageQuery query) {
       PageInfo<Holiday> pageInfo = holidayService.find(query);
       return OutUtil.success(pageInfo);
    }
}
 ```
 
 调用查询列表方法 `holiday/list` 就能查询数据列表了，其他方法也类同。实现一键配置 `curd` 方法。













