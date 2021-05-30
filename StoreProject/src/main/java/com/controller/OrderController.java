package com.controller;

import com.api.service.IOrderService;
import com.controller.configuration.NoSuchEntityException;
import com.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> showAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public Order showOrder(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping("/orders")
    public Order saveOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return order;
    }

    @PutMapping("/orders")
    public Order updateOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return order;
    }

    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id) {
        if (orderService.getById(id) == null) {
            throw new NoSuchEntityException("There is no order with id=" + id + " in database");
        } else {
            orderService.deleteOrder(id);
            return "Order with id=" + id + " was deleted";
        }
    }
}
