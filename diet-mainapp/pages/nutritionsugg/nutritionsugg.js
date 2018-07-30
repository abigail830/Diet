// pages/nutritionsugg/nutritionsugg.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
var url = {
  calculateEnergy: '/api/scalerStandard/calculateEnergy',
  saveRecomdPlan: '/api/nutritionRecomdPlan/saveRecomdPlan',
  recomdPlanList: '/api/nutritionRecomdPlan/list'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userItems: [],
    totalEnergies: 0,
    totalProteins: 0,
    standard: {},
    ratio: 1,
    initComplete: false
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
    var userItem = wx.getStorageSync('recomdUser')
    if (userItem) {
      var userItems = []
      userItems.push(userItem)
      request.request(url.calculateEnergy, JSON.stringify({
        users: userItems
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

        wx.setStorageSync('cofStandard', resCb.data.standard)
        wx.setStorageSync('totalEnergies', resCb.data.totalEnergies)
        that.initStandard(resCb.data.standard, resCb.data.ratio);
        that.setData({
          userItems: userItems,
          totalEnergies: resCb.data.totalEnergies,
          totalProteins: resCb.data.totalProteins,
          ratio: resCb.data.ratio,
          initComplete: true
        })
        
      })
    } else {
      wx.showModal({
        content: "请先保存个人基础信息",
        mask: true,
        showCancel: false,
        success: function (res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/baseinfo/baseinfo'
            })
          }
        }
      })
    }
  },
  initStandard: function(standardTmp, ratio) {
    standardTmp.milk = ((standardTmp.milk || 0) * ratio).toFixed(0)
    standardTmp.egg = ((standardTmp.egg || 0) * ratio).toFixed(0)
    standardTmp.eggWhite = ((standardTmp.eggWhite || 0) * ratio).toFixed(0)
    standardTmp.lean = ((standardTmp.lean || 0) * ratio).toFixed(0)
    standardTmp.tofu = ((standardTmp.tofu || 0) * ratio).toFixed(0)
    standardTmp.vegetable = ((standardTmp.vegetable || 0) * ratio).toFixed(0)
    standardTmp.leafyVegetable = ((standardTmp.leafyVegetable || 0) * ratio).toFixed(0)
    standardTmp.melon = ((standardTmp.melon || 0) * ratio).toFixed(0)
    standardTmp.homonemeae = ((standardTmp.homonemeae || 0) * ratio).toFixed(0)
    standardTmp.fruit = ((standardTmp.fruit || 0) * ratio).toFixed(0)
    standardTmp.rice = ((standardTmp.rice || 0) * ratio).toFixed(0)
    standardTmp.tuber = ((standardTmp.tuber || 0) * ratio).toFixed(0)
    standardTmp.amyloid = ((standardTmp.amyloid || 0) * ratio).toFixed(0)
    standardTmp.oil = ((standardTmp.oil || 0) * ratio).toFixed(0)
    standardTmp.salt = ((standardTmp.salt || 0) * ratio).toFixed(0)

    standardTmp.va = ((standardTmp.va || 0) * ratio).toFixed(0)
    standardTmp.vb = ((standardTmp.vb || 0) * ratio).toFixed(0)
    standardTmp.vc = ((standardTmp.vc || 0) * ratio).toFixed(0)
    standardTmp.vd = ((standardTmp.vd || 0) * ratio).toFixed(0)
    standardTmp.ve = ((standardTmp.ve || 0) * ratio).toFixed(0)
    standardTmp.phosphor = ((standardTmp.phosphor || 0) * ratio).toFixed(0)
    standardTmp.na = ((standardTmp.na || 0) * ratio).toFixed(0)
    standardTmp.ca = ((standardTmp.ca || 0) * ratio).toFixed(0)
    standardTmp.fe = ((standardTmp.fe || 0) * ratio).toFixed(0)

    this.setData({
      standard: standardTmp
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
  recomdRecipe: function(){
    var that = this
    var userItem = wx.getStorageSync('recomdUser')
    request.request(url.saveRecomdPlan, JSON.stringify({
      standard: that.data.standard,
      memberId: userItem.id
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

      wx.redirectTo({
        url: '/pages/dailyrecipe/dailyrecipe?isFromIdx=2'
      })
    })
  }
})