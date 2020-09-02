package com.jeremy.admin.controller;

import com.github.pagehelper.PageInfo;
import com.jeremy.data.query.PageQuery;
import com.jeremy.data.user.model.User;
import com.jeremy.demo.web.core.utils.OutUtil;
import com.jeremy.demo.web.core.view.Result;
import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.user.IndexServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Logined
@Api(value = "首页api",description = "首页api desc")
@RestController
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private IndexServer indexServer;

	@ApiOperation(value = "添加用户")
    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",paramType = "query",required = true),
            @ApiImplicitParam(name = "password",value = "密码 md5加密密码",paramType = "query",required = true)
    })
    public Result add(String username, String password) throws BusinessException {
	    indexServer.add(username,password);
		int begin = 78679;
		int length = 1483647;
		for (int i = begin; i < length; i++) {
			indexServer.add("apple"+i, i+""+8839);
		}
	    return OutUtil.success(null);
	}

	@ApiOperation(value = "全修改用户")
    @PostMapping("/update-all")
    public Result updateAll(User user) throws BusinessException {
        indexServer.updateAll(user);
	    return OutUtil.success(null);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("/update")
    public Result update(User user) throws BusinessException {
	    indexServer.update(user);
	    return OutUtil.success(null);
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    public Result delete(Integer id){
	    indexServer.deleteByPrimaryKey(id);
	    return OutUtil.success(null);
    }

    @ApiOperation(value = "分页获取用户",response = User.class)
    @GetMapping("/find")
    public Result find(PageQuery query){
        PageInfo<User> list = indexServer.find(query);
	    return OutUtil.success(list);
    }

    @ApiOperation(value = "redis 获取用户")
    @GetMapping("/find-redis")
    public Result findRedis(PageQuery query){
        List<User> list = indexServer.findRedis(query);
        return OutUtil.success(list);
    }

    @ApiOperation(value = "清楚缓存")
    @GetMapping("/clean-cache")
    public Result cleanCache(){
	    indexServer.ClearnCache();
	    return OutUtil.success(null);
    }

    @ApiOperation(value = "获取所有用户",response = User.class)
    @GetMapping(value = "/all")
    public Result userList(){
	    List<User> list = indexServer.findAll();
        return OutUtil.success(list);
    }

    @ApiOperation(value = "通过id获取用户")
	@GetMapping("/get-by-id")
	public Result getById(int id){
	    User user = indexServer.findById(id);
		return OutUtil.success(user);
    }

    @ApiOperation("事务测试")
	@PostMapping("/trans-test")
	public Result transTest(String password){
	    try {
		    indexServer.transTest(password);
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
	    return OutUtil.success(null);
    }

}
