const CryptoJS = require('./aes.js'); //引用AES源码js
/**
 * aes 解密方法
 */
function AesDecrypt(word, key) {
  let keyHex = CryptoJS.enc.Utf8.parse(key); //十六位十六进制数作为秘钥
  let decrypt = CryptoJS.AES.decrypt(word, keyHex, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
  return decryptedStr.toString();
}
/**
 * aes 加密方法
 */
function AesEncrypt(word, key) {
  let keyHex = CryptoJS.enc.Utf8.parse(key); //十六位十六进制数作为秘钥
  let srcs = CryptoJS.enc.Utf8.parse(word);
  let encrypted = CryptoJS.AES.encrypt(srcs, keyHex, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  return encrypted.toString();
}

/**
 * base64 加密方法
 */
function Base64Encode(val) {
  let str = CryptoJS.enc.Utf8.parse(val);
  let base64 = CryptoJS.enc.Base64.stringify(str);
  return base64;
}

/**
 * base64 解密方法
 */
function Base64Decode(val) {
  let words = CryptoJS.enc.Base64.parse(val);
  return words.toString(CryptoJS.enc.Utf8);
}


//暴露接口
module.exports = {
  AesEncrypt,
  AesDecrypt,
  Base64Encode,
  Base64Decode
}