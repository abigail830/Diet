// pages/recomdsum/recomdsum.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
var url = {
  calculateEnergy: '/api/scalerStandard/calculateEnergy',
  saveRecomdPlan: '/api/nutritionRecomdPlan/saveRecomdPlan'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userItems: [],
    totalEnergies: 0,
    totalProteins: 0,
    standard:{},
    ratio: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this
    var userItems = wx.getStorageSync('recomdUsers')
    if (!userItems) {
      userItems = []
    }

    request.request(url.calculateEnergy, JSON.stringify({
      users: userItems
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

      var standardTmp = resCb.data.standard
      var ratio = resCb.data.ratio

      standardTmp.targetEnergy = (standardTmp.targetEnergy * ratio).toFixed(0)
      standardTmp.milk = (standardTmp.milk * ratio).toFixed(0)
      standardTmp.egg = (standardTmp.egg * ratio).toFixed(0)
      standardTmp.lean = (standardTmp.lean * ratio).toFixed(0)
      standardTmp.tofu = (standardTmp.tofu * ratio).toFixed(0)
      standardTmp.vegetable = (standardTmp.vegetable * ratio).toFixed(0)
      standardTmp.leafyVegetable = (standardTmp.leafyVegetable * ratio).toFixed(0)
      standardTmp.melon = (standardTmp.melon * ratio).toFixed(0)
      standardTmp.homonemeae = (standardTmp.homonemeae * ratio).toFixed(0)
      standardTmp.fruit = (standardTmp.fruit * ratio).toFixed(0)
      standardTmp.rice = (standardTmp.rice * ratio).toFixed(0)
      standardTmp.tuber = (standardTmp.tuber * ratio).toFixed(0)
      standardTmp.amyloid = (standardTmp.amyloid * ratio).toFixed(0)
      standardTmp.oil = (standardTmp.oil * ratio).toFixed(0)

      standardTmp.va = ((standardTmp.va || 0) * ratio).toFixed(0)
      standardTmp.vb = ((standardTmp.vb || 0) * ratio).toFixed(0)
      standardTmp.vc = ((standardTmp.vc || 0) * ratio).toFixed(0)
      standardTmp.vd = ((standardTmp.vd || 0) * ratio).toFixed(0)
      standardTmp.ve = ((standardTmp.ve || 0) * ratio).toFixed(0)
      standardTmp.phosphor = ((standardTmp.phosphor || 0) * ratio).toFixed(0)
      standardTmp.na = ((standardTmp.na || 0) * ratio).toFixed(0)
      standardTmp.ca = ((standardTmp.ca || 0) * ratio).toFixed(0)
      standardTmp.fe = ((standardTmp.fe || 0) * ratio).toFixed(0)

      wx.setStorageSync('cofStandard', resCb.data.standard)
      wx.setStorageSync('totalEnergies', resCb.data.totalEnergies)
      that.setData({
        userItems: userItems,
        totalEnergies: resCb.data.totalEnergies,
        totalProteins: resCb.data.totalProteins,
        standard: standardTmp,
        ratio: resCb.data.ratio
      })
    })

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  nextStep: function () {
    var that = this
    request.request(url.saveRecomdPlan, JSON.stringify({
      standard: that.data.standard,
      memberId: that.data.userItems[0].id
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

      wx.navigateTo({
        url: '/pages/dailyrecipe/dailyrecipe'
      })
    })
    
  },
})