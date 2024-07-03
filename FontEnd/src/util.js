export const validatePassword = function(password) {
  if (typeof password !== 'string' || password === '') {
    return {
      success: false,
      message: '请输入新密码'
    }
  }
  const errors = []
  if (!/[a-z]/.test(password)) {
    errors.push('小写字母')
  }
  if (!/[0-9]/.test(password)) {
    errors.push('数字')
  }
 
  if (errors.length > 0) {
    return {
      success: false,
      message: `缺少${errors.join('，')}`
    }
  }
 
 
  if (password.length < 6) {
    return {
      success: false,
      message: '长度至少6位'
    }
  } else if (password.length > 12) {
    return {
      success: false,
      message: '长度至多12位'
    }
  }
  if (!/^(?=.*?[a-z])(?=.*?[0-9]).{6,12}$/.test(password)) {
    return {
      success: false,
      message: '校验失败'
    }
  }
  return {
    success: true
  }
}