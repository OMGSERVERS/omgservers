package com.omgservers.schema.entrypoint.worker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConfigWorkerRequest {

    @NotNull
    Long runtimeId;
}