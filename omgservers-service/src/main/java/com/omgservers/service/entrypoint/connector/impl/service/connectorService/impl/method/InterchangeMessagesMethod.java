package com.omgservers.service.entrypoint.connector.impl.service.connectorService.impl.method;

import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMessagesMethod {
    Uni<InterchangeMessagesConnectorResponse> execute(InterchangeMessagesConnectorRequest request);
}
