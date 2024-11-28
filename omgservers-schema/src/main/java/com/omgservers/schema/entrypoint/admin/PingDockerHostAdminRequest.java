package com.omgservers.schema.entrypoint.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PingDockerHostAdminRequest {

    @NotNull
    URI dockerDaemonUri;
}
