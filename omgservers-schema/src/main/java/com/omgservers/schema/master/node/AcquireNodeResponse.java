package com.omgservers.schema.master.node;

import com.omgservers.schema.model.node.NodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireNodeResponse {
    NodeModel node;
}
