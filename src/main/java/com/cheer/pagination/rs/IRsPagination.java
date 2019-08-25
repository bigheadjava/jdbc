package com.cheer.pagination.rs;

import java.sql.SQLException;

public interface IRsPagination {

	// 返回总页数
	int getPageCount();

	// 返回当前页记录数
	int getPageRowsCount();

	// 跳转到指定页
	void gotoPage(int page);

	// 返回每页多少条记录
	int getPageSize();

	// 返回总的记录数
	int getRecordCount();

	// 前一页
	void goToPreviousPage() throws SQLException;

	// 后一页
	void goToNextPage() throws SQLException;

	// 跳转到第一页
	void goToFirstPage() throws SQLException;

	// 跳转到最后一页
	void goToLastPage() throws SQLException;

	// 返回当前页号
	int getCurrentPage();
}
