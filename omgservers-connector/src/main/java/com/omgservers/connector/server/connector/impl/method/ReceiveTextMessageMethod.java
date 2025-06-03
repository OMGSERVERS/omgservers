package com.omgservers.connector.server.connector.impl.method;

import com.omgservers.connector.server.connector.dto.ReceiveTextMessageRequest;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface ReceiveTextMessageMethod {
    Uni<ReceiveTextMessageResponse> execute(ReceiveTextMessageRequest request);
}
