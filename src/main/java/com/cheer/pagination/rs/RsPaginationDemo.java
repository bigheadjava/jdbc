package com.cheer.pagination.rs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.cheer.util.JdbcUtil;

public class RsPaginationDemo {

	public static void main(String[] args) {
		String sql = "select * from c_staff";
		Connection conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Scanner sc = null;
		try {
			// 允许各种滚动、并发模式是只读
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			IRsPagination pg = new RsPagination(rs, 6);
			loop: do {
				System.out.println("请选择操作: 0 => 上一页; 1 => 下一页; 2 => 第一页; 3 => 最后一页; 4 => 跳转");
				sc = new Scanner(System.in);
				int oper = sc.nextInt();
				switch (oper) {
				case 0:
					pg.goToPreviousPage();
					printResult(rs, pg);
					break;
				case 1:
					pg.goToNextPage();
					printResult(rs, pg);
					break;
				case 2:
					pg.goToFirstPage();
					printResult(rs, pg);
					break;
				case 3:
					pg.goToLastPage();
					printResult(rs, pg);
					break;
				case 4:
					System.out.println("请输入跳转的页数: ");
					sc = new Scanner(System.in);
					int page = sc.nextInt();
					pg.gotoPage(page);
					printResult(rs, pg);
					break;
				default:
					break loop;
				}
			} while (true);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(conn, ps, rs);
			sc.close();
		}
	}

	private static void printResult(ResultSet rs, IRsPagination pg) {
		try {
			int count = 1;
			while (rs.next()) {
				if (count > pg.getPageRowsCount()) {
					break;
				}
				System.out.println("第 " + rs.getRow() + " 条记录");
				System.out.println("员工编号: " + rs.getString(1) + "; 员工姓名: " + rs.getString("staff_name"));
				count++;
			}
			System.out.println("------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
