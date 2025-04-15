package com.omgservers.service.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobQualifierEnum {
    BOOTSTRAP("bootstrap", LockQualifierEnum.BOOTSTRAP),
    EVENT_HANDLER("event-handler", LockQualifierEnum.EVENT_HANDLER),
    SCHEDULER("scheduler", LockQualifierEnum.SCHEDULER);

    final String qualifier;
    final LockQualifierEnum lock;
}
