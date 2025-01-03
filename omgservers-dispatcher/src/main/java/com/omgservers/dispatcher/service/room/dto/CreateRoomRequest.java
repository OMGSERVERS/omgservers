package com.omgservers.dispatcher.service.room.dto;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {

    @NotNull
    DispatcherConnection runtimeConnection;
}
