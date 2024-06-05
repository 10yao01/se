const validateMobile = function (rule, value, callback) {
  let newValue = value.replace(/[^0-9]/gi, '')
  if (value !== newValue) {
    callback(new Error('请输入正确的手机号'))
  } else if (newValue.length !== 11) {
    callback(new Error('请输入正确的手机号'))
  } else {
    callback()
  }
}
export { validateMobile }
