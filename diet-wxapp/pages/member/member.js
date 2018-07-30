// pages/member/member.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
let commonArr = require("../../utils/commonArr.js")
var url = {
  insert: '/api/memberInfo/insert'
}
Page({
  /**
   * 页面的初始数据
   */
  data: {
    sexArr:[
      {name:'男', value:'1', checked: true},
      { name: '女', value: '2'}
    ],
    user:{
      id: '',
      userId: null,
      name: '',
      sex: 1,
      birthDate: '2000-01-01',
      height: '175',
      weight: '65',
      profession: '无职业',
      checked: false,
      region: ['广东省', '广州市', '天河区'],
      workType: '',
      bloodFat: 3,
      bloodSugar: 3,
      bloodPress: 3,
      uricAcid: 3,
      kidney: 3
    },
    maxDate: util.format('yyyy-MM-dd', new Date()),
    professionArr: commonArr.professionArr
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
    var wxUserInfo = wx.getStorageSync('wxUserInfo')
    var user = this.data.user
    user.userId = wxUserInfo.id
    if(wxUserInfo){
      this.setData({
        user: user
      })
    }
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
  nameChg: function(e){
    let usr = this.data.user
    usr.name = e.detail.value
    this.setData({
      user: usr
    })
  },
  usrSexChg: function(e){
    let usr = this.data.user
    usr.sex = e.detail.value
    var radioItems = this.data.sexArr;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].value == e.detail.value;
    }
    this.setData({
      user: usr,
      sexArr: radioItems
    })
  },
  birthDateChg: function (e) {
    let usr = this.data.user
    usr.birthDate = e.detail.value
    this.setData({
      user: usr
    })
  },
  heightChg: function (e) {
    let usr = this.data.user
    usr.height = e.detail.value
    this.setData({
      user: usr
    })
  },
  weightChg: function (e) {
    let usr = this.data.user
    usr.weight = e.detail.value
    this.setData({
      user: usr
    })
  },
  cancleBtn: function() {
    wx.navigateBack({
      delta: 1
    })
  },
  addUser: function() {
    let userItems = wx.getStorageSync('userItems')
    if (!userItems){
      userItems = []
    }
    var usr = this.data.user
    if(!usr.name){
      wx.showModal({
        content: '请输入姓名',
        mask: true,
        showCancel: false
      })
      return false;
    }
    if (!usr.birthDate) {
      wx.showModal({
        content: '请选择出生日期',
        mask: true,
        showCancel: false
      })
      return false;
    }
    if (!usr.height) {
      wx.showModal({
        content: '请输入身高',
        mask: true,
        showCancel: false
      })
      return false;
    }
    if (!usr.weight) {
      wx.showModal({
        content: '请输入体重',
        mask: true,
        showCancel: false
      })
      return false;
    }
    if (!usr.profession) {
      wx.showModal({
        content: '请选择职业',
        mask: true,
        showCancel: false
      })
      return false;
    }

    request.request(url.insert, JSON.stringify({
      user: this.data.user
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

      userItems.push(usr)
      wx.setStorageSync('userItems', userItems)
      wx.redirectTo({
        url: '/pages/recommend/recommend'
      })
    })
  },
  regionChg: function (e) {
    let usr = this.data.user
    usr.region = e.detail.value
    this.setData({
      user: usr
    })
  },
  professionChg: function (e) {
    let usr = this.data.user
    usr.profession = commonArr.professionArr[e.detail.value]
    this.setData({
      user: usr
    })
  }
})