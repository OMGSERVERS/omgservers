package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerTextMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferServerTextMessageMethod {

    Uni<TransferServerTextMessageResponse> transferServerTextMessage(TransferServerTextMessageRequest request);
}
