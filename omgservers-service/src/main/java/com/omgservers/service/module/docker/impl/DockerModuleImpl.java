package com.omgservers.service.module.docker.impl;

import com.omgservers.service.module.docker.DockerModule;
import com.omgservers.service.module.docker.impl.service.dockerService.DockerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DockerModuleImpl implements DockerModule {

    final DockerService service;
}
