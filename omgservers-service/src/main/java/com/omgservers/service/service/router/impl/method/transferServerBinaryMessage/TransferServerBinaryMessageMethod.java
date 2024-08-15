package com.omgservers.service.service.router.impl.method.transferServerBinaryMessage;

import com.omgservers.service.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.service.router.dto.TransferServerBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferServerBinaryMessageMethod {

    Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(TransferServerBinaryMessageRequest request);
}
