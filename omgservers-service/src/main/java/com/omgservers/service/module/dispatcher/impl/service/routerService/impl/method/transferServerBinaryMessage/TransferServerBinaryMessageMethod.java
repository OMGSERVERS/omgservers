package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerBinaryMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferServerBinaryMessageMethod {

    Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(TransferServerBinaryMessageRequest request);
}
