package com.bist.tracking.app.controller.api;

import com.bist.tracking.app.model.Stock;
import com.bist.tracking.app.model.User;
import com.bist.tracking.app.service.StockService;
import com.bist.tracking.app.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class ApiStockController {

    private final UserService userService;
    private final StockService stockService;


    @Operation(summary = "Home Page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Home get succesful", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Login required", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Stock>> getStockState(@AuthenticationPrincipal User user){

        return new ResponseEntity<>(user.getStocks(), HttpStatus.OK);
    }


    @Hidden
    @PostMapping("/{userId}")
    public ResponseEntity<Stock> addStockToUser(@PathVariable Integer userId, @RequestBody Stock stock) {

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        stock.setUser(user);
        stockService.saveStock(stock);

        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }


}
