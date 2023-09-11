package com.bist.tracking.app.controller;


import com.bist.tracking.app.controller.api.ApiGetBistDataController;
import com.bist.tracking.app.model.Stock;
import com.bist.tracking.app.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor

public class StockController {

    private final ApiGetBistDataController apiGetBistDataController;
    @GetMapping("/")
    public String viewHomePage(Model model) {
//        model.addAttribute("allemplist", employeeServiceImpl.getAllEmployee());
        return "index";
    }
    @GetMapping("/index")
    public String index(@AuthenticationPrincipal User user,Model model) {
        apiGetBistDataController.getBistData(user);
        List<Stock> stocks = user.getStocks();

        model.addAttribute("stocks", stocks);
        return "index";
    }

}
