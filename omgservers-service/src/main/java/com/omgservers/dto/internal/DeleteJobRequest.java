package com.omgservers.dto.internal;

import com.omgservers.model.job.JobQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteJobRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;

    @NotNull
    JobQualifierEnum qualifier;
}
