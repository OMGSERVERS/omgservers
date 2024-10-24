package com.omgservers.schema.module.runtime.poolContainerRef;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimePoolContainerRefResponse {

    RuntimePoolContainerRefModel runtimePoolContainerRef;
}
