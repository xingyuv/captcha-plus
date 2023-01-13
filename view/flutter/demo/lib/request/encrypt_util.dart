import 'dart:convert';

import 'package:steel_crypt/steel_crypt.dart';
//import 'package:encrypt/encrypt.dart';

class EncryptUtil {

  ///aes加密
  /// [key]AesCrypt加密key
  /// [content] 需要加密的内容字符串
  static String aesEncode({String key,String content}) {
    var aesEncrypter = AesCrypt(key ,'ecb', 'pkcs7');
    return aesEncrypter.encrypt(content);
  }

  ///aes解密
  /// [key]aes解密key
  /// [content] 需要加密的内容字符串
  static String aesDecode({String key,String content}) {
    var aesEncrypter = AesCrypt(key, 'ecb', 'pkcs7');
    return aesEncrypter.decrypt(content);
  }





}