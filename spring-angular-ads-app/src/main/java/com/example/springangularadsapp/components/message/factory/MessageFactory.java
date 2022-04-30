package com.example.springangularadsapp.components.message.factory;

import com.example.springangularadsapp.components.message.Message;
import com.example.springangularadsapp.components.message.MessageDto;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {
    public Message getMessageInstance(Class<? extends MessageDto> type) {
        if (type == MessageDto.class) return new Message();
        else
            return null;
    }
}
