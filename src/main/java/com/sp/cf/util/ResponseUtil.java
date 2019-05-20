package com.sp.cf.util;


import com.sp.cf.common.ResponseVO;
import com.sp.cf.constant.SystemErrorCodeEnum;

import java.util.Map;

/**
 * Created by alexlu
 * 2018/1/31.
 */
public class ResponseUtil {
    public static ResponseVO getSuccess(){
        return new ResponseVO(SystemErrorCodeEnum.SUCCESS.getCode(),"Succeed!");
    }
    public static ResponseVO getFailure(){
        return new ResponseVO(SystemErrorCodeEnum.SYSTEM_ERROR.getCode(),"Failed!");
    }

    public static ResponseVO getFailure(String msg){
        return new ResponseVO(SystemErrorCodeEnum.SYSTEM_ERROR.getCode(),msg);
    }

    public static ResponseVO getFailure(Integer errorCode, String msg){
        return new ResponseVO(errorCode, msg);
    }

    public static ResponseVO getFailure(Integer errorCode, Map<String, String> errMsgMap){
        StringBuilder stb = new StringBuilder();
        for (String errMsg : errMsgMap.values()){
            stb.append(errMsg);
        }
        return new ResponseVO(errorCode, stb.toString());
    }
}
