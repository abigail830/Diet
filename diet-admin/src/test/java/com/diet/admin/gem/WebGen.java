package com.diet.admin.gem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiuYu
 * @date 2017/12/5
 */
public class WebGen {
    /**
     * 功能：生成Dao类主体代码
     */
    public static void parse(String tableName) {
        String webName = GenTool.initcap(tableName) + "Controller";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.webPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import com.diet.admin.core.BaseController;\r\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
        sb.append("import org.springframework.web.bind.annotation.RestController;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@RestController\r\n");
        sb.append("@RequestMapping(BaseController.API + \"/" + GenTool.initcap2(tableName) + "\")\r\n");

        //实体部分
        sb.append("public class " + GenTool.initcap(webName) + " extends BaseController {\r\n\r\n");
        sb.append("}\r\n");

        try {
            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];
            String outputPath = path + "/src/main/java/" + GenTool.webPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if(!outPutFile.exists()){
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + GenTool.initcap(webName) + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
