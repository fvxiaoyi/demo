package com.example.demo;

import com.example.demo.model.Field;
import com.example.demo.model.Table;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

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

import static com.example.demo.utils.StringUtils.toUpperCaseFirstOne;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private JdbcTemplate tpl;

    Configuration configuration;

    static String serverName = "customer";
    static String moduleName = "customer";
    static String tableName = "customers";
    static String name = "Customer";

    static String domainFolderName = "";
    static String serviceFolderName = "";
    static String webFolderName = "";

    @Test
    void contextLoads() throws Exception {
        init();
        initApiFolder();
        initFolder();
        Connection conn = tpl.getDataSource().getConnection();
        DatabaseMetaData data = conn.getMetaData();
        Table table = new Table();
        table.tableName = tableName;
        table.name = name;
        List<Field> fields = new ArrayList<>();
        ResultSet colRet = data.getColumns(null, "%", "customers", "%");
        while (colRet.next()) {
            Field f = new Field();
            f.setColumnName(colRet.getString("COLUMN_NAME"));
            f.setType(colRet.getString("TYPE_NAME"));
            f.nullable = colRet.getInt("NULLABLE") != 0;
            fields.add(f);
        }
        gen(table, fields);
    }

    public void init() {
        configuration = new Configuration();
        configuration.setClassicCompatible(true);
        try {
            Resource path = new ClassPathResource("/tpl");
            File file = path.getFile(); // will fail if not resolvable in the file system
            TemplateLoader loader = new FileTemplateLoader(file);
            configuration.setTemplateLoader(loader);
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/tpl"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void initApiFolder() throws Exception {
        String folder = "/" + serverName + "-service-interface/app/" + serverName + "/api/";
        String folderPath = "c:/gen" + folder;
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // webservice interface
        String webservicePath = folderPath + toUpperCaseFirstOne(moduleName) + "WebService.java";
        File webserviceFile = new File(webservicePath);
        if (!webserviceFile.exists()) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("serverName", serverName);
            dataMap.put("moduleName", moduleName);
            // step4 加载模版文件
            Template template = configuration.getTemplate("webservice.ftl");
            // step5 生成数据
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webserviceFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
        }
        // req resp
        String reqAndRespPath = folderPath + moduleName + "/";
        File reqAndRespPathDir = new File(reqAndRespPath);
        if (!reqAndRespPathDir.exists()) {
            reqAndRespPathDir.mkdirs();
        }
        File createReq = new File(reqAndRespPath + "Create" + toUpperCaseFirstOne(moduleName) + "Request.java");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverName", serverName);
        dataMap.put("moduleName", moduleName);
        dataMap.put("tableName", tableName);
        Template template = configuration.getTemplate("CreateRequest.ftl");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(createReq)));
        template.process(dataMap, out);
        out.flush();

        File getResp = new File(reqAndRespPath + "Get" + toUpperCaseFirstOne(moduleName) + "Response.java");
        template = configuration.getTemplate("GetResponse.ftl");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getResp)));
        template.process(dataMap, out);
        out.flush();

        File updateReq = new File(reqAndRespPath + "Update" + toUpperCaseFirstOne(moduleName) + "Request.java");
        template = configuration.getTemplate("UpdateRequest.ftl");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(updateReq)));
        template.process(dataMap, out);
        out.flush();

        File searchReq = new File(reqAndRespPath + "Search" + toUpperCaseFirstOne(moduleName) + "Request.java");
        template = configuration.getTemplate("SearchRequest.ftl");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(searchReq)));
        template.process(dataMap, out);
        out.flush();

        File searchResp = new File(reqAndRespPath + "Search" + toUpperCaseFirstOne(moduleName) + "Response.java");
        template = configuration.getTemplate("SearchResponse.ftl");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(searchResp)));
        template.process(dataMap, out);
        out.flush();

        File searchDetailResp = new File(reqAndRespPath + "Search" + toUpperCaseFirstOne(moduleName) + "DetailResponse.java");
        template = configuration.getTemplate("SearchDetailResponse.ftl");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(searchDetailResp)));
        template.process(dataMap, out);
        out.flush();
    }

    public void initFolder() throws Exception {
        String folder = "";
        if (serverName.equals(moduleName)) {
            folder = "/" + serverName + "-service/app/" + serverName + "/";
        } else {
            folder = "/" + serverName + "-service/app/" + serverName + "/" + moduleName + "/";
        }
        String folderPath = "c:/gen" + folder;
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String mainPath = "c:/gen/" + serverName + "-service/Main.java";
        File mainFile = new File(mainPath);
        if (!mainFile.exists()) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("serverName", serverName);
            // step4 加载模版文件
            Template template = configuration.getTemplate("main.ftl");
            // step5 生成数据
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mainFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
        }

        domainFolderName = folderPath + "domain/";
        File domainFolder = new File(domainFolderName);
        if (!domainFolder.exists()) {
            domainFolder.mkdirs();
        }

        serviceFolderName = folderPath + "service/";
        String servicePath = serviceFolderName + toUpperCaseFirstOne(moduleName) + "Service.java";
        File serviceFolder = new File(serviceFolderName);
        if (!serviceFolder.exists()) {
            serviceFolder.mkdirs();
        }
        File serviceFile = new File(servicePath);
        if (!serviceFile.exists()) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("serverName", serverName);
            dataMap.put("moduleName", moduleName);
            // step4 加载模版文件
            Template template = configuration.getTemplate("service.ftl");
            // step5 生成数据
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
        }

        webFolderName = folderPath + "web/";
        File webFolder = new File(webFolderName);
        if (!webFolder.exists()) {
            webFolder.mkdirs();
        }
        String webPath = webFolderName + toUpperCaseFirstOne(moduleName) + "WebServiceImpl.java";
        File webFile = new File(webPath);
        if (!webFile.exists()) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("serverName", serverName);
            dataMap.put("moduleName", moduleName);
            // step4 加载模版文件
            Template template = configuration.getTemplate("webserviceimpl.ftl");
            // step5 生成数据
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(webFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
        }
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
            dataMap.put("serverName", serverName);
            dataMap.put("moduleName", moduleName);
            dataMap.put("table", table);
            dataMap.put("fields", fields);
            // step4 加载模版文件
            Template template = configuration.getTemplate("domain.ftl");
            // step5 生成数据
            File docFile = new File(domainFolderName + table.name + ".java");
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
