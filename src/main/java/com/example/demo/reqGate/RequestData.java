package com.example.demo.reqGate;

import org.omg.CORBA.OBJ_ADAPTER;

import java.util.HashMap;
import java.util.Map;

public class RequestData {
    public static final String ParamFlag = "Params";
    public String service;
    public String method;
    private Map<String, Map<String, Object>> mapParams = new HashMap<>();

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getMapParams(String key) {
        return mapParams.get(key);
    }

    public void setMapParams(Map<String, Map<String, Object>> mapParams) {
        this.mapParams = mapParams;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "service='" + service + '\'' +
                ", method='" + method + '\'' +
                ", mapParams=" + mapParams +
                '}';
    }

    public void putMapParams(String paramKey, Map<String, Object> objectObjectHashMap) {
        mapParams.put(paramKey,objectObjectHashMap);
    }

    public Map<String,Object> getParams() {
        return getMapParams(ParamFlag) == null ? new HashMap<>() : getMapParams(ParamFlag);
    }
}
