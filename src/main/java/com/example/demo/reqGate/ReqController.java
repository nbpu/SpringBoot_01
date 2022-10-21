package com.example.demo.reqGate;

import com.example.demo.response.ResponseBean;
import com.example.demo.response.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReqController extends ApplicationObjectSupport {
    private static Logger logger = LoggerFactory.getLogger(ReqController.class);
    @ResponseBody
    @PostMapping(value = "invoke/{service}/{method}")
    public Map<String,Object> doInvoke(@PathVariable String service, @PathVariable String method, HttpServletRequest request , @RequestBody Map<String,Object> initParam){
        try {
            RequestData requestData = buildRequestData(initParam);
            requestData.setService(service);
            requestData.setMethod(method);
            Map<String,Object> responseData = callService(requestData);
            return responseData;
        }catch (Exception e){
            logger.error("-----------doInvoke Exception>>>error",e);
            return ResponseBean.success(StatusEnum.SERVICE_DEAL_ERROR);
        }
    }

    private Map<String, Object> callService(RequestData requestData){
        String service = requestData.getService();
        String method = requestData.getMethod();
        try {
            logger.info("-----------callService发起服务请求{}",requestData.toString());
            Object classObject = getApplicationContext().getBean(service);
            Method classMethod = classObject.getClass().getMethod(method,new Class[]{Map.class});
            Object returnParam = classMethod.invoke(classObject,requestData.getParams());
            logger.info("-----------callService请求成功返回{}",returnParam.toString());
            return (Map<String, Object>) returnParam;
        }catch (Exception e){
            logger.error("-----------callService Exception>>>error",e);
            return ResponseBean.success(StatusEnum.SERVICE_DEAL_ERROR);
        }

    }

    private RequestData buildRequestData(Map<String, Object> initParam) {
        String paramKey = RequestData.ParamFlag;
        RequestData requestData = new RequestData();
        if(requestData.getMapParams(paramKey) == null){
            requestData.putMapParams(paramKey,new HashMap<>());
        }
        Map<String,Object> paramMap = requestData.getMapParams(paramKey);
        for (Map.Entry<String,Object> entry : initParam.entrySet()){
            paramMap.put(entry.getKey(),entry.getValue());
        }
        return requestData;
    }


}
