package com.example.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@Component
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //전처리
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper ((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper ((HttpServletResponse) response);


//        BufferedReader br = httpServletRequest.getReader();
//        br.lines().forEach(line -> {
//            log.info("url {},  line {}", url, line);
//        });

        chain.doFilter(httpServletRequest, httpServletResponse);
        String url = httpServletRequest.getRequestURI();


        //후처리
        //req
        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        log.info("request url : {} , request body : {}", url, reqContent);

        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        log.info("response status : {}, responseBoddy : {} ", httpStatus, resContent);
    }
}
