package com.bist.tracking.app.controller.api;

import com.bist.tracking.app.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {
    @Operation(summary = "My Logged User Info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get succesful", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Login required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Get Failed.", content = @Content)
    })
    @GetMapping
    private ResponseEntity<User> getCurrentUserOnAuth(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
