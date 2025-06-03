package com.omgservers.service.entrypoint.connector.impl.service.connectorService.impl.method;

import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenConnectorResponse> execute(CreateTokenConnectorRequest request);
}
