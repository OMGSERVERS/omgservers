
package com.omgservers.schema.shard.client.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientRuntimeRefResponse {

    ClientRuntimeRefModel clientRuntimeRef;
}
