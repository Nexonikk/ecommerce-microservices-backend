package com.ecommerce.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.order_service.dto.InventoryResponse;
import com.ecommerce.order_service.dto.OrderLineItemsDto;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderLineItems;
import com.ecommerce.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

       InventoryResponse[] InventoryResponseArray =  webClient.get().uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();

       Boolean allProductsInStock = Arrays.stream(InventoryResponseArray)
       .allMatch(r -> Boolean.TRUE.equals(r.getIsInStock()));
       
       if (allProductsInStock) {
           orderRepository.save(order);
       } else {
        throw new IllegalArgumentException("Product out of stock");
       }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }

}
