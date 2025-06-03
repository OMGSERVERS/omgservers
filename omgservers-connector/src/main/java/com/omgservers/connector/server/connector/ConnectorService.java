package com.omgservers.connector.server.connector;

import com.omgservers.connector.server.connector.dto.CreateConnectorRequest;
import com.omgservers.connector.server.connector.dto.CreateConnectorResponse;
import com.omgservers.connector.server.connector.dto.DeleteConnectorRequest;
import com.omgservers.connector.server.connector.dto.DeleteConnectorResponse;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesRequest;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesResponse;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageRequest;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ConnectorService {

    Uni<CreateConnectorResponse> execute(@Valid CreateConnectorRequest request);

    Uni<DeleteConnectorResponse> execute(@Valid DeleteConnectorRequest request);

    Uni<ReceiveTextMessageResponse> execute(@Valid ReceiveTextMessageRequest request);

    Uni<InterchangeMessagesResponse> execute(@Valid InterchangeMessagesRequest request);
}
