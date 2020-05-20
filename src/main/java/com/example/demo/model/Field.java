package com.example.demo.model;

import com.example.demo.utils.StringUtils;

public class Field {
    public String type;
    public String name;
    public String columnName;
    public boolean nullable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getType() {
        return type;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.name = StringUtils.camelName(columnName);
    }

    public void setType(String type) {
        if ("VARCHAR".equals(type)) {
            this.type = "String";
        } else if ("DATETIME".equals(type)) {
            this.type = "LocalDateTime";
        } else if ("DATE".equals(type)) {
            this.type = "LocalDate";
        } else if ("INT".equals(type)) {
            this.type = "int";
        } else if ("ENUM".equals(type)) {
            this.type = "String";
        } else if ("ENUM".equals(type)) {
            this.type = "String";
        }
    }
}
