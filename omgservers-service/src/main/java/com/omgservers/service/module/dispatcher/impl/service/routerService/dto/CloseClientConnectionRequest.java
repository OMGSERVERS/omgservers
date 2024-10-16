package com.omgservers.service.module.dispatcher.impl.service.routerService.dto;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import io.quarkus.websockets.next.CloseReason;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseClientConnectionRequest {

    @NotNull
    DispatcherConnection serverConnection;

    @NotNull
    CloseReason closeReason;
}
