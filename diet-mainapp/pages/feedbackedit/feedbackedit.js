// pages/feedbackedit/feedbackedit.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
var app = getApp()
var url = {
  listByName: '/api/cofInfo/listByName',
  feedbackInsert: '/api/feedback/insert',
  listByOpenId: '/api/feedback/listByOpenId'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: request.baseUrl + "/images/wxapp",
    maxDate: util.format('yyyy-MM-dd', new Date()),
    inputShowed: false,
    inputVal: "",
    cofInfos: [],
    currentMealTime: 0,
    showCofQuery: false,
    feedback: {
      id: null,
      mealDesc: '',
      mealDate: '',
      mealList: [
        // {
        //   mealTime: '',
        //   cofIds:[],
        //   cofInfos:[]
        // }
      ]
    },
    isEdit: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      isEdit: options.isEdit
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
    var feedback = wx.getStorageSync('feedback')
    if (!feedback) {
      feedback = {
        id: null,
        mealDesc: '',
        mealDate: '',
        mealList: []
      }
    }
    this.setData({
      feedback: feedback
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
  mealDescChg: function(e) {
    var feedback = this.data.feedback
    feedback.mealDesc = e.detail.value
    this.setData({
      feedback: feedback
    })
  },
  mealDateChg: function(e) {
    var feedback = this.data.feedback
    feedback.mealDate = e.detail.value
    this.setData({
      feedback: feedback
    })
  },
  chgCurrMealTime: function(e) {
    this.setData({
      currentMealTime: e.currentTarget.dataset.value
    })
  },
  addMealTime: function(e) {
    console.log(e)
    var feedback = this.data.feedback
    var isExists = false
    for (var i = 0, iLen = feedback.mealList.length; i < iLen; i++) {
      if (feedback.mealList[i].mealTime == e.detail.value) {
        isExists = true
        break;
      }
    }
    if (!isExists) {
      var mealTimeObj = {
        mealTime: e.detail.value,
        cofIds: [],
        cofInfos: [],
        cofVals: []
      }
      this.sortMealTime(mealTimeObj)
      // feedback.mealList.push(mealTimeObj)
      // this.setData({
      //   feedback: feedback        
      // })
    }
  },
  removeMealTime: function() {
    var feedback = this.data.feedback
    if (feedback.mealList && feedback.mealList.length > 0) {
      var curr = this.data.currentMealTime
      feedback.mealList.splice(this.data.currentMealTime, 1)
      if (curr >= feedback.mealList.length){
        curr = feedback.mealList.length - 1
      }
      if(curr < 0){
        curr = 0
      }
      this.setData({
        feedback: feedback,
        currentMealTime: curr
      })
    }
  },
  sortMealTime: function(newMealTime) {
    var feedback = this.data.feedback
    var mealList = feedback.mealList
    var tmpN = parseInt(newMealTime.mealTime.replace(':', ''))
    var currIdx = this.data.currentMealTime
    if (mealList.length < 1) {
      mealList.push(newMealTime)
    } else {
      var isNotAdd = true
      for (var i = 0, iLen = mealList.length; i < iLen; i++) {
        var tmpM = parseInt(mealList[i].mealTime.replace(':', ''))
        if (tmpN < tmpM) {
          mealList.splice(i, 0, newMealTime)
          currIdx = i
          isNotAdd = false
          break
        }
      }
      if (isNotAdd){
        currIdx = mealList.length
        mealList.push(newMealTime)
      }
    }
    this.setData({
      feedback: feedback,
      currentMealTime: currIdx
    })
  },
  showAddCofPage: function() {
    this.setData({
      showCofQuery: true
    })
  },
  addCof: function(e) {
    var cof = e.currentTarget.dataset.value
    var feedback = this.data.feedback
    var cofList = feedback.mealList[this.data.currentMealTime].cofInfos
    var cofIds = feedback.mealList[this.data.currentMealTime].cofIds
    var isExists = false
    for (var i = 0, iLen = cofList.length; i < iLen; i++) {
      if (cofList[i].id == cof.id) {
        isExists = true
        break;
      }
    }
    if (!isExists) {
      cofList.push(cof)
      cofIds.push(cof.id)
      this.setData({
        feedback: feedback
      })
    }
    this.setData({
      showCofQuery: false,
      inputVal: "",
      cofInfos: []
    })
  },
  clearInput: function() {
    this.setData({
      inputVal: "",
      cofInfos: []
    })
  },
  hideCofQuery: function() {
    this.setData({
      showCofQuery: false,
      inputVal: "",
      cofInfos: []
    })
  },
  amountChg: function(e) {
    console.log(e)
    var val = e.currentTarget.dataset.value
    var feedback = this.data.feedback
    var cofVals = feedback.mealList[this.data.currentMealTime].cofVals
    cofVals[val] = e.detail.value
    this.setData({
      feedback: feedback
    })
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
        })
      })
    } else {
      that.setData({
        inputVal: e.detail.value,
        cofInfos: []
      });
    }
  },
  isCanAddTime: function() {
    var feedback = this.data.feedback
    if (feedback.mealList[this.data.currentMealTime] && feedback.mealList[this.data.currentMealTime].cofIds.length < 1) {
      wx.showModal({
        content: "请添加食材",
        mask: true,
        showCancel: false
      })
      return false
    }
  },
  saveFeedBack: function(e) {
    var that = this
    var feedback = this.data.feedback
    var openId = wx.getStorageSync("openid")
    if (feedback.mealList[this.data.currentMealTime].cofIds.length < 1) {
      wx.showModal({
        content: "请添加食材",
        mask: true,
        showCancel: false
      })
      return false
    }
    request.request(url.feedbackInsert, JSON.stringify({
      feedback: feedback,
      openId: openId
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
      })
      wx.navigateBack({
        delta: 1
      })
    })
  }
})