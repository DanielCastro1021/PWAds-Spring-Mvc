package com.example.springangularadsapp.firebase;

import com.example.springangularadsapp.components.messages.Message;
import com.example.springangularadsapp.components.messages.assembler.MessageModelAssembler;
import com.example.springangularadsapp.security.authorization.annotation.UserAccess;
import com.example.springangularadsapp.security.models.User;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.Data;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseTestController {
    /*******************************/
    private final FirebaseMessagingService firebaseService;
    private final MessageModelAssembler assembler;

    public FirebaseTestController(FirebaseMessagingService firebaseService, MessageModelAssembler assembler) {
        this.firebaseService = firebaseService;
        this.assembler = assembler;
    }

    @UserAccess
    @PostMapping("/send-notification/{token}")
    public String sendNotification(@PathVariable String token) throws FirebaseMessagingException, IOException {
        Message msg = new Message();
        msg.setMessage("TestController.sendNotification");
        User user=new User();
        user.setUsername("danielcastro11");
        msg.setFrom(user);
        EntityModel<Message> model = assembler.toModel(msg);
        return firebaseService.sendMessageNotification(model, token);
    }
}
