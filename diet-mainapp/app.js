//app.js
var request = require('./utils/request.js')
var url = {
  wxLogin: '/api/wxUserInfo/wxLogin'
}
App({
  onLaunch: function () {
    // wx.clearStorageSync();
    var that = this
    var openid = wx.getStorageSync('openid')
    var isWxLogin = wx.getStorageSync('isWxLogin')
    if (!isWxLogin) {
      // 登录
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
          request.request(url.wxLogin, JSON.stringify({
            code: res.code
          }), function (resCb) {
            wx.setStorageSync('openid', resCb.data.openid)
            wx.setStorageSync('isWxLogin', resCb.data.isWxLogin)
            wx.setStorageSync('recomdUser', resCb.data.memberInfo)
            that.globalData.openid = resCb.data.openid
            that.globalData.isWxLogin = resCb.data.isWxLogin
          })
        }
      })
    } else {
      // 获取用户信息
      wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
            wx.getUserInfo({
              success: res => {
                // 可以将 res 发送给后台解码出 unionId
                this.globalData.userInfo = res.userInfo

                // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                // 所以此处加入 callback 以防止这种情况
                if (this.userInfoReadyCallback) {
                  this.userInfoReadyCallback(res)
                }
              }
            })
          }
        }
      })
      that.globalData.openid = openid
      that.globalData.isWxLogin = isWxLogin
    }
  },
  globalData: {
    userInfo: null,
    openid: '',
    isWxLogin: false
  }
})