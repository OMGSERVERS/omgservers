package com.omgservers.model.dto.runtime;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimeClientResponse {

    RuntimeClientModel runtimeClient;
}
