package com.omgservers.model.dto.system.job;

import com.omgservers.model.job.JobModel;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetJobResponse {

    JobModel job;
}
