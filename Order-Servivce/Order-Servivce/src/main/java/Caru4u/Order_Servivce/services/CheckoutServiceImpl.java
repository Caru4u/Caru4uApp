package Caru4u.Order_Servivce.services;

import Caru4u.Order_Servivce.client.CartClient;
import Caru4u.Order_Servivce.client.CustomerClient;

import Caru4u.Order_Servivce.dto.AddressResponse;
import Caru4u.Order_Servivce.dto.CartItemResponse;
import Caru4u.Order_Servivce.dto.CartResponse;
import Caru4u.Order_Servivce.dto.CheckoutItemResponse;
import Caru4u.Order_Servivce.dto.CheckoutResponse;
import Caru4u.Order_Servivce.dto.PlaceOrderRequest;
import Caru4u.Order_Servivce.dto.PlaceOrderResponse;

import Caru4u.Order_Servivce.entity.Order;
import Caru4u.Order_Servivce.entity.OrderItem;
import Caru4u.Order_Servivce.entity.OrderStatus;

import Caru4u.Order_Servivce.kafka.OrderEventProducer;
import Caru4u.Order_Servivce.kafka.OrderPlacedEvent;

import Caru4u.Order_Servivce.repository.OrderItemRepository;
import Caru4u.Order_Servivce.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CartClient cartClient;

    private final CustomerClient customerClient;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final OrderEventProducer orderEventProducer;


    // =========================================================
    // GET CHECKOUT DETAILS
    // =========================================================

    @Override
    public CheckoutResponse getCheckout(
            Long customerId,
            String authorization
    ) {

        // =====================================================
        // 1. GET LOGGED-IN CUSTOMER CART
        // =====================================================

        CartResponse cart =
                cartClient.getCart(
                        authorization
                );


        if (cart == null) {

            throw new IllegalStateException(
                    "Cart not found"
            );
        }


        if (cart.getItems() == null ||
                cart.getItems().isEmpty()) {

            throw new IllegalStateException(
                    "Cart is empty"
            );
        }


        // =====================================================
        // 2. VERIFY CART BELONGS TO LOGGED-IN CUSTOMER
        // =====================================================

        if (cart.getCustomerId() == null ||
                !customerId.equals(
                        cart.getCustomerId()
                )) {

            throw new SecurityException(
                    "Cart does not belong to authenticated customer"
            );
        }


        // =====================================================
        // 3. GET LOGGED-IN CUSTOMER ADDRESS
        // =====================================================

        AddressResponse address =
                customerClient.getAddress(
                        authorization
                );


        if (address == null) {

            throw new IllegalStateException(
                    "Customer address not found"
            );
        }


        // =====================================================
        // 4. VERIFY ADDRESS BELONGS TO LOGGED-IN CUSTOMER
        // =====================================================

        if (address.getCustomerId() == null ||
                !customerId.equals(
                        address.getCustomerId()
                )) {

            throw new SecurityException(
                    "Address does not belong to authenticated customer"
            );
        }


        // =====================================================
        // 5. VALIDATE APARTMENT
        // =====================================================

        if (address.getApartmentOrVillaId() == null) {

            throw new IllegalStateException(
                    "Apartment/Villa is not configured for customer"
            );
        }


        // =====================================================
        // 6. CONVERT CART ITEMS -> CHECKOUT ITEMS
        // =====================================================

        List<CheckoutItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(item ->

                                CheckoutItemResponse
                                        .builder()

                                        .cartItemId(
                                                item.getCartItemId()
                                        )

                                        .productId(
                                                item.getProductId()
                                        )

                                        .packageId(
                                                item.getPackageId()
                                        )

                                        .productName(
                                                item.getProductName()
                                        )

                                        .packageName(
                                                item.getPackageName()
                                        )

                                        .vehicleType(
                                                item.getVehicleType()
                                        )

                                        .frequency(
                                                item.getFrequency()
                                        )

                                        .quantity(
                                                item.getQuantity()
                                        )

                                        .unitPrice(
                                                item.getUnitPrice()
                                        )

                                        .totalPrice(
                                                item.getTotalPrice()
                                        )

                                        .build()
                        )
                        .toList();


        // =====================================================
        // 7. CALCULATE SUBTOTAL
        // =====================================================

        BigDecimal subtotal =
                cart.getItems()
                        .stream()

                        .map(
                                CartItemResponse::getTotalPrice
                        )

                        .filter(
                                price -> price != null
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );


        // =====================================================
        // 8. GET DISCOUNT
        // =====================================================

        BigDecimal discount =
                cart.getDiscount() == null
                        ? BigDecimal.ZERO
                        : cart.getDiscount();


        // =====================================================
        // 9. CALCULATE TOTAL
        // =====================================================

        BigDecimal total =
                subtotal.subtract(
                        discount
                );


        if (total.compareTo(
                BigDecimal.ZERO
        ) < 0) {

            total = BigDecimal.ZERO;
        }


        // =====================================================
        // 10. RETURN CHECKOUT
        // =====================================================

        return CheckoutResponse
                .builder()

                .cartId(
                        cart.getCartId()
                )

                .customerId(
                        customerId
                )

                .address(
                        address
                )

                .items(
                        items
                )

                .itemCount(
                        items.size()
                )

                .subtotal(
                        subtotal
                )

                .discount(
                        discount
                )

                .total(
                        total
                )

                .build();
    }


    // =========================================================
    // PLACE ORDER
    // =========================================================

    @Override
    @Transactional
    public PlaceOrderResponse placeOrder(
            Long customerId,
            String authorization,
            PlaceOrderRequest request
    ) {

        // =====================================================
        // 1. VALIDATE REQUEST
        // =====================================================

        if (request == null) {

            throw new IllegalArgumentException(
                    "Place order request cannot be null"
            );
        }


        if (request.getPreferredDate() == null) {

            throw new IllegalArgumentException(
                    "Preferred date is required"
            );
        }


        if (request.getPreferredTime() == null ||
                request.getPreferredTime().isBlank()) {

            throw new IllegalArgumentException(
                    "Preferred time is required"
            );
        }


        if (request.getPaymentMethod() == null) {

            throw new IllegalArgumentException(
                    "Payment method is required"
            );
        }


        // =====================================================
        // 2. FETCH CHECKOUT AGAIN
        // =====================================================
        //
        // customerId comes from JWT.
        //
        // Cart and prices come from Cart Service.
        //
        // Address comes from Customer Service.
        //
        // Frontend cannot change these values.
        //
        // =====================================================

        CheckoutResponse checkout =
                getCheckout(
                        customerId,
                        authorization
                );


        // =====================================================
        // 3. GET CUSTOMER ADDRESS
        // =====================================================

        AddressResponse address =
                checkout.getAddress();


        if (address == null) {

            throw new IllegalStateException(
                    "Customer address not found"
            );
        }


        // =====================================================
        // 4. VERIFY CUSTOMER ADDRESS
        // =====================================================

        if (address.getCustomerId() == null ||
                !customerId.equals(
                        address.getCustomerId()
                )) {

            throw new SecurityException(
                    "Customer address does not belong to authenticated customer"
            );
        }


        // =====================================================
        // 5. VALIDATE APARTMENT
        // =====================================================

        if (address.getApartmentOrVillaId() == null) {

            throw new IllegalStateException(
                    "Apartment/Villa is not configured"
            );
        }


        // =====================================================
        // 6. CREATE ORDER
        // =====================================================

        LocalDateTime now =
                LocalDateTime.now();


        Order order =
                new Order();


        // Logged-in customer
        order.setCustomerId(
                customerId
        );


        /*
         * IMPORTANT:
         *
         * We are no longer doing:
         *
         * order.setAddressId(request.getAddressId());
         *
         * because the current Customer Service does not have
         * a separate addressId.
         *
         * Instead we use the customer's selected
         * Apartment/Villa ID.
         *
         * This requires Order.addressId to remain Long.
         */

        order.setAddressId(
                address.getApartmentOrVillaId()
        );


        order.setPreferredDate(
                request.getPreferredDate()
        );


        order.setPreferredTime(
                request.getPreferredTime()
        );


        order.setPaymentMethod(
                request.getPaymentMethod()
        );


        // =====================================================
        // 7. ORDER AMOUNTS
        // =====================================================

        order.setSubtotal(
                checkout.getSubtotal()
        );


        order.setDiscount(
                checkout.getDiscount()
        );


        order.setTotal(
                checkout.getTotal()
        );


        // =====================================================
        // 8. ORDER STATUS
        // =====================================================

        order.setStatus(
                OrderStatus.CONFIRMED
        );


        order.setCreatedAt(
                now
        );


        order.setUpdatedAt(
                now
        );


        // =====================================================
        // 9. SAVE ORDER
        // =====================================================

        Order savedOrder =
                orderRepository.save(
                        order
                );


        // =====================================================
        // 10. COPY CART ITEMS -> ORDER ITEMS
        // =====================================================

        for (
                CheckoutItemResponse item :
                checkout.getItems()
        ) {

            OrderItem orderItem =
                    new OrderItem();


            orderItem.setOrderId(
                    savedOrder.getId()
            );


            orderItem.setProductId(
                    item.getProductId()
            );


            orderItem.setPackageId(
                    item.getPackageId()
            );


            orderItem.setProductName(
                    item.getProductName()
            );


            orderItem.setPackageName(
                    item.getPackageName()
            );


            orderItem.setVehicleType(
                    item.getVehicleType()
            );


            orderItem.setFrequency(
                    item.getFrequency()
            );


            orderItem.setQuantity(
                    item.getQuantity()
            );


            orderItem.setUnitPrice(
                    item.getUnitPrice()
            );


            orderItem.setTotalPrice(
                    item.getTotalPrice()
            );


            orderItemRepository.save(
                    orderItem
            );
        }


        // =====================================================
        // 11. CLEAR CUSTOMER CART
        // =====================================================

        cartClient.clearCart(
                authorization
        );


        // =====================================================
        // 12. CREATE KAFKA EVENT
        // =====================================================

        OrderPlacedEvent event =
                new OrderPlacedEvent(

                        savedOrder.getId(),

                        savedOrder.getCustomerId(),

                        savedOrder.getTotal(),

                        savedOrder
                                .getStatus()
                                .name(),

                        savedOrder.getCreatedAt()
                );


        // =====================================================
        // 13. SEND KAFKA EVENT
        // =====================================================

        orderEventProducer.send(
                event
        );


        // =====================================================
        // 14. RETURN RESPONSE
        // =====================================================

        return new PlaceOrderResponse(

                savedOrder.getId(),

                savedOrder.getStatus(),

                savedOrder.getTotal(),

                "Order placed successfully"
        );
    }
}