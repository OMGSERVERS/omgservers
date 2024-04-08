package com.omgservers.model.dto.server;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootstrapIndexServerRequest {

    @NotNull
    Integer shardCount;

    @NotEmpty
    List<URI> servers;
}
