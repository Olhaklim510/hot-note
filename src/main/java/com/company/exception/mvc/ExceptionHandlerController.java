package com.company.exception.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exceptionMessage", e.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        if (e.getClass().getSimpleName().equals("TitleException") || e.getClass().getSimpleName().equals("ContentException")) {
            modelAndView.addObject("http_page", "https://http.cat/400.jpg");
        } else if (e.getClass().getSimpleName().equals("ShareException")) {
            modelAndView.addObject("http_page", "https://http.cat/403.jpg");
        } else {
            modelAndView.addObject("http_page", "https://http.cat/404.jpg");
        }

        return modelAndView;
    }
}
