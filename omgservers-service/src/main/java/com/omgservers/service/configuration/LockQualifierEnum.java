package com.omgservers.service.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LockQualifierEnum {
    BOOTSTRAP(AdvisoryLockConfiguration.MAGIC_NUM + 1),
    EVENT_HANDLER(AdvisoryLockConfiguration.MAGIC_NUM + 2),
    SCHEDULER(AdvisoryLockConfiguration.MAGIC_NUM + 3);

    final long key;
}
