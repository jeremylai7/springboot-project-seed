package com.jeremy.common.util.excel.model;

/**
 * @author
 * @create on 2020/7/23 17:09
 * @desc
 **/
public class Merge {

	/**
	 * 开始合并行
	 */
	private int fromRow;

	/**
	 * 结束合并行
	 */
	private int toRow;

	/**
	 * 开始下标
	 */
	private int fromIndex;

	/**
	 * 结束下标
	 */
	private int toIndex;

	public int getFromRow() {
		return fromRow;
	}

	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}

	public int getToRow() {
		return toRow;
	}

	public void setToRow(int toRow) {
		this.toRow = toRow;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public int getToIndex() {
		return toIndex;
	}

	public void setToIndex(int toIndex) {
		this.toIndex = toIndex;
	}
}
