// 加密数据函数 工具crypto.js 文件工具
/**
 * @word 要加密的内容
 * @keyWord String  服务器随机返回的关键字
 *  */
import {AES, enc, mode, pad} from 'crypto-js';

function aesEncrypt(word,keyWord){
  var key = enc.Utf8.parse(keyWord);
  var srcs = enc.Utf8.parse(word);
  var encrypted = AES.encrypt(srcs, key, {mode:mode.ECB,padding:pad.Pkcs7});
  return encrypted.toString();
}

export default aesEncrypt
