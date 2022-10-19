package com.example.demo.response;

public enum StatusEnum {
    HTTP_OK("200","成功"),
    SERVICE_DEAL_ERROR("603","服务处理请求失败");

    private String statusCode;
    private String statusMsg;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    StatusEnum(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
