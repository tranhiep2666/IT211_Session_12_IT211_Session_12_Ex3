package org.example.ex3.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.ex3.dto.OrderCreateDto;
import org.example.ex3.enitty.Order;
import org.example.ex3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET ALL
    @GetMapping
    public List<Order> getAllOrders() {

        log.info("GET /api/orders - Fetch all orders");

        List<Order> orders = orderService.getAllOrder();

        log.info("Total orders found: {}", orders.size());

        return orders;
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {

        log.info("GET /api/orders/{} - Fetch order by id", id);

        Order order = orderService.findById(id);

        if (order == null) {

            log.error("Order not found with id: {}", id);

            throw new IllegalArgumentException("Order not found");
        }

        log.info("Order found: {}", order);

        return order;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody OrderCreateDto dto) {

        log.info("POST /api/orders - Create new order");

        log.info("Request body: customerName={}, product={}, quantity={}, totalAmount={}",
                dto.getCustomerName(),
                dto.getProduct(),
                dto.getQuantity(),
                dto.getTotalAmount());

        Order createdOrder = orderService.createOrder(dto);

        log.info("Order created successfully with id: {}", createdOrder.getId());

        return createdOrder;
    }

    // UPDATE
    @PutMapping("/{id}")
    public Order updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderCreateDto dto
    ) {

        log.info("PUT /api/orders/{} - Update order", id);

        Order updatedOrder = orderService.updateOrder(id, dto);

        log.info("Order updated successfully: {}", updatedOrder);

        return updatedOrder;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {

        log.info("DELETE /api/orders/{} - Delete order", id);

        orderService.deleteOrder(id);

        log.info("Order deleted successfully with id: {}", id);

        return "Delete success";
    }
}