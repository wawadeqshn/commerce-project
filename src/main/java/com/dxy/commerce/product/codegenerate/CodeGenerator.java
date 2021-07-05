package com.dxy.commerce.product.codegenerate;

import com.baomidou.mybatisplus.core.exceptions.*;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.generator.*;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.*;
import com.baomidou.mybatisplus.generator.config.rules.*;
import com.baomidou.mybatisplus.generator.engine.*;

import java.util.*;

/**
 * 代码生成器
 * @author dingxy
 * @date 2021/2/27 3:01 下午
 * @return
 */
public class CodeGenerator {

    /**
     * 读取控制台内容
     * @param tip
     * @author dingxy
     * @date 2021/2/27 3:02 下午 
     * @return java.lang.String
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        generator("dingxy", "mysql");

    }

    private static void generator(String userName, String type){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/generator");
        gc.setAuthor(userName);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        mpg.setDataSource(type.equals("mysql") ?  getMysqlDsc() :  getSqlServerDsc());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.dxy.commerce.product");
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Dao" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // strategy.setSuperControllerClass("com.jarvis.pms.controller.BaseController");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

    private static DataSourceConfig getMysqlDsc(){
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + MYSQL_URI + ":" + PORT + "/" + MYSQL_DB_NAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(MYSQL_U_NAME);
        dsc.setPassword(MYSQL_PASS_WORD);

        return dsc;
    }

    private static DataSourceConfig getSqlServerDsc(){
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(SQLSERVER_URI);
        dsc.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dsc.setUsername(SQLSERVER_U_NAME);
        dsc.setPassword(SQLSERVER_PASS_WORD);

        return dsc;
    }


    private final static String MYSQL_URI = "sd-test-env.cleordivudm0.rds.cn-northwest-1.amazonaws.com.cn";
    private final static String MYSQL_DB_NAME = "wind_eye";
    private final static String MYSQL_U_NAME = "wind_usr";
    private final static String MYSQL_PASS_WORD = "sense@windeye";
    private final static String PORT = "3306";

//    private final static String MYSQL_URI = "sdai-core-config.mysql.rds.aliyuncs.com";
//    private final static String MYSQL_DB_NAME = "authentication";
//    private final static String MYSQL_U_NAME = "auth_rd";
//    private final static String MYSQL_PASS_WORD = "sdai@2020A1";
//    private final static String PORT = "6603";


    private final static String SQLSERVER_URI = "jdbc:sqlserver://52.83.112.96:1433;DatabaseName=wind_eye_db";
    private final static String SQLSERVER_U_NAME = "sa";
    private final static String SQLSERVER_PASS_WORD = "sdai@2018A1";


}
