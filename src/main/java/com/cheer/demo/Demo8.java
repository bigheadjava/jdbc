package com.cheer.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cheer.util.JdbcUtil;

/**
 * 演示通过ResultSet更改数据
 * 
 * @author 程序猿大头
 *
 */
public class Demo8 {

	public static void main(String[] args) {
		String sql = "select * from student";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 允许各种滚动、并发模式是只读
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();

			// 将游标滚动到插入行
			rs.last();
			// 修改记录数据
			rs.updateString(1, "S008");
			rs.updateString("stu_name", "新李四");
			// 将缓冲区中的数据刷新到数据库
			rs.updateRow();

			// 更改之后查看数据
			System.out.println("本次查询出记录 " + JdbcUtil.getRecordCount(rs) + " 条");
			System.out.println("学号: " + rs.getString(1) + "; 学生姓名: " + rs.getString("stu_name"));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
		}

	}

}
