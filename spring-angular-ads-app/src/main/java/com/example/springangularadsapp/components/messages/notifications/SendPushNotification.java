package com.example.springangularadsapp.components.messages.notifications;


import com.example.springangularadsapp.components.messages.Message;
import com.example.springangularadsapp.firebase.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Aspect
public class SendPushNotification {
    private final FirebaseMessagingService firebaseMessagingService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SendPushNotification(FirebaseMessagingService firebaseMessagingService) {
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @Pointcut("within(com..messages.controller.MessageController)")
    public void routeMessageControllerPointcut() {
    }

    @Pointcut("execution(* save(..))")
    public void saveMessagePointcut() {
    }

    @AfterReturning(value = "routeMessageControllerPointcut() && saveMessagePointcut()", returning = "entityModel")
    public void sendPushNotificationToUser(EntityModel<Message> entityModel) throws IOException, FirebaseMessagingException {
        String token = entityModel.getContent().getTo().getFirebaseToken();
        logger.info("TOKEN====>" + token);
        firebaseMessagingService.sendMessageNotification(entityModel, token);
    }
}
