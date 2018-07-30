// pages/feedback/feedback.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
var app = getApp()
var url = {
  listByOpenId: '/api/feedback/listByOpenId'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: request.baseUrl + "/images/wxapp",
    feedbacks: []
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
  onShow: function() {
    var that = this
    var openId = wx.getStorageSync("openid")
    request.request(url.listByOpenId, JSON.stringify({
      openId: openId
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
      that.setData({
        feedbacks: resCb.data
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
  addFeedBack: function(){
    wx.setStorageSync('feedback', null)
    wx.navigateTo({
      url: '/pages/feedbackedit/feedbackedit?isEdit=1'
    })
  },
  feedBackDetail: function(e){
    var feedback = e.currentTarget.dataset.value
    wx.setStorageSync('feedback', feedback)
    wx.navigateTo({
      url: '/pages/feedbackedit/feedbackedit?isEdit=0'
    })
  }
})