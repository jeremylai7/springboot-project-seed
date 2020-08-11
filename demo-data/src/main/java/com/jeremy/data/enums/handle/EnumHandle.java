package com.jeremy.data.enums.handle;


import com.jeremy.data.enums.BaseCodeEnum;
import com.jeremy.data.enums.util.CodeEnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Auther: laizc
 * @Date: 2019/1/12 13:34
 * @Description: 枚举转换器
 */
public class EnumHandle<E extends Enum<?> & BaseCodeEnum> extends BaseTypeHandler<BaseCodeEnum> {
    private Class<E> type;

    public EnumHandle(Class<E> type){
        if(type == null){
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BaseCodeEnum baseCodeEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,baseCodeEnum.getCode());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String code = resultSet.getString(s);
        return resultSet.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String code = resultSet.getString(i);
        return resultSet.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String code = callableStatement.getString(i);
        return callableStatement.wasNull() ? null : codeOf(code);
    }

    private E codeOf(String code){
        try {
            return CodeEnumUtil.codeOf(type, code);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
        }
    }
}
