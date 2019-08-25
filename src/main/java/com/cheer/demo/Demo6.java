package com.cheer.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cheer.util.JdbcUtil;

/**
 * 演示ResultSet用法
 * 
 * @author 程序猿大头
 *
 */
public class Demo6 {

	public static void main(String[] args) {
		String sql = "select * from c_staff";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 允许各种滚动、并发模式是只读
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			// 游标滚动到第一条记录
			rs.first();
			printResult(rs);

			// 游标滚动到下一条记录
			rs.next();
			printResult(rs);

			// 游标滚动到第五条记录
			rs.absolute(5);
			printResult(rs);
		
			//游标滚动到第8行
			rs.relative(3);
			printResult(rs);
					
			//游标滚动到第四行记录
			rs.relative(-4);
			printResult(rs);
			
			//游标滚动到第三行记录
			rs.previous();
			printResult(rs);
			
			//游标滚动到倒数第二行
			rs.absolute(-2);
			printResult(rs);
			
			System.out.println("本次查询出记录 " + JdbcUtil.getRecordCount(rs) + " 条");
			//遍历结果集
			rs.absolute(0);
			while(rs.next()) {
				printResult(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
		}

	}

	private static void printResult(ResultSet rs) {
		try {
			System.out.println("第 " + rs.getRow() + " 条记录");
			System.out.println("员工编号: " + rs.getString(1) + "; 员工姓名: " + rs.getString("staff_name"));
			System.out.println("------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
