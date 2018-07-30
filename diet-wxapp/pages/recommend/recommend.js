// pages/recommend/recommend.js
let request = require("../../utils/request.js")
var url = {
  list: '/api/memberInfo/list'
}
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isShowRecomd: true,
    canRecomd: false,
    userItems: [],
    recomdUsers: [],
    baseUrl: request.baseUrl + "/images/wxapp"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this
    var wxUserInfo = wx.getStorageSync('wxUserInfo')

    request.request(url.list, JSON.stringify({
      userId: wxUserInfo.id
    }), function (resCb) {
      console.log(resCb)
      if (resCb.code != 0) {
        wx.showModal({
          content: resCb.desc,
          mask: true,
          showCancel: false
        })
        return false
      }

      var userItems = resCb.data;
      for(i=0,iLen=userItems.length; i<iLen; i++){
        userItems[i].region = JSON.parse(userItems[i].region)
        userItems[i].bloodFat = JSON.parse(userItems[i].bloodFat)
        userItems[i].bloodSugar = JSON.parse(userItems[i].bloodSugar)
        userItems[i].bloodPress = JSON.parse(userItems[i].bloodPress)
        userItems[i].uricAcid = JSON.parse(userItems[i].uricAcid)
      }

      that.setData({
        userItems: userItems,
        recomdUsers: [],
        canRecomd: false
      })
    })
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
  chgTab: function(e) {
    this.setData({
      isShowRecomd: e.currentTarget.dataset.tabid == 'true'
    })
  },
  addMember: function() {
    wx.navigateTo({
      url: '/pages/member/member'
    })
  },
  chkboxChg: function(e) {
    var userItems = this.data.userItems,
      values = e.detail.value
    var recomdUsers = [];
    for (var i = 0, lenI = userItems.length; i < lenI; ++i) {
      userItems[i].checked = false;
      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (userItems[i].id == values[j]) {
          userItems[i].checked = true;
          recomdUsers.push(userItems[i])
          break;
        }
      }
    }
    this.setData({
      userItems: userItems,
      recomdUsers: recomdUsers,
      canRecomd: recomdUsers.length > 0
    });
  },
  goRecomd: function() {
    if (this.data.recomdUsers.length > 0) {
      wx.setStorageSync('recomdUsers', this.data.recomdUsers)
      wx.navigateTo({
        url: '/pages/recomdprocs/recomdprocs'
      })
    } else {
      wx.showModal({
        content: '请至少选择一个成员',
        mask: true,
        showCancel: false
      })
    }
  }
})