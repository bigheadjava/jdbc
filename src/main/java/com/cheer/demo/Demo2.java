package com.cheer.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cheer.util.JdbcUtil;

public class Demo2 {

	public static void main(String[] args) {
		Connection conn = JdbcUtil.getConnection();
		Statement st = JdbcUtil.getStatement(conn);
		ResultSet rs = null;
		String sql_1 = "select * from b_staff";
		try {
			rs = st.executeQuery(sql_1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeResource(conn, st, rs);
		}

	}

}
