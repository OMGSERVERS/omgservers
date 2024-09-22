package com.omgservers.service.service.registry.dto;

import com.omgservers.service.service.registry.dto.DockerRegistryContainerQualifierEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryRepositoryDto;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@QuarkusTest
class DockerRegistryRepositoryDtoTest {

    @Test
    void givenDockerRepositoryModel_whenToString_thenEquals() {
        final var repositoryModel = new DockerRegistryRepositoryDto("omgservers",
                245515657456648192L,
                231077687903387648L,
                DockerRegistryContainerQualifierEnum.UNIVERSAL);
        final var repositoryString = repositoryModel.toString();
        assertEquals("omgservers/245515657456648192/231077687903387648/universal",
                repositoryString);
    }
}