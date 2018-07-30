let professionArr = {
  0: '无职业',
  1: '国家机关、党群组织、企业、事业单位负责人',
  2: '专业技术人员',
  3: '办事人员和有关人员',
  4: '商业、服务业人员',
  5: '农、林、牧、渔、水利业生产人员',
  6: '生产、运输设备操作人员及有关人员',
  7: '军人',
  8: '不便分类的其他从业人员'
}
let workTypeArr = {
  0: '轻度体力劳动',
  1: '中度体力劳动',
  2: '重度体力劳动'
}
let foodTypeArr = ['全部分类', '蔬菜', '水果', '肉蛋豆类', '奶类', '谷薯类', '油']
let healthGrade = {
  1: "不能吃",
  2: "少吃",
  3: "无影响",
  4: "多吃"
}
module.exports = {
  professionArr: professionArr,
  workTypeArr: workTypeArr,
  foodTypeArr: foodTypeArr,
  healthGrade: healthGrade
}