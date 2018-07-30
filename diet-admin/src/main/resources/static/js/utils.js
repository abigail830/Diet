function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数
    var res = window.location.search.substr(1).match(reg);
    if (res != null) {
        return decodeURIComponent(res[2]);
    }else{
        return '';
    }
}

function showSysError(){
    layer.alert("系统错误，给您带来的不便非常抱歉", {icon: 2});
}

function showTimeOutError(){
    layer.alert("请求超时，请重试", {icon: 0});
}

/**
 * 提示框
 * @param cont
 */
function showTip(cont){
    layer.alert(cont);
}

function showTipWithCb(cont,cb){
    layer.alert(cont, function(index){
        cb();
        layer.close(index);
    });
}

/**
 * 弹出loading框
 * @returns {*}
 */
function showLoading(){
    var loading = layer.load(0, {shade: [0.4, '#393D49'], offset: ['50%', '45%']});
    return loading;
}

/**
 * 关闭loading框
 * @param loading
 */
function closeLoading(loading){
    layer.close(loading);
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o){
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));

    }
    return fmt;
};
