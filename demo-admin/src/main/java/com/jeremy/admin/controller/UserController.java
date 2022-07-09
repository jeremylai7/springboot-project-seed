package com.jeremy.admin.controller;
import com.jeremy.demo.web.core.utils.OutUtil;
import com.jeremy.demo.web.core.view.Result;
import com.jeremy.data.user.model.User;
import com.jeremy.service.user.UserService;
import com.jeremy.data.query.PageQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* Created by Jeremy on 2022-07-09.
*/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Result add(User user) {
        userService.save(user);
        return OutUtil.success(null);
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        userService.deleteByPrimaryKey(id);
        return OutUtil.success(null);
    }

    @PostMapping("/update")
    public Result update(User user) {
        userService.updateByPrimaryKey(user);
        return OutUtil.success(null);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        User user = userService.findById(id);
         return OutUtil.success(user);
    }

    @GetMapping("/list")
    public Result list(PageQuery query) {
       PageInfo<User> pageInfo = userService.find(query);
       return OutUtil.success(pageInfo);
    }
}
