package com.omgservers.schema.shard.root.root;

import com.omgservers.schema.model.root.RootModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRootResponse {

    RootModel root;
}
