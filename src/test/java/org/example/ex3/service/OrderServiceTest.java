package org.example.ex3.service;

import org.example.ex3.dto.OrderCreateDto;
import org.example.ex3.enitty.Order;
import org.example.ex3.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    private OrderCreateDto dto;

    @BeforeEach
    void setUp() {

        order = Order.builder()
                .id(1L)
                .customerName("John")
                .product("Laptop")
                .quantity(2)
                .totalAmount(2000.0)
                .build();

        dto = new OrderCreateDto();

        dto.setCustomerName("Anna");
        dto.setProduct("Phone");
        dto.setQuantity(1);
        dto.setTotalAmount(1000.0);
    }

    // 1
    @Test
    void getAllOrders_ReturnNonEmptyList() {

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        List<Order> result = orderService.getAllOrder();

        assertFalse(result.isEmpty());

        assertEquals(1, result.size());

        verify(orderRepository, times(1)).findAll();
    }

    // 2
    @Test
    void getOrderById_Found() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = orderService.findById(1L);

        assertNotNull(result);

        assertEquals("John", result.getCustomerName());

        verify(orderRepository, times(1)).findById(1L);
    }

    // 3
    @Test
    void getOrderById_NotFound_ThrowException() {

        when(orderRepository.findById(99L))
                .thenReturn(Optional.empty());

        Order result = orderService.findById(99L);

        assertNull(result);

        verify(orderRepository, times(1)).findById(99L);
    }

    // 4
    @Test
    void addOrder_Success() {

        Order savedOrder = Order.builder()
                .id(1L)
                .customerName(dto.getCustomerName())
                .product(dto.getProduct())
                .quantity(dto.getQuantity())
                .totalAmount(dto.getTotalAmount())
                .build();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        Order result = orderService.createOrder(dto);

        assertNotNull(result);

        assertEquals(1L, result.getId());

        assertEquals("Anna", result.getCustomerName());

        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    // 5
    @Test
    void updateOrder_Success() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        Order result = orderService.updateOrder(1L, dto);

        assertNotNull(result);

        assertEquals("Anna", result.getCustomerName());

        assertEquals("Phone", result.getProduct());

        verify(orderRepository, times(1)).save(order);
    }

    // 6
    @Test
    void deleteOrder_RemovesElement() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        doNothing().when(orderRepository).delete(order);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1))
                .delete(order);
    }
}