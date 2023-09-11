package com.bist.tracking.app.controller.api;

import com.bist.tracking.app.request.response.AuthenticationRequest;
import com.bist.tracking.app.request.response.AuthenticationResponse;
import com.bist.tracking.app.service.AuthenticationService;
import com.bist.tracking.app.request.response.RegisterRequest;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiAuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "New user section")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Register succesful", content = {@Content(mediaType = "application/json")}),
          @ApiResponse(responseCode = "404", description = "Register Failed.", content = @Content)
  })

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  @Operation(summary = "Login section")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Login succesful", content = {@Content(mediaType = "application/json")}),
          @ApiResponse(responseCode = "401", description = "Login Failed.", content = @Content)
  })

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
      return ResponseEntity.ok(service.authenticate(request));
  }

  @Hidden
  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
