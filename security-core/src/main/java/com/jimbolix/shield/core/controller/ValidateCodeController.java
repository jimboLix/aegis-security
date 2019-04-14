package com.jimbolix.shield.core.controller;

import com.jimbolix.shield.core.validate.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> codeProcessorMap;

    /**
     *
     * @param request
     * @param type sms or image
     * @throws IOException
     */
    @GetMapping("/code/{type}")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "type")String type) throws Exception {
        String processorKey = type.concat("ValidateCodeProcessor");
        ValidateCodeProcessor validateCodeProcessor = codeProcessorMap.get(processorKey);
        validateCodeProcessor.create(new ServletWebRequest(request,response));
    }


}
