package com.omgservers.service.module.dispatcher.impl.service.roomService.dto;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRoomTextMessageRequest {

    @NotNull
    DispatcherConnection dispatcherConnection;

    @NotNull
    String message;
}
