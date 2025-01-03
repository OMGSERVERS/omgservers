package com.omgservers.dispatcher.service.router.dto;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
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
