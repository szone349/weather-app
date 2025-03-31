package com.weatherapp.weatherdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // org.springframework.web.client.HttpClientErrorException$NotFound
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("handleHttpClientErrorException {}", ex.getStatusCode());

        ModelAndView modelAndView = new ModelAndView();
        // all errors going to the same view template
        modelAndView.setViewName("error");
        if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
            // handle 400
            modelAndView.addObject("message", "Bad Request");
        } else if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            // handle 404
            modelAndView.addObject("message", "Resource/Zip Not Found");
        } else {
            modelAndView.addObject("message", ex.getStatusCode());
        }
        return modelAndView;
    }

    // General exception handler for any other exceptions
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("message", ex.getMessage());
        log.error("Error occured: ", ex);
        return modelAndView;
    }

    // org.springframework.web.method.annotation.HandlerMethodValidationException
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ModelAndView handleValidationException(HandlerMethodValidationException ex) {
        log.error("Contraint Validation error occured: ", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("message", "Invalid data");
        return modelAndView;
    }

}
