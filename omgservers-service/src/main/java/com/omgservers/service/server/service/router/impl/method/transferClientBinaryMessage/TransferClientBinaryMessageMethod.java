package com.omgservers.service.server.service.router.impl.method.transferClientBinaryMessage;

import com.omgservers.service.server.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferClientBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientBinaryMessageMethod {

    Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(TransferClientBinaryMessageRequest request);
}
