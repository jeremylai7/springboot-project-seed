package com.springbootredis.controller;

import com.springbootredis.model.Result;
import com.springbootredis.model.ValidateModel;
import com.springbootredis.util.OutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: laizc
 * @Date: Created in 10:41 2019-08-29
 */
@Api(value = "其他示例api",description = "其他示例api desc")
@RestController
@Validated
@RequestMapping("/example")
public class ExampleController {
    @GetMapping("/index")
    public Result index(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        System.out.println(session.isNew());

        return null;
    }

    @ApiOperation(value = "字段验证测试")
    @GetMapping("/index2")
    public Result index2(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        System.out.println(session.isNew());

        return null;
    }

    @GetMapping("/vali2")
    public Result validate(@Validated @ModelAttribute ValidateModel validateModel){
        System.out.println(validateModel.getUsername());
        return OutUtil.success(null);
    }

    @GetMapping("/vali3")
    public Result vali3(@NotNull @NotEmpty @ModelAttribute String username){
        return OutUtil.success(null);
    }



}
