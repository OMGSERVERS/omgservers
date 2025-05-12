package com.omgservers.service.master.node.impl.operation;

import java.net.URI;

public interface GetNodeMasterClientOperation {
    NodeMasterClient execute(URI uri);
}
