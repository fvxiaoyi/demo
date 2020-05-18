package com.example.demo.model;

public class Table {
	public String name;
	public String tableName;

	public void setTableName(String tableName) {
		this.tableName = tableName;
		this.name = tableName;//TODO 去复数，驼峰
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}
}
