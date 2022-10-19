package com.example.demo.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBean {
    private static Map ConvertStrResponse(String statusCode, String statusMsg, Object object) {
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("result_code",statusCode);
        retMap.put("result_msg",statusMsg);
        retMap.put("result_data",object);
        return retMap;
    }

    public static Map success(Object object) {
        return ConvertStrResponse(StatusEnum.HTTP_OK.getStatusCode(),StatusEnum.HTTP_OK.getStatusMsg(),object);
    }
}
