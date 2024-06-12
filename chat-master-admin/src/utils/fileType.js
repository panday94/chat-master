/*
 * @Descripttion: 根据文件名或者格式判断文件类型  
 * @param: 文件类型 3 图片  4 音频  5 视频  6 文件 7 其他
 * @version: v1.0.0
 * @Author: yang
 * @Date: 2022-08-12 16:42:55
 * @LastEditors: yang
 * @LastEditTime: 2022-08-12 16:50:24
 */
export function resoveFileType(filePath, type) {
  let suff;
  if (filePath) {
    suff = filePath.split(".")[1];
  }
  if (type) {
    suff = type;
  }
  const IMGLIST = ["png", "jpg", "jpeg", "bmp", "gif"];
  if (IMGLIST.includes(suff)) {
    return 3;
  }

  const RADIOLIST = ["mp3", "wav", "wmv"];

  if (RADIOLIST.includes(suff)) {
    return 4;
  }

  const VIDEOLIST = ["mp4", "m2v", "mkv"];

  if (VIDEOLIST.includes(suff)) {
    return 5;
  }

  return 6
}
