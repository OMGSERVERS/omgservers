package com.omgservers.module.gateway.impl.service.connectionService.response;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignedRuntimeResponse {

    AssignedRuntimeModel assignedRuntime;
}
