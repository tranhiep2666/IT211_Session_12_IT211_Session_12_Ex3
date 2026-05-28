package org.example.ex3.service;


import org.example.ex3.dto.OrderCreateDto;
import org.example.ex3.enitty.Order;
import org.example.ex3.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public Order createOrder(OrderCreateDto createDto){

        Order newOrder = Order.builder()
                .customerName(createDto.getCustomerName())
                .product(createDto.getProduct())
                .quantity(createDto.getQuantity())
                .totalAmount(createDto.getTotalAmount())
                .build();


        return orderRepository.save(newOrder);
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrder(Long id, OrderCreateDto  dto){

        Order order = findById(id);

        if(order == null){
            throw new IllegalArgumentException("error Id");
        }

        order.setCustomerName(dto.getCustomerName());
        order.setProduct(dto.getProduct());
        order.setQuantity(dto.getQuantity());
        order.setTotalAmount(dto.getTotalAmount());
        return orderRepository.save(order);
    }


    public void deleteOrder(Long id){
        Order order = findById(id);
        if(order == null){
            throw new IllegalArgumentException("error Id");
        }

        orderRepository.delete(order);
    }
}
