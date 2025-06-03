package com.omgservers.connector.server.connector.impl.method;

import com.omgservers.connector.server.connector.dto.DeleteConnectorRequest;
import com.omgservers.connector.server.connector.dto.DeleteConnectorResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteConnectorMethod {

    Uni<DeleteConnectorResponse> execute(DeleteConnectorRequest request);
}
