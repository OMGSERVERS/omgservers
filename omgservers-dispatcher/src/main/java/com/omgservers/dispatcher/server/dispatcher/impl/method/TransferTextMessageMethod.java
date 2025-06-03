package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferTextMessageMethod {
    Uni<TransferTextMessageResponse> execute(TransferTextMessageRequest request);
}
