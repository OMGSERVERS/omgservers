package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response;

import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeInternalResponse {

    RuntimeModel runtime;
}
