package com.omgservers.service.factory.client;

import com.omgservers.model.message.MessageBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MessageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MessageModel create(final MessageQualifierEnum qualifier, final MessageBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, qualifier, body);
    }

    public MessageModel create(final Long id,
                               final MessageQualifierEnum qualifier,
                               final MessageBodyModel body) {
        MessageModel message = new MessageModel();
        message.setId(id);
        message.setQualifier(qualifier);
        message.setBody(body);
        return message;
    }
}
