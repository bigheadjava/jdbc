package com.cheer.pagination.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.cheer.util.JdbcUtil;

public class DbPaginationDemo {

	public static void main(String[] args) {
		String sql = "select * from c_staff limit ?,?";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Scanner sc = null;
		try {
			ps = conn.prepareStatement(sql);
			int count = getRecordCount();
			System.out.println("查询出" + count + "条记录");
			if (count == 0) {
				return;
			}
			IDbPagination pg = new DbPagination(count, 6);
			loop: do {
				System.out.println("请选择操作: 0 => 上一页; 1 => 下一页; 2 => 第一页; 3 => 最后一页; 4 => 跳转");
				sc = new Scanner(System.in);
				int oper = sc.nextInt();
				switch (oper) {
				case 0:
					pg.goToPreviousPage();
					break;
				case 1:
					pg.goToNextPage();
					break;
				case 2:
					pg.goToFirstPage();
					break;
				case 3:
					pg.goToLastPage();
					break;
				case 4:
					System.out.println("请输入跳转的页数: ");
					sc = new Scanner(System.in);
					int page = sc.nextInt();
					pg.gotoPage(page);
					break;
				default:
					break loop;
				}
				ps.setInt(1, pg.getStartPos());
				ps.setInt(2, pg.getPageRowsCount());
				rs = ps.executeQuery();
				printResult(rs);
			} while (true);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
			sc.close();
		}
	}

	private static int getRecordCount() {
		String sql = "select * from c_staff";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.absolute(-1);
			count = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
		}
		return count;
	}

	private static void printResult(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println("第 " + rs.getRow() + " 条记录");
				System.out.println("员工编号: " + rs.getString(1) + "; 员工姓名: " + rs.getString("staff_name"));
			}
			System.out.println("------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
