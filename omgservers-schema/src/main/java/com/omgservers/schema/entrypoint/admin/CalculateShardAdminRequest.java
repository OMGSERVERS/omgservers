package com.omgservers.schema.entrypoint.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateShardAdminRequest {

    @NotNull
    String shardKey;
}
