package com.diet.admin;


import com.alibaba.fastjson.JSONReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author LiuYu
 * @date 2018/7/11
 */
public class JsonToSqlTest {
    private static final String FILE_PATH = "D:\\local.food.json";

    public static void main(String[] args) throws FileNotFoundException {

        JSONReader jsonReader = new JSONReader(new FileReader(new File(FILE_PATH)));

        jsonReader.startArray();//---> [

        while (jsonReader.hasNext()) {
            String info = jsonReader.readObject().toString();//---> {"key":"value"}
            System.out.println(info);
        }
        jsonReader.endArray();//---> ]
        jsonReader.close();
    }
}
