package com.diet.admin.gem;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author LiuYu
 */
public class GenTool {

    public static String entityPackageOutPath = "com.diet.admin.entity";//指定实体生成所在包的路径
    public static String daoPackageOutPath = "com.diet.admin.dao";//指定Dao所在包的路径
    public static String svcPackageOutPath = "com.diet.admin.service";//指定service接口所在包的路径
    public static String svcImplPackageOutPath = "com.diet.admin.service.impl";//指定service实现所在包的路径
    public static String webPackageOutPath = "com.diet.admin.controller";//指定Controller实现所在包的路径
    public static String authorName = "LiuYu";//作者名字

    //数据库连接
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/diet?characterEncoding=utf8";
    private static final String NAME = "root";
    private static final String PASS = "";
    private static final String SQL = "SELECT table_name AS tableName, column_name AS columnName, data_type AS dataType, " +
            "column_comment AS columnComment, column_key AS columnKey, extra AS extra FROM information_schema.columns " +
            "WHERE table_schema ='diet' AND table_name LIKE '%s' ORDER BY table_name ASC, ordinal_position ASC;";
    private static final String PREFIX = "tb_";

    public static void main(String[] args) throws Exception {
//        entityPackageOutPath = "com.eshore.sys_base.pojo";
//        daoPackageOutPath = "com.diet.admin.dao";
//        svcPackageOutPath = "com.diet.admin.service";
//        svcImplPackageOutPath = "com.diet.admin.service.impl";
//        webPackageOutPath = "com.diet.admin.web";
        gen("tb_wx_user_info_ext", true, true, false, false);
    }

    /**
     * 构造函数
     */
    public static void gen(String tbName, boolean isGenEntity, boolean isGenDao, boolean isGenService, boolean isGenWeb) {
        //创建连接
        Connection con = null;
        //查要生成实体类的表
        String sql = String.format(SQL, tbName);
        System.out.println(sql);
        PreparedStatement pStemt = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            ResultSet resultSet = pStemt.executeQuery();

            Map<String, List> columnMap = new HashMap<>();
            List<ColumnInfo> columnInfos = null;
            while (resultSet.next()) {
                if (columnMap.containsKey(resultSet.getString(1))) {
                    columnInfos = columnMap.get(resultSet.getString(1));
                    columnInfos.add(new ColumnInfo(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
                } else {
                    columnInfos = new ArrayList<>();
                    columnInfos.add(new ColumnInfo(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
                    columnMap.put(resultSet.getString(1), columnInfos);
                }
            }

            for (Map.Entry<String, List> map : columnMap.entrySet()) {
                String tableName = map.getKey();
                if (isGenEntity) {
                    EntityGen.parse(tableName, map.getValue());
                }
                if (isGenDao) {
                    DaoGen.parse(tableName);
                }
                if (isGenService) {
                    ServiceGen.parse(tableName);
                }
                if (isGenWeb) {
                    WebGen.parse(tableName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生成完毕！");
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public static String initcap(String str) {
        if(StringUtils.isNotBlank(PREFIX)){
            str = str.replace(PREFIX, "");
        }
        String[] arr = str.split("_");
        String result = "";
        for (int i=0; i<arr.length; i++) {
            if(i == 0) {
                char[] ch = initcap0(arr[i]).toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                    ch[0] = (char) (ch[0] - 32);
                }
                result += new String(ch);
            } else {
                result += initcap0(arr[i]);
            }
        }
        return result;
    }

    /**
     * 功能：将输入字符串的首字母改成小写
     *
     * @param str
     * @return
     */
    public static String initcap2(String str) {
        if(StringUtils.isNotBlank(PREFIX)){
            str = str.replace(PREFIX, "");
        }
        String[] arr = str.split("_");
        String result = "";
        for (int i=0; i<arr.length; i++) {
            if(i == 0) {
                char[] ch = initcap0(arr[i]).toCharArray();
                if (ch[0] >= 'A' && ch[0] <= 'Z') {
                    ch[0] = (char) (ch[0] + 32);
                }
                result += new String(ch);
            } else {
                result += initcap0(arr[i]);
            }

        }
        return result;
    }

    private static String initcap0(String str){
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
