package com.omgservers.service.factory.client;

import com.omgservers.schema.model.message.MessageBodyDto;
import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MessageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MessageModel create(final MessageQualifierEnum qualifier, final MessageBodyDto body) {
        final var id = generateIdOperation.generateId();
        return create(id, qualifier, body);
    }

    public MessageModel create(final Long id,
                               final MessageQualifierEnum qualifier,
                               final MessageBodyDto body) {
        MessageModel message = new MessageModel();
        message.setId(id);
        message.setQualifier(qualifier);
        message.setBody(body);
        return message;
    }
}
