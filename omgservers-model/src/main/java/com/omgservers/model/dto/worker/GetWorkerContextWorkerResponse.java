package com.omgservers.model.dto.worker;

import com.omgservers.model.workerContext.WorkerContextModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkerContextWorkerResponse {

    WorkerContextModel workerContext;
}
