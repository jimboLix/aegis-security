package com.jimbolix.shield.brower.support;

public class SimpleResponse {
    public SimpleResponse(Object message) {
        this.message = message;
    }

    private Object message;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
