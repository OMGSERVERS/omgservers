package com.omgservers.application.module.gatewayModule.model.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MessageDeserializer.class)
public class MessageModel {

    static public MessageModel create(MessageQualifierEnum qualifier, MessageBodyModel body) {
        MessageModel message = new MessageModel();
        message.setId(UUID.randomUUID().toString());
        message.setQualifier(qualifier);
        message.setBody(body);
        return message;
    }

    String id;
    MessageQualifierEnum qualifier;
    @ToString.Exclude
    MessageBodyModel body;
}
