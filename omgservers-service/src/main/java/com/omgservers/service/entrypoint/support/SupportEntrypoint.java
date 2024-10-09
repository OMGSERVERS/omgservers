package com.omgservers.service.entrypoint.support;

import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;

public interface SupportEntrypoint {

    SupportService getService();
}