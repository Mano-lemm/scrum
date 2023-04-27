package com.example.scrumtrial.services;

import com.example.scrumtrial.models.dtos.MessageResponse;
import com.example.scrumtrial.models.dtos.MsgByEmailRequest;
import com.example.scrumtrial.models.dtos.MsgBySmsRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    public List<MessageResponse> getAllBySender(MsgByEmailRequest req) {
        System.err.println("getAllSentBy (email) is not yet implemented");
        return List.of();
    }

    public List<MessageResponse> getAllBySender(MsgBySmsRequest req) {
        System.err.println("getAllSentBy (sms) is not yet implemented");
        return List.of();
    }

    public List<MessageResponse> getAllByReceiver(MsgByEmailRequest req) {
        System.err.println("getAllSentBy (email) is not yet implemented");
        return List.of();
    }

    public List<MessageResponse> getAllByReceiver(MsgBySmsRequest req) {
        System.err.println("getAllSentBy (sms) is not yet implemented");
        return List.of();
    }
}
