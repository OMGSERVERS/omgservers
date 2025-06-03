package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferBinaryMessageMethod {
    Uni<TransferBinaryMessageResponse> execute(TransferBinaryMessageRequest request);
}
