
package com.omgservers.model.dto.runtime.serverRuntimeRef;

import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeServerContainerRefResponse {

    RuntimeServerContainerRefModel runtimeServerContainerRef;
}
