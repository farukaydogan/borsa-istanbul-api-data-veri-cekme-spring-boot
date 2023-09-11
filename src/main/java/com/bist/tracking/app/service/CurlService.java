package com.bist.tracking.app.service;

import com.bist.tracking.app.model.Stock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurlService {

    private final WebClient webClient;

    public CurlService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Stock getCurrentStockInformationOnCurl(String code) {
        Stock stock=new Stock();
        String response = webClient.get()
                .uri("https://bigpara.hurriyet.com.tr/api/v1/borsa/hisseyuzeysel/"+code)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode data = root.path("data");
            JsonNode hisseYuzeysel = data.path("hisseYuzeysel");
            stock.setPrice( hisseYuzeysel.path("kapanis").asInt());
            stock.setName( hisseYuzeysel.path("aciklama").asText());
            return stock;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public  List<Stock> getAllStocks() {
        List<Stock> stocksList = null;
        try {
            String data = webClient.get()
                    .uri("https://www.sabah.com.tr/json/canli-borsa-verileri")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            String[] stocks = data.split("~");
            stocksList = new ArrayList<>();

            // İlk eleman tarih ve saat bilgisi olduğu için onu atlayalım
            for (int i = 1; i < stocks.length; i++) {
                // Her bir hisse senedini `|` ile ayıralım
                String[] stockInfo = stocks[i].split("\\|");
                String stockCode = stockInfo[0];  // Hisse senedi kodu
                double stockPrice = Double.parseDouble(stockInfo[1].replace(",", "."));
                String stockChange = stockInfo[2]; // Fiyat değişimi
                // Daha fazla bilgiyi burada ayıklayabilirsiniz
                Stock stock = new Stock();
                stock.setPrice(stockPrice);
                stock.setCode(stockCode);
                stocksList.add(stock);

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Hata yakalandı, burada ne yapacağınızı belirleyin
        }
        return stocksList;
    }
}