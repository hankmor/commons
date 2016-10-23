package com.belonk.commons.util.http;

public class HttpProxy {
    private String host_url;
    private String port;
    private boolean isActivity = true;

    @Override
    public String toString() {
        String str = "";
        str = "new ProxyBuilder.HttpProxy(\"" + getHost_url() + "\",\"" + getPort() + "\");";
        return str;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setActivity(boolean isActivity) {
        this.isActivity = isActivity;
    }

    public HttpProxy(String host_url, String port) {
        this.host_url = host_url;
        this.port = port;
        this.setActivity(true);
    }

    public String getHost_url() {
        return host_url;
    }

    public void setHost_url(String host_url) {
        this.host_url = host_url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}