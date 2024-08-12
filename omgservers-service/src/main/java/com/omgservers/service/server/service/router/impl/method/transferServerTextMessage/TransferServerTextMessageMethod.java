package com.omgservers.service.server.service.router.impl.method.transferServerTextMessage;

import com.omgservers.service.server.service.router.dto.TransferServerTextMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferServerTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferServerTextMessageMethod {

    Uni<TransferServerTextMessageResponse> transferServerTextMessage(TransferServerTextMessageRequest request);
}
