package com.cheer.pagination.rs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RsPagination implements IRsPagination {

	protected ResultSet rs = null;
	protected int recordCount;
	protected int pageSize;
	protected int currentPage;

	public RsPagination() {

	}

	public RsPagination(ResultSet rs, int pageSize) throws SQLException {
		if (rs == null) {
			throw new SQLException("当前查询没有查到记录!");
		}
		if (pageSize < 0) {
			throw new SQLException("pageSize必须大于等于0!");
		}
		rs.last();
		recordCount = rs.getRow();
		rs.beforeFirst();
		this.rs = rs;
		this.pageSize = pageSize;
		this.currentPage = 1;
	}

	public int getPageCount() {
		if (recordCount == 0) {
			return 0;
		}
		if (pageSize == 0) {
			return 1;
		}
		double temp_pages = (double) recordCount / pageSize;
		int pages = (int) temp_pages;
		if (temp_pages > pages)
			pages++;
		return pages;
	}

	public int getPageRowsCount() {
		if (pageSize == 0) {
			return recordCount;
		}
		if (getRecordCount() == 0) {
			return 0;
		}
		if (currentPage != getPageCount()) {
			return pageSize;
		}
		return recordCount - (getPageCount() - 1) * pageSize; // 最后一页记录数
	}

	public void gotoPage(int page) {
		if (page < 1) {
			page = 1;
		}
		if (page > getPageCount()) {
			page = getPageCount();
		}
		int row = (page - 1) * pageSize + 1;
		try {
			rs.absolute(row);
			currentPage = page;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void goToFirstPage() throws SQLException {
		rs.beforeFirst();
		currentPage = 1;
	}

	public void goToLastPage() throws SQLException {
		int row = (getPageCount() - 1) * pageSize;
		rs.absolute(row);
		currentPage = getPageCount();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void goToPreviousPage() throws SQLException {
		if (currentPage <= 2) {
			rs.beforeFirst();
		} else {
			int row = (currentPage - 2) * pageSize;
			rs.absolute(row);
		}
		currentPage--;
	}

	public void goToNextPage() throws SQLException {
		int row = 0;
		if (currentPage == getPageCount()) {
			row = (currentPage - 1) * pageSize;
		} else {
			row = currentPage * pageSize;
		}
		rs.absolute(row);
		currentPage++;
	}

}
