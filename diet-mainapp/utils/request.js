let baseUrl = 'http://localhost:7091';
// let baseUrl = 'http://gammayu.club:9091';
function request(url, params, success, fail) {
  this.requestLoading(url, params, "", success, fail)
}
// 展示进度条的网络请求
// url:网络请求的url
// params:请求参数
// message:进度条的提示信息
// success:成功的回调函数
// fail：失败的回调
function requestLoading(url, params, message, successCB, failCB) {
  wx.showNavigationBarLoading()
  if (message != "") {
    wx.showToast({
      title: message,
      icon: 'loading',
      duration: 6000
    })
  } else {
    wx.showToast({
      title: '数据加载中',
      icon: 'loading',
      duration: 6000
    })
  }
  wx.request({
    url: baseUrl + url,
    data: params,
    header: {
      'Content-Type': 'application/json'
      //'content-type': 'application/x-www-form-urlencoded'
    },
    method: 'post',
    success: function (res) {
      //console.log(res.data)
      wx.hideNavigationBarLoading()
      wx.hideToast()
      if (res.statusCode == 200) {
        successCB(res.data)
      } else {
        if (failCB) {
          failCB(res)
        } else {
          wx.showModal({
            content: '请求失败，请联系管理员',
            mask: true,
            showCancel: false
          })
        }
      }

    },
    fail: function (res) {
      wx.hideNavigationBarLoading()
      wx.hideToast()
      if (failCB) {
        failCB(res)
      } else {
        wx.showModal({
          content: '请求失败，请联系管理员',
          mask: true,
          showCancel: false
        })
      }
    },
    complete: function (res) {

    },
  })
}
module.exports = {
  baseUrl: baseUrl,
  request: request,
  requestLoading: requestLoading
}