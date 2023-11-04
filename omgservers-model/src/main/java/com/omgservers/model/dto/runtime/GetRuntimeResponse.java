package com.omgservers.model.dto.runtime;

import com.omgservers.model.runtime.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeResponse {

    RuntimeModel runtime;
}
