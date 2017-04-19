package com.kaoguan.app.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for sms message send.
 */
@RestController
@RequestMapping("/api")
public class PostSmsResource {

    //查账户信息的http地址
    private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";

    //智能匹配模板发送接口的http地址
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

    private static String USER_API_KEY = "cffef252f1dbf5a571b9dad9a5bca347";

    @RequestMapping(value = "/smsTemplate", method = RequestMethod.GET)
    public ResponseEntity<?> getSmsTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URI_GET_USER_INFO+"?="+USER_API_KEY, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/postSms", method = RequestMethod.POST)
    public ResponseEntity<?> postSmsMessage(){

        RestTemplate restTemplate = new RestTemplate();
        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(formHttpMessageConverter);
        messageConverters.add(stringHttpMessageConverternew);
        restTemplate.setMessageConverters(messageConverters);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("apikey", USER_API_KEY);
        map.add("mobile", "18616758805");
        map.add("text", "【云片网】您的验证码是1234");
        String response = restTemplate.postForObject(URI_SEND_SMS, map, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
