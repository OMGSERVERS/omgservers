package com.omgservers.connector.server.connector.impl.method;

import com.omgservers.connector.server.connector.dto.CreateConnectorRequest;
import com.omgservers.connector.server.connector.dto.CreateConnectorResponse;
import io.smallrye.mutiny.Uni;

public interface CreateConnectorMethod {

    Uni<CreateConnectorResponse> execute(CreateConnectorRequest request);
}
