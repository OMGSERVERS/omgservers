package com.omgservers.service.shard.docker.impl;

import com.omgservers.service.shard.docker.DockerShard;
import com.omgservers.service.shard.docker.impl.service.dockerService.DockerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DockerShardImpl implements DockerShard {

    final DockerService service;
}
