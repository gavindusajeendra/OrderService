package com.gusto.microservices.service;

import com.gusto.microservices.client.InventoryClient;
import com.gusto.microservices.dto.OrderRequest;
import com.gusto.microservices.model.Order;
import com.gusto.microservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){
        var isProductIsInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductIsInStock){
            //map the order request to order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuentity(orderRequest.quantity());

            //save order to orderRepository
            orderRepository.save(order);

        }else{
            throw new RuntimeException("Present with skucode " + orderRequest.skuCode() + "Is Not in Stock");
        }




    }
}
