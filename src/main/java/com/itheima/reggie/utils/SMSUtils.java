package com.itheima.reggie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.rmi.ServerException;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	public static void sendMessage(){
    DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tRWSxkpF3dxzCXxVq6T", "2IZTcEDHhRoux8ZeEy5jBAjUh1yQuS");
    /** use STS Token
     DefaultProfile profile = DefaultProfile.getProfile(
     "<your-region-id>",           // The region ID
     "<your-access-key-id>",       // The AccessKey ID of the RAM account
     "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
     "<your-sts-token>");          // STS Token
     **/

    IAcsClient client = new DefaultAcsClient(profile);

    SendSmsRequest request = new SendSmsRequest();
    request.setSignName("阿里云短信测试");
    request.setTemplateCode("SMS_154950909");
    request.setPhoneNumbers("18737955153");
    request.setTemplateParam("{\"code\":\"1234\"}");

    try {
      SendSmsResponse response = client.getAcsResponse(request);
      System.out.println(new Gson().toJson(response));
    } catch (ClientException e) {
      System.out.println("ErrCode:" + e.getErrCode());
      System.out.println("ErrMsg:" + e.getErrMsg());
      System.out.println("RequestId:" + e.getRequestId());
    }
	}

}
