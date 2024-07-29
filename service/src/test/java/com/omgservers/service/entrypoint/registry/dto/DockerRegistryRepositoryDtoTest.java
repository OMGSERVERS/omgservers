package com.omgservers.service.entrypoint.registry.dto;

import com.omgservers.schema.service.registry.DockerRegistryContainerQualifierEnum;
import com.omgservers.schema.service.registry.DockerRegistryRepositoryDto;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DockerRegistryRepositoryDtoTest extends Assertions {

    @Test
    void givenDockerRepositoryModel_whenToString_thenEquals() {
        final var repositoryModel = new DockerRegistryRepositoryDto("omgservers",
                245515657456648192L,
                231077687903387648L,
                231939082811342849L,
                DockerRegistryContainerQualifierEnum.UNIVERSAL);
        final var repositoryString = repositoryModel.toString();
        assertEquals("omgservers/245515657456648192/231077687903387648/231939082811342849/universal",
                repositoryString);
    }
}