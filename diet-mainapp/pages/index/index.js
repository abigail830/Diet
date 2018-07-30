//index.js
let request = require("../../utils/request.js")
const app = getApp()
var url = {
  bindWxUser: '/api/wxUserInfo/bindWxUser',
  listByName: '/api/cofInfo/listByName'
}
Page({
  data: {
    baseUrl: request.baseUrl + "/images/wxmainapp",
    inputShowed: false,
    inputVal: "",
    cofInfos: []
  },
  showInput: function() {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function() {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function() {
    this.setData({
      inputVal: ""
    });
  },
  searchCofs: function(e) {
    var that = this
    if (e.detail.value) {
      request.request(url.listByName, JSON.stringify({
        name: e.detail.value
      }), function(resCb) {
        console.log(resCb)
        if (resCb.code != 0) {
          wx.showModal({
            content: resCb.desc,
            mask: true,
            showCancel: false
          })
          return false
        }
        that.setData({
          inputVal: e.detail.value,
          cofInfos: resCb.data
        });
      })
    } else {
      that.setData({
        inputVal: e.detail.value,
        cofInfos: []
      });
    }
  },
  showCofInfo: function(e){
    var idx = e.currentTarget.dataset.value
    var cofInfo = this.data.cofInfos[idx]
    wx.setStorageSync('cofInfo', cofInfo)
    wx.navigateTo({
      url: '/pages/cofinfo/cofinfo'
    })
  },
  jumpPage: function(e) {
    var that = this
    var isWxLogin = wx.getStorageSync('isWxLogin')
    var wxUserInfo = wx.getStorageSync('wxUserInfo')
    var redirectUrl = e.currentTarget.dataset.value
    if (wxUserInfo && isWxLogin) {
      wx.navigateTo({
        url: redirectUrl
      })
    } else {
      if (e.detail.userInfo) {
        console.log(e)
        var openid = wx.getStorageSync('openid')
        request.request(url.bindWxUser, JSON.stringify({
          userInfo: e.detail.userInfo,
          openid: openid,
          encryptedData: e.detail.encryptedData,
          iv: e.detail.iv
        }), function(resCb) {
          console.log(resCb)
          if (resCb.code != 0) {
            wx.showModal({
              content: resCb.desc,
              mask: true,
              showCancel: false
            })
            return false
          }
          wx.setStorageSync('isWxLogin', true)
          wx.setStorageSync('wxUserInfo', resCb.data.userInfo)
          wx.setStorageSync('wxUserExtId', resCb.data.wxUserExtId)
          that.setData({
            isInitComplete: false
          })
          wx.navigateTo({
            url: redirectUrl
          })
        })
      } else {
        wx.showModal({
          content: "跳转失败，请重新再试",
          mask: true,
          showCancel: false
        })
      }
    }
  }
});