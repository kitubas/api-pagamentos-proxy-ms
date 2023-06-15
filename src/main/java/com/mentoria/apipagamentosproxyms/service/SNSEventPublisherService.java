package com.mentoria.apipagamentosproxyms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SNSEventPublisherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SNSEventPublisherService.class);

    @NonNull
    private NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${cloud.aws.queue.url}")
    private String queueUrl;

    @Value("${cloud.aws.topic.name}")
    private String topicName;


    public void enviarMensagemSns(Object message) {
        try {
            LOGGER.info("Generating event : {}", message);
            notificationMessagingTemplate.convertAndSend(topicName, objectMapper.writeValueAsString(message));
            LOGGER.info("Event has been published in SNS.");
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Exception ocurred while pushing event to sns : {} and stacktrace ; {}", e.getMessage(), e);
        }
    }

}