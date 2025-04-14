package com.omgservers.schema.shard.runtime.runtime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeResponse {

    RuntimeModel runtime;
}
