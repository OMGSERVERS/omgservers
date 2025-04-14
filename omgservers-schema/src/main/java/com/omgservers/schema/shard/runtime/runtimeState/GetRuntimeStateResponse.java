package com.omgservers.schema.shard.runtime.runtimeState;

import com.omgservers.schema.model.runtimeState.RuntimeStateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeStateResponse {

    RuntimeStateDto runtimeState;
}
