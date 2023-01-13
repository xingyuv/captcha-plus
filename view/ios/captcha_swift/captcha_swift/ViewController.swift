//
//  ViewController.swift
//  captcha_swift
//
//  Created by kean_qi on 2020/4/29.
//  Copyright © 2020 kean_qi. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


    @IBAction func loginButtonClick(_ sender: UIButton) {
//        testDic()
        let type = CaptchaType(rawValue: sender.tag) ?? .puzzle
        CaptchaView.show(type) { (v) in
            print(v)
        }
    }
    //加密解密使用
    func testDic(){
        let d = ["x":5,"y":5]
        printLog(d)
        //序列化
        let pointEncode = ESConfig.jsonClickWordEncode(d)
//        let pointEncode = ESConfig.jsonClickWordEncode("123456")
        printLog("序列化： \(pointEncode)");
        //加密
        let pointJson = ESConfig.aesEncrypt(pointEncode)
        printLog("加密： \(pointJson)");

        //解密
        let pointDecodeJson = ESConfig.aesDncrypt(pointJson)
        printLog("解密： \(pointDecodeJson)");

        //反序列化
        let pointDncryptJson: [String: Any] = ESConfig.jsonClickWordDecode(pointEncode) as! [String : Any]
        printLog("反序列化： \(pointDncryptJson)");

    }
}
