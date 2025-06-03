package com.omgservers.dispatcher.server.dispatcher.dto;

import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemovePlayerConnectionRequest {

    @Valid
    @NotNull
    DispatcherConnection playerConnection;
}
