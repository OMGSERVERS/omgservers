package com.omgservers.connector.server.task.impl.method.executeMessageInterchangerTask;

import com.omgservers.connector.operation.Task;
import com.omgservers.connector.server.connector.ConnectorService;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MessageInterchangerTaskImpl implements Task<MessageInterchangerTaskArguments> {

    final ConnectorService connectorService;

    @Override
    public Uni<Boolean> execute(final MessageInterchangerTaskArguments taskArguments) {
        return connectorService.execute(new InterchangeMessagesRequest())
                .replaceWith(Boolean.FALSE);
    }
}
