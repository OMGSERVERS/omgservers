package com.omgservers.dispatcher.service.router.dto;

import com.omgservers.dispatcher.service.dispatcher.component.DispatcherConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteServerConnectionRequest {

    @NotNull
    DispatcherConnection serverConnection;

    @NotNull
    URI serverUri;
}
