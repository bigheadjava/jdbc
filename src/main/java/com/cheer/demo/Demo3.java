package com.cheer.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.cheer.util.JdbcUtil;

public class Demo3 {

	public static void main(String[] args) {
		Connection conn = JdbcUtil.getConnection();
		Statement st = JdbcUtil.getStatement(conn);
		String sql = "insert into b_staff values('S5','刘德华','D2')";
		try {
			int count = st.executeUpdate(sql);
			System.out.println(count);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeResource(conn, st, null);
		}
	}

}
