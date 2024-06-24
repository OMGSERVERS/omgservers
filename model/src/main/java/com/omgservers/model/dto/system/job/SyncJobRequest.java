package com.omgservers.model.dto.system.job;

import com.omgservers.model.job.JobModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncJobRequest {

    @NotNull
    JobModel job;
}
