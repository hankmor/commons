package com.belonk.commons.util.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ting.yao
 * version 1.0
 * on 16/5/19.
 */
public class ServiceMsg {
    private Map head;
    private Object body;

    public ServiceMsg() {
        this.setHead(new HashMap());
        this.setBody(new Object());
    }

    public void setHead(Map head) {
        this.head = head;
    }

    public Map getHead() {
        return this.head;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Object getBody() {
        return this.body;
    }

}
