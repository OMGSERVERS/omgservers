package com.omgservers.schema.model.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientConfigDto {

    static public ClientConfigDto create() {
        final var clientConfig = new ClientConfigDto();
        clientConfig.setVersion(ClientConfigVersionEnum.V1);
        return clientConfig;
    }

    @NotNull
    ClientConfigVersionEnum version;
}
