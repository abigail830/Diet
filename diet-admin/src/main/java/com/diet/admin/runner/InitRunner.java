package com.diet.admin.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.diet.admin.caches.BaseCacheService;
import com.diet.admin.dao.CofInfoMapper;
import com.diet.admin.dao.CookbookMapper;
import com.diet.admin.entity.CofInfo;
import com.diet.admin.entity.Cookbook;
import com.diet.admin.entity.TbTest;
import com.diet.admin.service.TbTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * @author LiuYu
 * @date 2018/5/7
 */
@Component
@Order(value = 1)
public class InitRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TbTestService tbTestService;

    @Autowired
    private BaseCacheService cacheService;

    @Autowired
    private CookbookMapper cookbookMapper;

    @Autowired
    private CofInfoMapper cofInfoMapper;

    @Override
    public void run(String... args) throws Exception {
//        List<TbTest> list = tbTestService.selectAll();
//        cacheService.put("initData", list);
//        initData();
        initCof();
        logger.info("initRunner run complete...");
    }

    private void initCof() {
        List<CofInfo> cofInfos = cofInfoMapper.selectAll();
        for (CofInfo cofInfo : cofInfos) {
            cacheService.put("cofInfo_" + cofInfo.getName(), JSON.toJSONString(cofInfo));
        }
    }

    private void initData() {
        JSONReader jsonReader = null;
        try {
            jsonReader = new JSONReader(new FileReader(new File("D:\\local.food.json")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jsonReader.startArray();//---> [

        while (jsonReader.hasNext()) {
            //---> {"key":"value"}
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(jsonReader.readObject()));
            jsonObject.remove("id");
            Cookbook cookbook = JSON.parseObject(jsonObject.toJSONString(), Cookbook.class);
            cookbookMapper.insert(cookbook);
        }
        jsonReader.endArray();//---> ]
        jsonReader.close();
    }
}
