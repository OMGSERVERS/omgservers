package com.omgservers.service.module.dispatcher.impl.service.routerService.dto;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
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
