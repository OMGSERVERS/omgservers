package com.omgservers.service.master.node.impl;

import com.omgservers.service.master.node.NodeMaster;
import com.omgservers.service.master.node.impl.service.nodeService.NodeService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class NodeMasterImpl implements NodeMaster {

    final NodeService nodeService;

    @Override
    public NodeService getService() {
        return nodeService;
    }
}
