// pages/dailyrecipe/dailyrecipe.js
let request = require("../../utils/request.js")
var wxCharts = require('../../utils/wxcharts-min.js')
var pieChart = null
var url = {
  listByOpenId: '/api/recipeInfo/listByOpenId',
  recomdRecipe: '/api/recipeInfo/recomdRecipe'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    memberRecipes: [],
    chartData: [],
    totalEnergies: 0,
    isFromIdx: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      isFromIdx: options.isFromIdx
    })
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
    var userItem = wx.getStorageSync('recomdUser')
    var cofStandard = wx.getStorageSync('cofStandard')
    var totalEnergies = wx.getStorageSync('totalEnergies')

    if (this.data.isFromIdx == 2) {
      request.request(url.recomdRecipe, JSON.stringify({
        user: userItem,
        cofStandard: cofStandard,
        totalEnergies: totalEnergies
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
        wx.setStorageSync('memberRecipes', resCb.data)

        var chartData = []
        for (var i = 0, iLen = resCb.data.length; i < iLen; i++) {
          var obj = {
            name: resCb.data[i].mealName,
            data: resCb.data[i].totalEnergy
          }
          chartData.push(obj)
        }
        that.setData({
          memberRecipes: resCb.data,
          chartData: chartData,
          totalEnergies: totalEnergies
        })
        that.initChart()
      })
    } else {
      var openId = wx.getStorageSync('openid')
      request.request(url.listByOpenId, JSON.stringify({
        openId: openId
      }), function(resCb) {
        console.log(resCb)
        if (resCb.code != 0) {
          wx.showModal({
            content: resCb.desc,
            mask: true,
            showCancel: false
          })
          if (resCb.code == -2){
            wx.redirectTo({
              url: '/pages/baseinfo/baseinfo',
            })
          }
          return false
        }
        wx.setStorageSync('memberRecipes', resCb.data)

        var chartData = [],
          totalEnergies = 0
        for (var i = 0, iLen = resCb.data.length; i < iLen; i++) {
          var obj = {
            name: resCb.data[i].mealName,
            data: resCb.data[i].totalEnergy
          }
          chartData.push(obj)
          totalEnergies += resCb.data[i].totalEnergy
        }
        that.setData({
          memberRecipes: resCb.data,
          chartData: chartData,
          totalEnergies: totalEnergies
        })
        that.initChart()
      })
    }
  },
  initChart: function() {
    var windowWidth = 320;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }

    pieChart = new wxCharts({
      animation: true,
      canvasId: 'pieCanvas',
      type: 'pie',
      series: this.data.chartData,
      width: 350,
      height: 200,
      dataLabel: true,
    });
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
  recipeDetail: function(e) {
    console.log(e)
    var val = e.currentTarget.dataset.value
    wx.setStorageSync('currMealTime', val)
    wx.navigateTo({
      url: '/pages/recipedetail/recipedetail'
    })
  }
})