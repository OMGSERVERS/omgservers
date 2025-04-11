package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatcher;
import io.smallrye.mutiny.Uni;

public interface TransferToRuntimeOperation {

    Uni<Boolean> execute(Dispatcher dispatcher,
                         Long clientId,
                         MessageEncodingEnum messageEncoding,
                         String playerMessage);
}
