// pages/recomdprocs/recomdprocs.js
let request = require("../../utils/request.js")
let util = require("../../utils/util.js")
let commonArr = require("../../utils/commonArr.js")
var url = {
  update: '/api/memberInfo/save'
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userItems: [],
    user:{
      id: '',
      userId: null,
      name: '',
      sex: 1,
      birthDate: '',
      height: '',
      weight: '',
      profession: '',
      checked: false,
      region: ['广东省', '广州市', '天河区'],
      workType:'',
      bloodFat: 3,
      bloodSugar: 3,
      bloodPress: 3,
      uricAcid: 3,
      kidney: 3
    },
    maxDate: util.format('yyyy-MM-dd', new Date()),
    professionArr: commonArr.professionArr,
    workTypeArr: commonArr.workTypeArr,
    usrIdx: 0,
    allUsrSize: 0,
    isShowData: false,
    isShowTip:false
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
    var userItems = wx.getStorageSync('recomdUsers')
    if(!userItems){
      userItems = []
    }
    this.setData({
      userItems: userItems,
      allUsrSize: userItems.length
    })
    if (userItems && userItems.length > 0){
      this.setData({
        user: userItems[0],
        isShowData: true
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
  professionChg: function (e) {
    let usr = this.data.user
    usr.profession = commonArr.professionArr[e.detail.value]
    this.setData({
      user: usr
    })
  },
  workTypeChg: function (e) {
    let usr = this.data.user
    usr.workType = commonArr.workTypeArr[e.detail.value]
    this.setData({
      user: usr
    })
  },
  bloodFatChg:function(e) {
    let usr = this.data.user
    usr.bloodFat.value = e.detail.value
    this.setData({
      user: usr
    })
  },
  cholesterinChg: function (e) {
    let usr = this.data.user
    usr.bloodFat.detail.cholesterin = e.detail.value
    this.setData({
      user: usr
    })
  },
  trilaurinChg: function (e) {
    let usr = this.data.user
    usr.bloodFat.detail.trilaurin = e.detail.value
    this.setData({
      user: usr
    })
  },
  chdlChg: function (e) {
    let usr = this.data.user
    usr.bloodFat.detail.chdl = e.detail.value
    this.setData({
      user: usr
    })
  },
  ildlChg: function (e) {
    let usr = this.data.user
    usr.bloodFat.detail.ildl = e.detail.value
    this.setData({
      user: usr
    })
  },
  bloodSugarChg: function (e) {
    let usr = this.data.user
    usr.bloodSugar.value = e.detail.value
    this.setData({
      user: usr
    })
  },
  bloodPressChg: function (e) {
    let usr = this.data.user
    usr.bloodPress.value = e.detail.value
    this.setData({
      user: usr
    })
  },
  systolicChg: function (e) {
    let usr = this.data.user
    usr.bloodPress.detail.systolic = e.detail.value
    this.setData({
      user: usr
    })
  },
  diastolicChg: function (e) {
    let usr = this.data.user
    usr.bloodPress.detail.diastolic = e.detail.value
    this.setData({
      user: usr
    })
  },
  uricAcidChg: function (e) {
    let usr = this.data.user
    usr.uricAcid.value = e.detail.value
    this.setData({
      user: usr
    })
  },
  kidneyChg: function(e){
    let usr = this.data.user
    usr.kidney.value = e.detail.value
    this.setData({
      user: usr
    })
  },
  nextStep: function(){
    var that = this
    var usr = this.data.user, nextIdx = this.data.usrIdx + 1
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
    if (!usr.workType) {
      wx.showModal({
        content: '请选择体力活动',
        mask: true,
        showCancel: false
      })
      return false;
    }

    if (usr.bloodFat.value == 2){
      if (!usr.bloodFat.detail.cholesterin){
        wx.showModal({
          content: '请输入总胆固醇',
          mask: true,
          showCancel: false
        })
        return false;
      }

      if (!usr.bloodFat.detail.trilaurin) {
        wx.showModal({
          content: '请输入甘油三酯',
          mask: true,
          showCancel: false
        })
        return false;
      }

      if (!usr.bloodFat.detail.chdl) {
        wx.showModal({
          content: '请输入高密度蛋白',
          mask: true,
          showCancel: false
        })
        return false;
      }

      if (!usr.bloodFat.detail.ildl) {
        wx.showModal({
          content: '请输入低密度蛋白',
          mask: true,
          showCancel: false
        })
        return false;
      }
    }

    if (usr.bloodSugar.value == 2) {
      if (!usr.bloodSugar.detail) {
        wx.showModal({
          content: '请输入血糖含量',
          mask: true,
          showCancel: false
        })
        return false;
      }
    }

    if (usr.bloodPress.value == 2) {
      if (!usr.bloodPress.detail.diastolic) {
        wx.showModal({
          content: '请输入舒张压',
          mask: true,
          showCancel: false
        })
        return false;
      }
      if (!usr.bloodPress.detail.systolic) {
        wx.showModal({
          content: '请输入收缩压',
          mask: true,
          showCancel: false
        })
        return false;
      }
    }

    if (usr.uricAcid.value == 2) {
      if (!usr.uricAcid.detail) {
        wx.showModal({
          content: '请输入尿酸含量',
          mask: true,
          showCancel: false
        })
        return false;
      }
    }

    request.request(url.update, JSON.stringify({
      user: usr
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

      if (nextIdx < that.data.allUsrSize) {
        usr = that.data.userItems[nextIdx]
        that.setData({
          user: usr,
          usrIdx: nextIdx
        })
      } else {
        wx.setStorageSync('recomdUsers', that.data.userItems)
        wx.navigateTo({
          url: '/pages/recomdsum/recomdsum'
        })
      }
    })
  },
  prevStep: function () {
    var usr = this.data.user, nextIdx = this.data.usrIdx - 1
    if (nextIdx >= 0) {
      usr = this.data.userItems[nextIdx]
      this.setData({
        user: usr,
        usrIdx: nextIdx
      })
    }
  },
  toogleShowTip:function(){
    this.setData({
      isShowTip: !this.data.isShowTip
    })
  }
})