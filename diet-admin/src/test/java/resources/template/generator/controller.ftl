package ${basePackageController};

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import ${basePackage}.core.BaseController;
import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackage}.message.ResponseMsg;
import ${basePackageService}.${modelNameUpperCamel}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* ${modelNameUpperCamel}Controller类
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller extends BaseController {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/insert")
    public ResponseMsg insert(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ResponseMsg responseMsg = new ResponseMsg();
        int result = ${modelNameLowerCamel}Service.insert(${modelNameLowerCamel});
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/deleteById/{id}")
    public ResponseMsg deleteById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer result = ${modelNameLowerCamel}Service.deleteByPrimaryKey(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/update")
    public ResponseMsg update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer result = ${modelNameLowerCamel}Service.updateByPrimaryKeySelective(${modelNameLowerCamel});
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/selectById/{id}")
    public ResponseMsg selectById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        ${modelNameUpperCamel} result = ${modelNameLowerCamel}Service.selectByPrimaryKey(id);
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
        PageInfo<${modelNameUpperCamel}> result = ${modelNameLowerCamel}Service.selectByExample(null, pageNum, pageSize);
        responseMsg.setData(result);
        return responseMsg;
    }
}