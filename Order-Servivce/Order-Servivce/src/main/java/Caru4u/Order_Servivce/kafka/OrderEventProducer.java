package Caru4u.Order_Servivce.kafka;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final String TOPIC =
            "caru4u-order-placed";

    private final KafkaTemplate<String, OrderPlacedEvent>
            kafkaTemplate;


    public void send(
            OrderPlacedEvent event
    ) {

        kafkaTemplate.send(
                TOPIC,
                event.orderId().toString(),
                event
        );
    }
}