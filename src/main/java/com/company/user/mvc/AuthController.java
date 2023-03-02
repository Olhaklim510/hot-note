package com.company.user.mvc;

import com.company.user.*;
import com.company.user.dto.LoginDto;
import com.company.user.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.Collections;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public ModelAndView processRegistrationForm(@ModelAttribute RegisterDto registerDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/register");

        String username = registerDto.getUsername();
        String password = registerDto.getPassword();

        if (!username.matches("[A-Za-z0-9]*")) {
            modelAndView.addObject("error", "Username must only contain letters and numbers");
            return modelAndView;
        }

        if (userService.findByUsername(username).isPresent()) {
            modelAndView.addObject("error", "Username already exists");
            return modelAndView; 
        }

        if (username.length() < 5 || username.length() > 50) {
            modelAndView.addObject("error", "Username length must be between 5 and 50 characters");
            return modelAndView;
        }

        if (password.length() < 8 || password.length() > 100) {
            modelAndView.addObject("error", "Password length must be between 8 and 100 characters");
            return modelAndView;
        }

        userService.addUser(username,password);
        modelAndView.setViewName("redirect:auth/login");
        return modelAndView;
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")

    public ModelAndView processLogin(@ModelAttribute LoginDto loginDto, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        String username = loginDto.getUsername();

        if (userService.findByUsername(username).isEmpty()) {
            modelAndView.addObject("error", "Username not found");
            return modelAndView;
        }

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            modelAndView.setViewName("redirect:/note/list");

        } catch (UsernameNotFoundException ex) {
            modelAndView.addObject("error", "Username not found");
            modelAndView.setViewName("auth/login");
        } catch (BadCredentialsException ex) {
            modelAndView.addObject("error", "The password is incorrect");
            modelAndView.setViewName("auth/login");
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        return modelAndView;
    }
}