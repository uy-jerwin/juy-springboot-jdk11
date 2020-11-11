package juy.web.interceptor;

import java.util.HashMap;
import java.util.Map;

public final class LoggingTransaction {

    private String id;
    private String method;
    private String requestURI;
    private final Map<String, String> headers = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public boolean addHeader(String name, String value) {
        return headers.put(name, value) != null;
    }

}
