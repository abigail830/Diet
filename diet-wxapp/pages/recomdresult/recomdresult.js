// pages/recomdresult/recomdresult.js
var wxCharts = require('../../utils/wxcharts-min.js')
var pieChart = null
Page({

  /**
   * 页面的初始数据
   */
  data: {
    foodItems: [{
      id: 1,
      foodType: 2,
      name: '大黄米（黍）',
      checked: false
    }, {
      id: 2,
      foodType: 2,
      name: '稻米（大米）',
      checked: false
    }, {
      id: 3,
      foodType: 2,
      name: '花生油',
      checked: false
    }, {
      id: 4,
      foodType: 2,
      name: '豆腐花',
      checked: false
    }, {
      id: 5,
      foodType: 2,
      name: '甘薯粉（地瓜粉）',
      checked: false
    }, {
      id: 6,
      foodType: 2,
      name: '鸡蛋',
      checked: false
    }, {
      id: 7,
      foodType: 2,
      name: '猪肉',
      checked: false
    }, {
      id: 8,
      foodType: 2,
      name: '苹果',
      checked: false
    }, ],
    selectedFoods: []
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
    this.setData({
        selectedFoods: wx.getStorageSync('selectedFoods')
    })


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
      series: [{
        name: '大黄米（黍）',
        data: 15,
      }, {
          name: '苹果',
        data: 35,
      }, {
          name: '稻米（大米）',
        data: 78,
      }, {
          name: '甘薯粉（地瓜粉）',
        data: 63,
      }],
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

  }
})