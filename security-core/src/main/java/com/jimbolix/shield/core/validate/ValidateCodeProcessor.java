package com.jimbolix.shield.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {

    void create(ServletWebRequest request) throws Exception;
}
