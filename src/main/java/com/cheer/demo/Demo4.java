package com.cheer.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.cheer.util.JdbcUtil;

public class Demo4 {

	public static void main(String[] args) {
		Connection conn = JdbcUtil.getConnection();
		Statement st = JdbcUtil.getStatement(conn);
		String sql = "select * from b_staff";
		try {
			boolean bValue = st.execute(sql);
			System.out.println(bValue);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeResource(conn, st, null);
		}
	}

}
