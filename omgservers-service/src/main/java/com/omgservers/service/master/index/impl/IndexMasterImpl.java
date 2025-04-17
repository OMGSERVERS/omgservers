package com.omgservers.service.master.index.impl;

import com.omgservers.service.master.index.IndexMaster;
import com.omgservers.service.master.index.impl.service.indexService.IndexService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class IndexMasterImpl implements IndexMaster {

    final IndexService indexService;

    @Override
    public IndexService getService() {
        return indexService;
    }
}
