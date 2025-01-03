package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.TransferServerTextMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferServerTextMessageMethod {

    Uni<TransferServerTextMessageResponse> execute(TransferServerTextMessageRequest request);
}
