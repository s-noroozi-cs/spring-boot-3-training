package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.model.Order;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class OrderController {
    Map<Principal, List<Order>> orderRepository = new ConcurrentHashMap<>();

    @QueryMapping
    public List<Order> fetchMyOrder(Principal principal) {
        return orderRepository.get(principal);
    }

    @MutationMapping
    public Order createOrder(Principal principal, @Argument String name, @Argument int count) {
        Order order = Order.builder()
                .name(name)
                .count(count)
                .build();
        orderRepository.merge(principal,
                List.of(order),
                (a, b) ->
                        Stream.concat(a.stream(), b.stream())
                                .collect(Collectors.toList()));
        return order;
    }
}
