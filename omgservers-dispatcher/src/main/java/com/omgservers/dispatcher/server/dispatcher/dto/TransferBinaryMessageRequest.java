package com.omgservers.dispatcher.server.dispatcher.dto;

import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferBinaryMessageRequest {

    @Valid
    @NotNull
    DispatcherConnection dispatcherConnection;

    @NotNull
    Buffer buffer;
}
