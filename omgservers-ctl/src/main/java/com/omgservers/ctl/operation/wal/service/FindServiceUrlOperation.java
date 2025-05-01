package com.omgservers.ctl.operation.wal.service;

import com.omgservers.ctl.dto.log.body.ServiceUrlLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindServiceUrlOperation {

    ServiceUrlLogLineBodyDto execute(WalDto wal);

    ServiceUrlLogLineBodyDto execute(WalDto wal, String name);
}
