package com.omgservers.application.module.adminModule.impl;

import com.omgservers.application.module.adminModule.AdminModule;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.AdminHelpService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AdminModuleImpl implements AdminModule {

    final AdminHelpService adminHelpService;

    @Override
    public AdminHelpService getAdminHelpService() {
        return adminHelpService;
    }
}
