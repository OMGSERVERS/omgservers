package com.omgservers.service.service.router.impl.method.transferClientTextMessage;

import com.omgservers.service.service.router.dto.TransferClientTextMessageRequest;
import com.omgservers.service.service.router.dto.TransferClientTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientTextMessageMethod {

    Uni<TransferClientTextMessageResponse> transferClientTextMessage(TransferClientTextMessageRequest request);
}
