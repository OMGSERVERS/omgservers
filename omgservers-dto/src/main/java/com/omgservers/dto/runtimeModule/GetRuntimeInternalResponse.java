package com.omgservers.dto.runtimeModule;

import com.omgservers.model.runtime.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeInternalResponse {

    RuntimeModel runtime;
}
