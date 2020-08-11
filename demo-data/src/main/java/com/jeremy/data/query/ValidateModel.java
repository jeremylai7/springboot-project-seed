package com.jeremy.data.query;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: laizc
 * @Date: Created in 9:52 2020-03-02
 */
@Getter
@Setter
public class ValidateModel {

    @Length(min = 4,message = "长度大于12")
    @NotEmpty(message = "不能，我发")
    @NotNull(message = "用户名不能为空")
    private String username;


    private String password;



}
