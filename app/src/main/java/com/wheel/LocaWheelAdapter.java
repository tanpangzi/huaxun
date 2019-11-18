package com.wheel;

public class LocaWheelAdapter<T> implements WheelAdapter {

	/** The default items length */
	public static final int DEFAULT_LENGTH = -1;

	// items
	private T items[];
	// length
	private int length;
	// format
	private String format;

	/**
	 * Constructor
	 * 
	 * @param items
	 *            the items
	 * @param length
	 *            the max items length
	 */
	public LocaWheelAdapter(T items[], int length) {
		this.items = items;
		this.length = length;
	}

	/**
	 * Contructor
	 * 
	 * @param items
	 *            the items
	 */
	public LocaWheelAdapter(T items[]) {
		this.items = items;
		this.length = length;
	}

	public String getItem(int index) {
		if (index >= 0 && index < items.length) {
			values = items[index].toString();
			setValue(values);
			this.indexs = index;
			return values;
		}
		return null;
	}

	String values;
	int indexs;

	public int getIndexs() {
		return indexs;
	}

	// 返回当前选中的
	public String getValues() {
		return values;
	}

	public void setValue(String value) {
		this.values = value;
	}

	public int getItemsCount() {
		return length;
	}

	public int getMaximumLength() {
		return length;
	}

}
