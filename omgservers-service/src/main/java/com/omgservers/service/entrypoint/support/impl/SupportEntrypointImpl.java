package com.omgservers.service.entrypoint.support.impl;

import com.omgservers.service.entrypoint.support.SupportEntrypoint;
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SupportEntrypointImpl implements SupportEntrypoint {

    final SupportService supportService;

    public SupportService getService() {
        return supportService;
    }
}
