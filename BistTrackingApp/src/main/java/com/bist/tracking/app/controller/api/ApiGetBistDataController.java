package com.bist.tracking.app.controller.api;

import com.bist.tracking.app.model.Stock;
import com.bist.tracking.app.service.CurlService;
import com.bist.tracking.app.model.User;
import com.bist.tracking.app.service.StockService;
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

import java.util.List;

@RestController
@RequestMapping("api/get-bist-data")
@RequiredArgsConstructor
public class ApiGetBistDataController {

    private  final CurlService curlService;
    private  final StockService stockService;


    @Operation(summary = "My Portfolio Stock Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get succesful", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Login required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Get Failed.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Stock>>getBistData(@AuthenticationPrincipal User user) {
        List<Stock> stocks = updateBistDatas(user);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }


    private List<Stock> updateBistDatas(User user){
        List<Stock> stocks = user.getStocks();

        if (stocks != null && !stocks.isEmpty()) {
            for (Stock stock : stocks) {
                // Burada curlService ile yeni fiyat verilerini çekip stock nesnesini güncelleyebiliriz.
                Stock currentStock = curlService.getCurrentStockInformationOnCurl(stock.getCode()); // getDetailedStock metodunu varsayıyorum.
                stock.setPrice(currentStock.getPrice()); // setPrice ve getPrice metodlarını varsayıyorum.
                double costTotalPrice=stock.getCost()*stock.getLot();
                double currentTotalPrice=stock.getPrice()*stock.getLot();
                stock.setEarning(currentTotalPrice-costTotalPrice);
                stock.setName(currentStock.getName());
                stock.setEarnPercentage((stock.getPrice()/stock.getCost())*100);
                stockService.saveStock(stock);
                // Diğer güncellenecek alanlar
            }
        }
        return stocks;
    }


    @Operation(summary = "All Bist Data Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get succesful", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Login required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Get Failed.", content = @Content)
    })
    @GetMapping("all")
    private ResponseEntity<List<Stock>> getAllStocks(){
        return new ResponseEntity<>(curlService.getAllStocks(), HttpStatus.OK);

    }
}
