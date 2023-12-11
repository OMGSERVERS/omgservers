package com.omgservers.service.module.admin.impl.service.adminService.testInterface;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import com.omgservers.service.module.admin.impl.service.adminService.AdminService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final AdminService adminService;

    public PingServerAdminResponse pingServer() {
        return adminService.pingServer()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GenerateIdAdminResponse generateId() {
        return adminService.generateId()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public BcryptHashAdminResponse bcryptHash(BcryptHashAdminRequest request) {
        return adminService.bcryptHash(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindIndexAdminResponse findIndex(FindIndexAdminRequest request) {
        return adminService.findIndex(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public CreateIndexAdminResponse createIndex(CreateIndexAdminRequest request) {
        return adminService.createIndex(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncIndexAdminResponse syncIndex(SyncIndexAdminRequest request) {
        return adminService.syncIndex(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

}
