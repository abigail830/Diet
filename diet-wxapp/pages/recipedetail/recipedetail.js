// pages/recipedetail/recipedetail.js
let request = require("../../utils/request.js")
var url = {
  refreshRecipe: '/api/memberRecipe/refreshRecipe',
  removeRecipe: '/api/memberRecipe/removeRecipe',
  listRecipe: '/api/memberRecipe/listRecipe',
  addRecipe: '/api/memberRecipe/addRecipe'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    memberRecipes: [],
    currMealTime: 0,
    baseUrl: request.baseUrl + "/images",
    recipeArr: []
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
    var memberRecipes = wx.getStorageSync('memberRecipes')
    var currMealTime = wx.getStorageSync('currMealTime')
    
    this.setData({
      memberRecipes: memberRecipes,
      currMealTime: currMealTime
    })

    request.request(url.listRecipe, JSON.stringify({
      memberRecipeId: memberRecipes[currMealTime].id
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
        recipeArr: resCb.data
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
  currentChg: function(e){
    this.setData({
      currMealTime: e.detail.current
    })
  },
  refreshRecipe:function(e){
    var that = this
    var recipeId = e.currentTarget.dataset.value
    var memberRecipes = this.data.memberRecipes
    var memberRecipe = memberRecipes[this.data.currMealTime]

    request.request(url.refreshRecipe, JSON.stringify({
      memberRecipeId: memberRecipe.id,
      recipeId: recipeId
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

      memberRecipes[that.data.currMealTime] = resCb.data.memberRecipe
      that.setData({
        memberRecipes: memberRecipes,
        recipeArr: resCb.data.recipeInfoList
      })
    })
  },
  removeRecipe: function (e) {
    var that = this
    var recipeId = e.currentTarget.dataset.value
    var memberRecipes = this.data.memberRecipes
    var memberRecipe = memberRecipes[this.data.currMealTime]

    request.request(url.removeRecipe, JSON.stringify({
      memberRecipeId: memberRecipe.id,
      recipeId: recipeId
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

      memberRecipes[that.data.currMealTime] = resCb.data.memberRecipe
      that.setData({
        memberRecipes: memberRecipes,
        recipeArr: resCb.data.recipeInfoList
      })
    })
  },
  recipePick: function(e){
    console.log(e)
    if (this.data.recipeArr.length < 1){
      return false
    }
    var that = this
    var recipeId = this.data.recipeArr[e.detail.value].id
    var memberRecipes = this.data.memberRecipes
    var memberRecipe = memberRecipes[this.data.currMealTime]

    request.request(url.addRecipe, JSON.stringify({
      memberRecipeId: memberRecipe.id,
      recipeId: recipeId
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

      memberRecipes[that.data.currMealTime] = resCb.data.memberRecipe
      that.setData({
        memberRecipes: memberRecipes,
        recipeArr: resCb.data.recipeInfoList
      })
    })
  }
})