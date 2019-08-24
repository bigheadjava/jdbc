package com.cheer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

	private final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private final static String URL = "jdbc:mysql://127.0.0.1:3306/cheer?useUnicode=true&characterEncoding=utf8";
	private final static String USER_NAME = "root";
	private final static String PASSWORD = "password";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Statement getStatement(Connection conn) {
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	public static void closeResource(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				System.out.println("关闭ResultSet成功...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
				System.out.println("关闭Statement成功...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
				System.out.println("关闭Connection成功...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
