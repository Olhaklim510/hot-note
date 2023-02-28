package com.company.user.mvc;

import com.company.user.dto.LoginDto;
import com.company.user.dto.RegisterDto;
import com.company.user.Role;
import com.company.user.RoleRepository;
import com.company.user.UserEntity;
import com.company.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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
    @ResponseBody
    public ResponseEntity processLogin(@ModelAttribute LoginDto loginDto, HttpServletRequest request) {

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/note/list");

        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
        } catch (Exception ex) {
            String username = loginDto.getUsername();
            String password = loginDto.getPassword();
            UserEntity user = userRepository.findByUsername(username).orElse(null);

            if (password.isEmpty()) {
                return ResponseEntity.badRequest().body("Please enter password");
            }
            if (!username.equals(user.getUsername())) {
                return ResponseEntity.badRequest().body("This user doesn't exists :(");
            }
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return ResponseEntity.ok("Success login");

    }
}
