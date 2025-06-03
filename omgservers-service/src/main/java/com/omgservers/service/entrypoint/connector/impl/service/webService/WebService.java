package com.omgservers.service.entrypoint.connector.impl.service.webService;

import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenConnectorResponse> execute(CreateTokenConnectorRequest request);

    Uni<InterchangeMessagesConnectorResponse> execute(InterchangeMessagesConnectorRequest request);
}
