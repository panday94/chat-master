// 获取当前日期
export function getCurrentDate() {
  const date = new Date()
  const day = date.getDate()
  const month = date.getMonth() + 1
  const year = date.getFullYear()
  return `${year}-${month}-${day}`
}

// 获取当前时间段
export function getGreeting() {  
  const now = new Date();  
  const hours = now.getHours();  
  
  if (hours < 12) {  
    return "上午好:sun_with_face:";  
  } else if (hours >= 12 && hours < 18) {  
    return "下午好:custard:";  
  } else {  
    return "晚上好:crescent_moon:";  
  }  
} 