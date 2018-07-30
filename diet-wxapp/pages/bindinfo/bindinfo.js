// pages/bindinfo/bindinfo.js
let request = require("../../utils/request.js")
let regexUtil = require("../../utils/regex.js")
var url = {
  sendCode: '/api/wxUserInfo/sendCode',
  bindWxUser: '/api/wxUserInfo/bindWxUser'
}
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isInitComplete: false,
    phoneNumber: null,
    chkCode: null,
    smsBtnTxt: '发送验证码', //60s后重新发送
    smsBtnDisabled: false,
    openid:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.setStorageSync('redirectUrl', options.redirectUrl);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    if (wx.getStorageSync('isWxLogin')) {
      wx.switchTab({
        url: '/pages/dietitian/dietitian'
      })
    } else {
      this.setData({
        isInitComplete: true,
        openid: wx.getStorageSync('openid')
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  phoneInput: function(e) {
    this.setData({
      phoneNumber: e.detail.value
    })
  },
  codeInput: function(e) {
    this.setData({
      chkCode: e.detail.value
    })
  },
  sendSms: function() {
    let that = this

    if (!regexUtil.phoneRegex(this.data.phoneNumber)) {
      wx.showModal({
        content: '请输入正确手机号',
        mask: true,
        showCancel: false
      })
      return false
    }

    request.request(url.sendCode, JSON.stringify({
      phone: this.data.phoneNumber,
      openid: this.data.openid
    }), function (resCb) {
      console.log(resCb)
    })

    var sec = 6
    that.setData({
      smsBtnDisabled: true,
      smsBtnTxt: '60s后重新发送'
    })
    var interval = setInterval(function() {
      sec--;
      if (sec > 0) {
        that.setData({
          smsBtnTxt: sec + 's后重新发送'
        })
      } else {
        that.setData({
          smsBtnDisabled: false,
          smsBtnTxt: '发送验证码'
        })
        clearInterval(interval);
      }
    }.bind(this), 1000)
  },
  bindUser: function(e) {
    let that = this
    if (!regexUtil.phoneRegex(this.data.phoneNumber)) {
      wx.showModal({
        content: '请输入正确手机号',
        mask: true,
        showCancel: false
      })
      return false
    }

    if (!regexUtil.codeRegex(this.data.chkCode)) {
      wx.showModal({
        content: '请输入正确验证码',
        mask: true,
        showCancel: false
      })
      return false
    }

    request.request(url.bindWxUser, JSON.stringify({
      userInfo: e.detail.userInfo,
      phone: this.data.phoneNumber,
      chkCode: this.data.chkCode,
      openid: this.data.openid
    }), function (resCb) {
      console.log(resCb)
      if(resCb.code != 0){
        wx.showModal({
          content: resCb.desc,
          mask: true,
          showCancel: false
        })
        return false
      }
      wx.setStorageSync('isWxLogin', true)
      wx.setStorageSync('wxUserInfo', resCb.data)
      that.setData({
        isInitComplete: false
      })
      wx.navigateTo({
        url: wx.getStorageSync('redirectUrl')
      })
    })
  }
})