package com.omgservers.service.entrypoint.docker.impl;

import com.omgservers.service.entrypoint.docker.DockerEntrypoint;
import com.omgservers.service.entrypoint.docker.impl.service.dockerService.DockerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DockerEntrypointImpl implements DockerEntrypoint {

    final DockerService service;
}
