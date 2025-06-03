package com.omgservers.connector.server.connector.impl.method;

import com.omgservers.connector.server.connector.dto.InterchangeMessagesRequest;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMessagesMethod {
    Uni<InterchangeMessagesResponse> execute(InterchangeMessagesRequest request);
}
