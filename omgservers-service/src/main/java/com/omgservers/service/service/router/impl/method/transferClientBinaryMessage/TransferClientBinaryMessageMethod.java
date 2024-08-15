package com.omgservers.service.service.router.impl.method.transferClientBinaryMessage;

import com.omgservers.service.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.service.router.dto.TransferClientBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientBinaryMessageMethod {

    Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(TransferClientBinaryMessageRequest request);
}
