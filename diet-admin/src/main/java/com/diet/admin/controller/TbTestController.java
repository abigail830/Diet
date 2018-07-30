package com.diet.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.TbTest;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.service.TbTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* TbTestController类
* @author LiuYu
* @date 2018/04/27
*/
@RestController
@RequestMapping("/tbTest")
public class TbTestController extends BaseController {

    @Autowired
    private TbTestService tbTestService;

    @PostMapping("/insert")
    public ResponseMsg insert(@RequestBody TbTest tbTest) {
        ResponseMsg responseMsg = new ResponseMsg();
        int result = tbTestService.insert(tbTest);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/deleteById/{id}")
    public ResponseMsg deleteById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer result = tbTestService.deleteByPrimaryKey(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/update")
    public ResponseMsg update(@RequestBody TbTest tbTest) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer result = tbTestService.updateByPrimaryKeySelective(tbTest);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/selectById/{id}")
    public ResponseMsg selectById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        TbTest result = tbTestService.selectByPrimaryKey(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    /**
    * 分页查询，pageNum 页码，pageSize 每页条数
    * @Reutrn ResponseMsg
    */
    @PostMapping("/list")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer pageNum = request.getInteger("pageNum");
        Integer pageSize = request.getInteger("pageSize");
        PageInfo<TbTest> result = tbTestService.selectByExample(null, pageNum, pageSize);
        responseMsg.setData(result);
        return responseMsg;
    }
}