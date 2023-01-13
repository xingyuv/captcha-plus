//
//  ESConfig.swift
//  captcha_swift
//
//  Created by kean_qi on 2020/4/30.
//  Copyright © 2020 kean_qi. All rights reserved.
//

import UIKit
import CryptoSwift
class ESConfig: NSObject {
    ///加密
    ///[req] 请求数据 序列化之后的字符串
    ///[secretKey]  加密key
    class func aesEncrypt(_ req: String, _ secretKey: String = "XwKsGlMcdPMEhR1B") -> String{
        printLog("aesEncrypt :   \(req)")
        var encryptedBase64 = ""
        do {
            //使用AES-128-ECB加密模式
            let aes = try AES(key: secretKey.bytes, blockMode: ECB(), padding: .pkcs7)
            //开始加密
            let encrypted = try aes.encrypt(req.bytes)
            encryptedBase64 = encrypted.toBase64() ?? "" //将加密结果转成base64形式
        } catch { }
        
        return encryptedBase64;
    }
    
    ///解密
    ///[req] 请求数据 序列化之后的字符串
    ///[secretKey]  加密key
    class func aesDncrypt(_ encodeStr: String, _ secretKey: String = "XwKsGlMcdPMEhR1B") -> String{
        var decryptStr = ""
        do {
            //使用AES-128-ECB加密模式
            let aes = try AES(key: secretKey.bytes, blockMode: ECB(), padding: .pkcs7)
            //开始解密1（从加密后的字符数组解密）
            //开始解密2（从加密后的base64字符串解密）
            decryptStr = try encodeStr.decryptBase64ToString(cipher: aes)
        } catch {
            printLog("err")
        }
        return decryptStr
    }
    
    //序列化
    class func jsonClickWordEncode(_ reqList: Any) -> String{
        var paramsStr = ""
        do {
            let newData = try JSONSerialization.data(withJSONObject: reqList, options: .fragmentsAllowed)
            paramsStr = String(data: newData as Data, encoding: .utf8)!
        }catch{}
        return paramsStr
    }
    
    //反序列化
    class func jsonClickWordDecode(_ re: String) -> Any{
        do {
            guard let jsonObject = re.data(using: .utf8) else { return "" }
            let dict = try JSONSerialization.jsonObject(with: jsonObject, options: .mutableContainers)
            return dict
        }catch{}
        return ""
    }
    
    
    
    //captcha序列化
    class func jsonEncode(_ req: CaptchaRequestModel) -> String{
        var paramsStr = ""
        do {
            let params = req
            print("params -- \(params)")
            let d = try JSONEncoder().encode(params)
            paramsStr = String(data: d, encoding: .utf8)!
        }catch{}
        return paramsStr
    }
}

struct CaptchaRequestModel: Codable {
    var x: CGFloat
    var y: CGFloat
}

