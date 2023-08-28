package com.omgservers.factory;

import com.omgservers.model.message.MessageBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MessageModelFactory {

    public MessageModel create(final MessageQualifierEnum qualifier, final MessageBodyModel body) {
        final var id = UUID.randomUUID().toString();
        return create(id, qualifier, body);
    }

    public MessageModel create(final String id,
                               final MessageQualifierEnum qualifier,
                               final MessageBodyModel body) {
        MessageModel message = new MessageModel();
        message.setId(id);
        message.setQualifier(qualifier);
        message.setBody(body);
        return message;
    }
}
