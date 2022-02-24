package com.github.pedroluiznogueira.microservicesproduct.service;

import com.github.pedroluiznogueira.microservicesproduct.enums.EventType;
import com.github.pedroluiznogueira.microservicesproduct.model.Envelope;
import com.github.pedroluiznogueira.microservicesproduct.model.Product;
import com.github.pedroluiznogueira.microservicesproduct.model.ProductEvent;
import org.springframework.stereotype.Service;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {
    private static final Logger log = LoggerFactory.getLogger(
            ProductPublisher.class);

    private AmazonSNS snsClient;
    private Topic productEventsTopic;
    private ObjectMapper objectMapper;

    public ProductPublisher(AmazonSNS snsClient,
                            @Qualifier("productEventsTopic")Topic productEventsTopic,
                            ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType,
                                    String username) {
        ProductEvent productEvent = new ProductEvent();
        productEvent.setProductId(product.getId());
        productEvent.setCode(product.getCode());
        productEvent.setUsername(username);

        Envelope envelope = new Envelope();
        envelope.setEventType(eventType);

        try {
            envelope.setData(objectMapper.writeValueAsString(productEvent));

            PublishResult publishResult = snsClient.publish(
                    productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelope));

            log.info("Product event sent - ProductId: {} - MessageId: {}",
                    product.getId(), publishResult.getMessageId());
        } catch (JsonProcessingException e) {
            log.error("Failed to create product event message");
        }
    }
}
