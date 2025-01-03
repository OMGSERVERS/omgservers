package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferServerBinaryMessageMethod {

    Uni<TransferServerBinaryMessageResponse> execute(TransferServerBinaryMessageRequest request);
}
