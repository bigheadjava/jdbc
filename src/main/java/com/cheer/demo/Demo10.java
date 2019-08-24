package com.cheer.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.cheer.util.JdbcUtil;

/**
 * 演示ResultSet元数据
 * 
 * @author 程序猿大头
 *
 */
public class Demo10 {

	public static void main(String[] args) {
		String sql = "select * from c_staff";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 允许各种滚动、并发模式是只读
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();

			if (rs != null) {
				ResultSetMetaData rsm = rs.getMetaData(); // 获取结果集元数据对象
				int columnCount = rsm.getColumnCount(); // 获取结果集字段数
				
				for (int i = 1; i <= columnCount; i++) {
					System.out.println("表名: " + rsm.getTableName(i));
					System.out.println("字段数: " + rsm.getColumnCount());
					System.out.println("字段名称: " + rsm.getColumnName(i));
					System.out.println("字段类型: " + rsm.getColumnTypeName(i));
					System.out.println("字段长度: " + rsm.getColumnDisplaySize(i));
					System.out.println("字段是否可以为null: " + rsm.isNullable(i));
					System.out.println("----------------------------------------");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
		}

	}

}
