package com.omgservers.service.server.job.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindJobRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;
}
