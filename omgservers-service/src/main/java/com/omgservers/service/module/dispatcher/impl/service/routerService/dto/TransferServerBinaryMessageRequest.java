package com.omgservers.service.module.dispatcher.impl.service.routerService.dto;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferServerBinaryMessageRequest {

    @NotNull
    DispatcherConnection serverConnection;

    @NotNull
    Buffer buffer;
}
