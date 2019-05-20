package com.sp.cf.controller;


import com.sp.cf.common.ResponseVO;
import com.sp.cf.util.ResponseUtil;

import java.util.Map;

/**
 * Created by alexlu
 * 2018/1/31.
 */
public class BaseController {

    protected ResponseVO getSuccess(){
        return ResponseUtil.getSuccess();
    }

    protected ResponseVO getFromData(Object data){
        ResponseVO responseVO = getSuccess();
        responseVO.setData(data);
        return responseVO;
    }

    protected ResponseVO getFailure(){
        return ResponseUtil.getFailure();
    }

    protected ResponseVO getFailure(String msg){
        return ResponseUtil.getFailure(msg);
    }

    protected ResponseVO getFailure(Integer errorCode, String msg){
        return ResponseUtil.getFailure(errorCode, msg);
    }

    protected ResponseVO getFailureWithMap(Integer errorCode, Map<String, String> errMsgMap){
        return ResponseUtil.getFailure(errorCode, errMsgMap);
    }

    protected ResponseVO getResponse(Object data){
        ResponseVO responseVO =  getSuccess();
        responseVO.setData(data);
        return responseVO;
    }
}
