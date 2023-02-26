package com.company.mvc;

import com.company.dto.LoginDto;
import com.company.dto.RegisterDto;
import com.company.user.Role;
import com.company.user.RoleRepository;
import com.company.user.UserEntity;
import com.company.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

        if (userRepository.findByUsername(username).isPresent()) {
            modelAndView.addObject("error", "Username already exists");
            return modelAndView; // return the ModelAndView object with error message
        }

        if (username.length() < 5 || username.length() > 50) {
            modelAndView.addObject("error", "Username length must be between 5 and 50 characters");
            return modelAndView;
        }

        if (password.length() < 8 || password.length() > 100) {
            modelAndView.addObject("error", "Password length must be between 8 and 100 characters");
            return modelAndView;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        modelAndView.setViewName("redirect:auth/login");
        return modelAndView;
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public RedirectView processLogin(@ModelAttribute LoginDto loginDto) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/note/list");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return redirectView;
    }
}
