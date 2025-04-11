package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.TransferTextMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.TransferTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferTextMessageMethod {
    Uni<TransferTextMessageResponse> execute(TransferTextMessageRequest request);
}
