package com.kody.uniserv.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * List<String> 类型处理器 - 处理PostgreSQL数组类型
 * </p>
 *
 * @author kody
 * @since 2026-02-08
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class ListStringTypeHandler extends BaseTypeHandler<List<String>> {

    /**
     * <p>
     * 设置参数
     * </p>
     *
     * @param ps        PreparedStatement
     * @param i         参数索引
     * @param parameter 参数
     * @param jdbcType  参数类型
     * @throws SQLException SQL执行异常
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String[] array = parameter.toArray(new String[0]);
        Array sqlArray = ps.getConnection().createArrayOf("text", array);
        ps.setArray(i, sqlArray);
    }

    /**
     * <p>
     * 获取结果
     * </p>
     *
     * @param rs         ResultSet
     * @param columnName 列名
     * @return 结果
     * @throws SQLException SQL执行异常
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Array array = rs.getArray(columnName);
        if (array == null) {
            return new ArrayList<>();
        }
        return Arrays.asList((String[]) array.getArray());
    }

    /**
     * <p>
     * 获取结果
     * </p>
     *
     * @param rs          ResultSet
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException SQL执行异常
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Array array = rs.getArray(columnIndex);
        if (array == null) {
            return new ArrayList<>();
        }
        return Arrays.asList((String[]) array.getArray());
    }

    /**
     * <p>
     * 获取结果
     * </p>
     *
     * @param cs          CallableStatement
     * @param columnIndex 列索引
     * @return 结果
     * @throws SQLException SQL执行异常
     */
    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Array array = cs.getArray(columnIndex);
        if (array == null) {
            return new ArrayList<>();
        }
        return Arrays.asList((String[]) array.getArray());
    }
}
