package com.omgservers.ctl.operation.wal.support;

import com.omgservers.ctl.dto.log.body.SupportTokenLogLineBodyDto;
import com.omgservers.ctl.dto.wal.WalDto;

public interface FindSupportTokenOperation {

    SupportTokenLogLineBodyDto execute(WalDto wal, String service);

    SupportTokenLogLineBodyDto execute(WalDto wal, String service, String user);
}
