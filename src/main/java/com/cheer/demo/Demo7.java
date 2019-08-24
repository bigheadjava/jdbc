package com.cheer.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cheer.util.JdbcUtil;

/**
 * 演示通过ResultSet插入数据
 * 
 * @author 程序猿大头
 *
 */
public class Demo7 {

	public static void main(String[] args) {
		String sql = "select * from student";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 允许各种滚动、并发模式是只读
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			
			//插入之前查看记录数
			System.out.println("本次查询出记录 " + JdbcUtil.getRecordCount(rs) + " 条");
			
			//将游标滚动到插入行
			rs.moveToInsertRow();
			//向缓冲区中写入一条数据
			rs.updateString(1, "S002");
			rs.updateString("stu_name", "李四");
			//将缓冲区中的数据刷新到数据库
			rs.insertRow();
			
			//插入之后查看记录数
			System.out.println("本次查询出记录 " + JdbcUtil.getRecordCount(rs) + " 条");

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
		}

	}

}
