package com.omgservers.schema.model.pool;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolConfigDto {

    static public PoolConfigDto create() {
        final var poolConfig = new PoolConfigDto();
        poolConfig.setVersion(PoolConfigVersionEnum.V1);
        return poolConfig;
    }

    @NotNull
    PoolConfigVersionEnum version;
}
