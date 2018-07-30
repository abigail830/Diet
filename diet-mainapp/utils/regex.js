function phoneRegex(str) {
  var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
  if (!myreg.test(str)) {
    return false;
  } else {
    return true;
  }
}
function codeRegex(str) {
  var myreg = /^\d{6}$/;
  if (!myreg.test(str)) {
    return false;
  } else {
    return true;
  }
}
module.exports = {
  phoneRegex: phoneRegex,
  codeRegex: codeRegex
}