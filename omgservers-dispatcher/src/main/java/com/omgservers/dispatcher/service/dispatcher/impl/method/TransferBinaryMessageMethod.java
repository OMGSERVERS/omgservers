package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.TransferBinaryMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.TransferBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferBinaryMessageMethod {
    Uni<TransferBinaryMessageResponse> execute(TransferBinaryMessageRequest request);
}
