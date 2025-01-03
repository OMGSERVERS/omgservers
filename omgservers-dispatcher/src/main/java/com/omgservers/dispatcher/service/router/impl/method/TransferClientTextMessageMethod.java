package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.TransferClientTextMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferClientTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientTextMessageMethod {

    Uni<TransferClientTextMessageResponse> execute(TransferClientTextMessageRequest request);
}
