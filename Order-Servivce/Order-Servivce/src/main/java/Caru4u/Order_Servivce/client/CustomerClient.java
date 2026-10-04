package Caru4u.Order_Servivce.client;

import Caru4u.Order_Servivce.dto.AddressResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class CustomerClient {

    private final RestClient restClient;


    public CustomerClient(
            @Value("${caru4u.customer-service.url}")
            String customerServiceUrl
    ) {

        this.restClient =
                RestClient
                        .builder()
                        .baseUrl(customerServiceUrl)
                        .build();
    }


    public AddressResponse getAddress(
            String authorizationHeader
    ) {

        return restClient
                .get()

                // VERY IMPORTANT
                .uri("/auth/Customer/me/address")

                .header(
                        "Authorization",
                        authorizationHeader
                )

                .retrieve()

                // ONE object
                .body(AddressResponse.class);
    }
}