package com.diet.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diet.admin.core.BaseController;
import com.diet.admin.entity.CofInfo;
import com.diet.admin.entity.Feedback;
import com.diet.admin.entity.MemberInfo;
import com.diet.admin.message.MsgCode;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.model.FeedBackDetailModel;
import com.diet.admin.service.ICofInfoService;
import com.diet.admin.service.IFeedbackService;
import com.diet.admin.service.IMemberInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
* TbFeedbackController类
* @author LiuYu
* @date 2018/07/16
*/
@RestController
@RequestMapping(BaseController.API + "/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IMemberInfoService memberInfoService;

    @Autowired
    private ICofInfoService cofInfoService;

    @PostMapping("/insert")
    public ResponseMsg insert(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        String openId = request.getString("openId");
        String feedback = request.getString("feedback");
        if (StringUtils.isBlank(openId) || StringUtils.isBlank(feedback)) {
            return new ResponseMsg(MsgCode.Param_Error);
        }
        MemberInfo memberInfo = memberInfoService.selectByOpenId(openId);
        if (memberInfo == null) {
            return responseMsg;
        }
        Feedback tbFeedback = JSON.parseObject(request.getJSONObject("feedback").toJSONString(), Feedback.class);
        for (FeedBackDetailModel detailModel : tbFeedback.getMealList()) {
            detailModel.getCofInfos().clear();
        }
        tbFeedback.setMealDetails(JSON.toJSONString(tbFeedback.getMealList()));
        tbFeedback.setMemberId(memberInfo.getId());
        tbFeedback.setCreateTime(new Date());
        tbFeedback.setUpdateTime(new Date());
        int result = feedbackService.insert(tbFeedback);
        responseMsg.setData(tbFeedback);
        return responseMsg;
    }

    @PostMapping("/deleteById/{id}")
    public ResponseMsg deleteById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer result = feedbackService.deleteByPrimaryKey(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/update")
    public ResponseMsg update(@RequestBody Feedback tbFeedback) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer result = feedbackService.updateByPrimaryKeySelective(tbFeedback);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/selectById/{id}")
    public ResponseMsg selectById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        Feedback result = feedbackService.selectByPrimaryKey(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/listByOpenId")
    public ResponseMsg listByOpenId(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        String openId = request.getString("openId");
        if (StringUtils.isBlank(openId)) {
            return new ResponseMsg(MsgCode.Param_Error);
        }
        MemberInfo memberInfo = memberInfoService.selectByOpenId(openId);
        if (memberInfo == null) {
            return responseMsg;
        }
        Example example = new Example(Feedback.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", memberInfo.getId());
        example.setOrderByClause(" mealDate desc, id desc ");
        List<Feedback> feedbacks = feedbackService.selectByExample(example);

        for (Feedback feedback : feedbacks) {
            List<FeedBackDetailModel> detailModels = JSONArray.parseArray(feedback.getMealDetails(), FeedBackDetailModel.class);
            for (FeedBackDetailModel detailModel : detailModels) {
                detailModel.setCofInfos(cofInfoService.selectByIds(StringUtils.join(detailModel.getCofIds(), ",")));
            }
            feedback.setMealList(detailModels);
        }
        responseMsg.setData(feedbacks);
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
        PageInfo<Feedback> result = feedbackService.selectByExample(null, pageNum, pageSize);
        responseMsg.setData(result);
        return responseMsg;
    }
}