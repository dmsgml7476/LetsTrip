package com.trip.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute
    public void addRequestUri(HttpServletRequest request, Model model) {
        model.addAttribute("requestUri", request.getRequestURI());
    }
}
