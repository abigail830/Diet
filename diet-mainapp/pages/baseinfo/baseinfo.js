// pages/baseinfo/baseinfo.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
let commonArr = require("../../utils/commonArr.js")
var url = {
  listByOpenId: '/api/memberInfo/listByOpenId',
  save: '/api/memberInfo/save'
}
Page({
  /**
   * 页面的初始数据
   */
  data: {
    sexArr: [
      { name: '男', value: '1', checked: true },
      { name: '女', value: '2' }
    ],
    user: {
      id: '',
      wxUserExtId: null,
      wxUserId: null,
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
    professionArr: commonArr.professionArr,
    workTypeArr: commonArr.workTypeArr
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

    var professionArr = []
    var workTypeArr = []
    for (var o in commonArr.professionArr){
      professionArr.push(commonArr.professionArr[o])
    }
    for (var o in commonArr.workTypeArr) {
      workTypeArr.push(commonArr.workTypeArr[o])
    }
    this.setData({
      professionArr: professionArr,
      workTypeArr: workTypeArr
    })

    var openId = wx.getStorageSync('openid')
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
      
      var usrRes = resCb.data
      if (usrRes && usrRes.id) {
        usrRes.region = JSON.parse(usrRes.region)
        that.setData({
          user: usrRes
        })
        wx.setStorageSync('recomdUser', usrRes)
      }
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
  nameChg: function (e) {
    let usr = this.data.user
    usr.name = e.detail.value
    this.setData({
      user: usr
    })
  },
  usrSexChg: function (e) {
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
  cancleBtn: function () {
    wx.navigateBack({
      delta: 1
    })
  },
  addUser: function () {
    var usr = this.data.user
    if (!usr.name) {
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

    var that = this
    usr.wxUserId = wx.getStorageSync('wxUserInfo').id
    usr.wxUserExtId = wx.getStorageSync('wxUserExtId')
    request.request(url.save, JSON.stringify({
      user: usr
    }), function (resCb) {
      console.log(resCb)
      wx.showModal({
        content: resCb.desc,
        mask: true,
        showCancel: false
      })
      var usrRes = resCb.data
      if (usrRes) {
        usrRes.region = JSON.parse(usrRes.region)
        that.setData({
          user: usrRes
        })
        wx.setStorageSync('recomdUser', usrRes)
      }
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
    usr.profession = e.detail.value
    this.setData({
      user: usr
    })
  },
  workTypeChg: function (e) {
    let usr = this.data.user
    usr.workType = e.detail.value
    this.setData({
      user: usr
    })
  },
  bloodFatChg: function (e) {
    let usr = this.data.user
    usr.bloodFat = e.detail.value
    this.setData({
      user: usr
    })
  },
  bloodSugarChg: function (e) {
    let usr = this.data.user
    usr.bloodSugar = e.detail.value
    this.setData({
      user: usr
    })
  },
  bloodPressChg: function (e) {
    let usr = this.data.user
    usr.bloodPress = e.detail.value
    this.setData({
      user: usr
    })
  },
  uricAcidChg: function (e) {
    let usr = this.data.user
    usr.uricAcid = e.detail.value
    this.setData({
      user: usr
    })
  },
  kidneyChg: function (e) {
    let usr = this.data.user
    usr.kidney = e.detail.value
    this.setData({
      user: usr
    })
  },
  goRecomd: function(){
    if(!this.data.user.id){
      wx.showModal({
        content: "请先保存个人信息",
        mask: true,
        showCancel: false
      })
      return false
    }
    wx.redirectTo({
      url: '/pages/nutritionsugg/nutritionsugg'
    })
  }
})