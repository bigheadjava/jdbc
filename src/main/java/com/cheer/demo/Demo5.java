package com.cheer.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cheer.util.JdbcUtil;

public class Demo5 {

	public static void main(String[] args) {
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from b_staff where staff_no = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "D2");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeResource(conn, ps, rs);
		}
	}

}
