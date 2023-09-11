package com.bist.tracking.app.controller;


import com.bist.tracking.app.exceptions.ApiRequestException;
import com.bist.tracking.app.request.response.AuthenticationRequest;
import com.bist.tracking.app.request.response.AuthenticationResponse;
import com.bist.tracking.app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @GetMapping("/login")
    public String viewHomePage() {
//        model.addAttribute("allemplist", employeeServiceImpl.getAllEmployee());
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletResponse response) {
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        try {
            AuthenticationResponse authResponse = authenticationService.authenticate(request);

            // Token'ı bir cookie olarak kaydet
            Cookie jwtCookie = new Cookie("jwt", authResponse.getAccessToken());
            jwtCookie.setSecure(true);  // HTTPS üzerinden gönder
            jwtCookie.setHttpOnly(true);  // JavaScript ile erişilemez
            jwtCookie.setPath("/");  // Cookie'nin geçerli olacağı yol
            response.addCookie(jwtCookie);


            return "redirect:/index";  // index sayfasına yönlendir
        } catch (ApiRequestException e) {
            // Hata durumlarında ne yapılacağı (Örneğin: hata sayfasına yönlendirme)
            return "redirect:/login?error=true";
        }
    }

}
