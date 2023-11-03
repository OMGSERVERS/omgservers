package com.omgservers.model.dto.system;

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
