package com.omgservers.model.dto.worker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkerContextWorkerRequest {

    @NotNull
    Long runtimeId;
}
