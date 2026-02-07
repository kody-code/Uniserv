package com.kody.uniserv.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.UUID;

/**
 * <p>
 * Postgres UUID 类型处理器
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@MappedTypes(UUID.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class PostgresUUIDTypeHandler extends BaseTypeHandler<UUID> {

    /**
     * 设置参数（插入/更新时用）
     *
     * @param ps        预编译语句
     * @param i         参数索引
     * @param parameter 参数
     * @param jdbcType  数据库类型
     * @throws SQLException SQL 错误
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter, Types.OTHER);
    }

    /**
     * 查询时从结果集按字段名获取 UUID
     *
     * @param rs         结果集
     * @param columnName 列名
     * @return UUID
     * @throws SQLException SQL 错误
     */
    @Override
    public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName, UUID.class);
    }

    /**
     * 查询时从结果集按索引获取 UUID
     *
     * @param rs          结果集
     * @param columnIndex 列索引
     * @return UUID
     * @throws SQLException SQL 错误
     */
    @Override
    public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex, UUID.class);
    }

    /**
     * 存储过程调用时获取 UUID
     *
     * @param cs          调用存储过程
     * @param columnIndex 列索引
     * @return UUID
     * @throws SQLException SQL 异常
     */
    @Override
    public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getObject(columnIndex, UUID.class);
    }
}