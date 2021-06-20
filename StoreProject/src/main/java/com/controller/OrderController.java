package com.controller;

import com.api.service.IOrderService;
import com.controller.configuration.NoSuchEntityException;
import com.model.Order;
import com.model.OrderStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> showAllOrders() {
        log.info("Received GET request /orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> showOrder(@PathVariable Long id) {
        log.info("Received GET request /orders/" + id);
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        log.info("Received POST request /orders");
        orderService.saveOrder(order);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        log.info("Received PUT request /orders");
        orderService.saveOrder(order);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        log.info("Received DELETE request /orders/" + id);
        if (orderService.getById(id) == null) {
            throw new NoSuchEntityException("There is no order with id=" + id + " in database");
        } else {
            orderService.deleteOrder(id);
            return "Order with id=" + id + " was deleted";
        }
    }

    @PatchMapping("/cancel-order/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        log.info("Received PATCH request /orders/" + id);
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @PatchMapping("/change-status/{status}/{id}")
    public ResponseEntity<Order> changeOrderStatus(@PathVariable String status,
                                                   @PathVariable Long id) {
        log.info("Received PATCH request /orders/change-status/" + status + "/" + id);
        return ResponseEntity.ok(orderService.changeOrderStatus(id, OrderStatus.valueOf(status.toUpperCase())));
    }

    @GetMapping("/price-by-period/{from}/{till}")
    public ResponseEntity<Double> priceGetByPeriodOfTime(@PathVariable String from,
                                                         @PathVariable String till) {
        log.info("Received GET request /orders/price-by-period/" + from + "/" + till);
        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate tillDate = LocalDate.parse(till, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return ResponseEntity.ok(orderService.priceGetByPeriodOfTime(fromDate, tillDate));
    }

    @GetMapping("/done-by-period/{from}/{till}")
    public ResponseEntity<List<Order>> ordersDoneByPeriodOfTime(@PathVariable String from,
                                                                @PathVariable String till) {
        log.info("Received GET request /orders/done-by-period/" + from + "/" + till);
        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate tillDate = LocalDate.parse(till, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return ResponseEntity.ok(orderService.ordersDoneByPeriodOfTime(fromDate, tillDate));
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "booksId") List<Long> booksId) {
        log.info("Received POST request /orders/add/" + name);
        return ResponseEntity.ok(orderService.addOrderUsingId(name, booksId));
    }
}
