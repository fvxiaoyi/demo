package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.Field;
import com.example.demo.model.Table;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private JdbcTemplate tpl;

	@Test
	void contextLoads() throws Exception {
		Connection conn = tpl.getDataSource().getConnection();
		DatabaseMetaData data = conn.getMetaData();
		Table table = new Table();
		ResultSet tableRet = data.getTables(null, "%", "t_article", new String[] { "TABLE" });
		while (tableRet.next()) {
			table.setTableName(tableRet.getString("TABLE_NAME"));
		}
		List<Field> fields = new ArrayList<>();
		ResultSet colRet = data.getColumns(null, "%", "t_article", "%");
		while (colRet.next()) {
			Field f = new Field();
			f.setColumnName(colRet.getString("COLUMN_NAME"));
			f.setType(colRet.getString("TYPE_NAME"));
			f.nullable = colRet.getInt("NULLABLE") != 0;
			fields.add(f);
		}
		gen(table, fields);
	}

	public void gen(Table table, List<Field> fields) {
		Configuration configuration = new Configuration();
		configuration.setClassicCompatible(true);
		Writer out = null;
		try {
			Resource path = new ClassPathResource("/tpl");
			File file = path.getFile(); // will fail if not resolvable in the file system
			TemplateLoader loader = new FileTemplateLoader(file);
			configuration.setTemplateLoader(loader);
			// step2 获取模版路径
			configuration.setDirectoryForTemplateLoading(new File("src/main/resources/tpl"));
			// step3 创建数据模型
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("serverName", "article");
			dataMap.put("moduleName", "article");
			dataMap.put("table", table);
			dataMap.put("fields", fields);
			// step4 加载模版文件
			Template template = configuration.getTemplate("domain.ftl");
			// step5 生成数据
			File docFile = new File("src/main/resources/AutoCodeDemo.java");
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
			// step6 输出文件
			template.process(dataMap, out);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^AutoCodeDemo.java 文件创建成功 !");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.flush();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		Writer out = null;
		try {
			Resource path = new ClassPathResource("/tpl");
			File file = path.getFile(); // will fail if not resolvable in the file system
			TemplateLoader loader = new FileTemplateLoader(file);
			configuration.setTemplateLoader(loader);
			// step2 获取模版路径
			configuration.setDirectoryForTemplateLoading(new File("src/main/resources/tpl"));
			// step3 创建数据模型
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("classPath", "com.freemark.hello");
			dataMap.put("className", "AutoCodeDemo");
			dataMap.put("helloWorld", "通过简单的 <代码自动生产程序> 演示 FreeMarker的HelloWorld！");
			// step4 加载模版文件
			Template template = configuration.getTemplate("domain.ftl");
			// step5 生成数据
			File docFile = new File("src/main/resources/AutoCodeDemo.java");
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
			// step6 输出文件
			template.process(dataMap, out);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^AutoCodeDemo.java 文件创建成功 !");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.flush();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
