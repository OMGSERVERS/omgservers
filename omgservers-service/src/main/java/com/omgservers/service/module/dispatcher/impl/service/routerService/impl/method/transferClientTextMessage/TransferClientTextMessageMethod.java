package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientTextMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientTextMessageMethod {

    Uni<TransferClientTextMessageResponse> transferClientTextMessage(TransferClientTextMessageRequest request);
}
