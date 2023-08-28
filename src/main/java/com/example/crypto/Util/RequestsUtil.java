//package com.example.crypto.Util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.security.KeyStore;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Consumer;
//
//@Component
//public class RequestsUtil {
//    //send an http request
//
//    //send an https request
//
//    //GET PUT POST DELETE
//    //GET and DELETE have no body
//    //POST and PUT have body
//
//    //Prepare request
//    //add url
//    //if present add headers
//    //if present add body
//
//    @Autowired
//    RestTemplate restTemplate;
//
//
////    RequestEntity request;
//
//    public HttpHeaders getHeaders(HttpHeaders headers, MultiValueMap multiValueMap){
//        headers.addAll(multiValueMap);
//        return headers;
//    }
//
//    public Object getBody(Object requestBody){
//        return requestBody;
//    }
//
//    public boolean validateProtocol(String url){
//        if(url.contains("http")){
//            //send https request
//            return true;
//        }
//        return false;
//    }
//
//    public void protocol(String url){
//        if(url.contains("https")){
//            //send https request
//        }
//        if(url.contains("http")){
//            //send http request
//        }
//    }
//
//    public <T> String sendRequest(String url,HttpMethod method, Object requestBody, MultiValueMap<String,String> headers){
//        RequestEntity<T> request = new RequestEntity<>();
//        if(!headers.isEmpty()){
//            for(String header:headers.keySet()){
//                request.getHeaders().set(header, String.valueOf(headers.get(header)));
//            }
//        }
//
//        if(method.matches("POST") || method.matches("PUT")){
//            request.getBody()
//        }
//    }
//
//
//}
