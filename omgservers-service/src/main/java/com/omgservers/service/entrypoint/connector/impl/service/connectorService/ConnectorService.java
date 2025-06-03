package com.omgservers.service.entrypoint.connector.impl.service.connectorService;

import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ConnectorService {

    Uni<CreateTokenConnectorResponse> execute(@Valid CreateTokenConnectorRequest request);

    Uni<InterchangeMessagesConnectorResponse> execute(@Valid InterchangeMessagesConnectorRequest request);
}
