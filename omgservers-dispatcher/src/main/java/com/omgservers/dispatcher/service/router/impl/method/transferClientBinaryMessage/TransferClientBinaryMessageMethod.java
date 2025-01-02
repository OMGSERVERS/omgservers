package com.omgservers.dispatcher.service.router.impl.method.transferClientBinaryMessage;

import com.omgservers.dispatcher.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferClientBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientBinaryMessageMethod {

    Uni<TransferClientBinaryMessageResponse> execute(TransferClientBinaryMessageRequest request);
}
