package com.belonk.commons.util.vo;

public class ResponseMessage {
    private String code;
    private String msg;
    private Object data;

    public ResponseMessage() {
    }

    public ResponseMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseMessage(String code, String msg,  Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
 