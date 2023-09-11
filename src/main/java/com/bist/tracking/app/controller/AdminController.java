package com.bist.tracking.app.controller;

import com.bist.tracking.app.model.Stock;
import com.bist.tracking.app.model.User;
import com.bist.tracking.app.service.StockService;
import com.bist.tracking.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final StockService stockService;
    @GetMapping()
    public String viewAdminPage(Model model) {
//        model.addAttribute("allemplist", employeeServiceImpl.getAllEmployee());
        List<User> users=userService.findAll();
        model.addAttribute("users", users);  //
        return "admin";
    }

    @GetMapping("add-stock/{id}")
    public String addStock(@PathVariable("id") int userId, Model model) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        model.addAttribute("user", user);

        return "addStock";  // addStock.html.html template'ini göster.
    }

    @PostMapping("/add-stock")
    public String saveStock(@ModelAttribute Stock stock, @RequestParam("userId") int userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        stock.setUser(user);
        stockService.saveStock(stock);  // StockRepository bir Spring Data JPA repository olmalıdır.
        return "redirect:/admin/users";
    }

}
