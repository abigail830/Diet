// pages/foodlist/foodlist.js
let commonArr = require("../../utils/commonArr.js")
Page({

  /**
   * 页面的初始数据
   */
  data: {
    foodItems:[
      {
        id: 1,
        foodType: 2,
        name:'大黄米（黍）',
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
      },
    ],
    foodTypeArr: commonArr.foodTypeArr,
    selectedFoodArr: [],
    foodType: '全部分类',
    recomdInfo:{
    },
    canEdit: false
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
  delSelected: function(e){
    if(this.data.canEdit){
      console.log(e)
      var selectedFoodArr = this.data.selectedFoodArr, selectedArr = [], values = []
      for (var i = 0, lenI = selectedFoodArr.length; i < lenI; ++i) {
        if (selectedFoodArr[i].checked) {
          values.push(selectedFoodArr[i].id)
        } else {
          var newObj = JSON.parse(JSON.stringify(selectedFoodArr[i]))
          newObj.checked = false;
          selectedArr.push(newObj)
        }
      }
      var foods = this.data.foodItems
      for (var i = 0, lenI = foods.length; i < lenI; ++i) {
        for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
          if (foods[i].id == values[j]) {
            foods[i].checked = false;
            break;
          }
        }
      }

      this.setData({
        selectedFoodArr: selectedArr,
        foodItems: foods
      })

    }
    this.setData({
      canEdit: !this.data.canEdit
    })
  },
  foodTypeChg: function (e) {
    var foodType = commonArr.foodTypeArr[e.detail.value]
    this.setData({
      foodType: foodType
    })
  },
  radioChange:function(e) {
    console.log(e)
    var foods = this.data.foodItems, values = e.detail.value
    var selectedArr = []
    for (var i = 0, lenI = foods.length; i < lenI; ++i) {
      foods[i].checked = false;
      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (foods[i].id == values[j]) {
          foods[i].checked = true;
          var newObj = JSON.parse(JSON.stringify(foods[i]))
          newObj.checked = false;
          selectedArr.push(newObj)
          break;
        }
      }
    }

    this.setData({
      foodItems: foods,
      selectedFoodArr: selectedArr
    })
  },
  selectedChange: function (e) {
    var selectedFoodArr = this.data.selectedFoodArr, values = e.detail.value
    for (var i = 0, lenI = selectedFoodArr.length; i < lenI; ++i) {
      selectedFoodArr[i].checked = false;
      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (selectedFoodArr[i].id == values[j]) {
          selectedFoodArr[i].checked = true;
          break;
        }
      }
    }

    this.setData({
      selectedFoodArr: selectedFoodArr
    })
  },
  nextStep: function () {
    var selectedFoods = this.data.selectedFoodArr
    if(!selectedFoods || selectedFoods.length < 1){
      wx.showModal({
        content: '请选择食材',
        mask: true,
        showCancel: false
      })
      return false;
    }
    wx.setStorageSync('selectedFoods', selectedFoods)
    wx.navigateTo({
      url: '/pages/recomdresult/recomdresult'
    })
  }
})