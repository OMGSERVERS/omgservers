package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientBinaryMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferClientBinaryMessageMethod {

    Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(TransferClientBinaryMessageRequest request);
}
