package com.omgservers.service.factory.message;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MessageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MessageModel create(final ClientMessageModel clientMessage) {
        return create(clientMessage.getQualifier(), clientMessage.getBody());
    }

    public MessageModel create(final MessageQualifierEnum qualifier,
                               final MessageBodyDto body) {
        final var message = new MessageModel();
        message.setQualifier(qualifier);
        message.setBody(body);
        return message;
    }
}
