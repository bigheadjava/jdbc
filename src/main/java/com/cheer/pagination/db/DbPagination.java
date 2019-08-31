package com.cheer.pagination.db;

import java.sql.SQLException;

public class DbPagination implements IDbPagination {
	protected int recordCount; // 总记录数
	protected int pageSize; // 每页多少记录
	protected int currentPage; // 当前页数
	protected int startPos; // 查询开始位置

	public DbPagination() {

	}

	public DbPagination(int recordCount, int pageSize) {
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.startPos = 0;
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
		startPos = (page - 1) * pageSize;
		currentPage = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void goToPreviousPage() throws SQLException {
		if (currentPage <= 2) {
			startPos = 0;
		} else {
			startPos = (currentPage - 2) * pageSize;
		}
		currentPage--;
		if(currentPage < 1) {
			currentPage = 1;
		}
	}

	public void goToNextPage() throws SQLException {
		if (currentPage == getPageCount()) {
			startPos = (currentPage - 1) * pageSize;
		} else {
			startPos = currentPage * pageSize;
			currentPage++;
		}
	}

	public void goToFirstPage() throws SQLException {
		startPos = 0;
		currentPage = 1;
	}

	public void goToLastPage() throws SQLException {
		startPos = (getPageCount() - 1) * pageSize;
		currentPage = getPageCount();
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getStartPos() {
		return startPos;
	}
}
