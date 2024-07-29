package com.omgservers.schema.entrypoint.router;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeServerUriRouterRequest {

    @NotNull
    Long runtimeId;
}
