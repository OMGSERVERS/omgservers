package com.omgservers.dispatcher.service.router.dto;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferServerTextMessageRequest {

    @NotNull
    DispatcherConnection serverConnection;

    @NotNull
    String message;
}
