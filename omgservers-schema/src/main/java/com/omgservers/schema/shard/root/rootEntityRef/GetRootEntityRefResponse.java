package com.omgservers.schema.shard.root.rootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRootEntityRefResponse {

    RootEntityRefModel rootEntityRef;
}
