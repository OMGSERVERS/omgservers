package com.omgservers.dispatcher.service.dispatcher.dto;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
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
