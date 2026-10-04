package Caru4u.Order_Servivce.client;

import Caru4u.Order_Servivce.dto.CartResponse;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestClient;

@Component
public class CartClient {

    private final RestClient restClient;


    public CartClient(
            @Value("${caru4u.cart-service.url}")
            String cartServiceUrl
    ) {

        this.restClient =
                RestClient.builder()
                        .baseUrl(cartServiceUrl)
                        .build();
    }


    public CartResponse getCart(
            String authorizationHeader
    ) {

        return restClient
                .get()
                .uri("/api/cart")
                .header(
                        "Authorization",
                        authorizationHeader
                )
                .retrieve()
                .body(CartResponse.class);
    }


    public void clearCart(
            String authorizationHeader
    ) {

        restClient
                .delete()
                .uri("/api/cart")
                .header(
                        "Authorization",
                        authorizationHeader
                )
                .retrieve()
                .toBodilessEntity();
    }
}