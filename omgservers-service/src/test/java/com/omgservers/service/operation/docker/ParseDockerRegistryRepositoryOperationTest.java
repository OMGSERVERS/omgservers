package com.omgservers.service.operation.docker;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.service.registry.dto.DockerRegistryContainerQualifierEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ParseDockerRegistryRepositoryOperationTest extends BaseTestClass {

    @Inject
    ParseDockerRepositoryOperation parseDockerRepositoryOperation;

    @Test
    void givenUniversal_whenParseDockerRegistryRepository_thenParsed() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/universal";
        final var repositoryModel = parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
        assertEquals("omgservers", repositoryModel.getNamespace());
        assertEquals("245515657456648192", repositoryModel.getTenant());
        assertEquals("231077687903387648", repositoryModel.getProject());
        assertEquals(DockerRegistryContainerQualifierEnum.UNIVERSAL, repositoryModel.getContainer());
    }

    @Test
    void givenLobby_whenParseDockerRegistryRepository_thenParsed() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/lobby";
        final var repositoryModel = parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
        assertEquals("omgservers", repositoryModel.getNamespace());
        assertEquals("245515657456648192", repositoryModel.getTenant());
        assertEquals("231077687903387648", repositoryModel.getProject());
        assertEquals(DockerRegistryContainerQualifierEnum.LOBBY, repositoryModel.getContainer());
    }

    @Test
    void givenMatch_whenParseDockerRegistryRepository_thenParsed() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/match";
        final var repositoryModel = parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
        assertEquals("omgservers", repositoryModel.getNamespace());
        assertEquals("245515657456648192", repositoryModel.getTenant());
        assertEquals("231077687903387648", repositoryModel.getProject());
        assertEquals(DockerRegistryContainerQualifierEnum.MATCH, repositoryModel.getContainer());
    }

    @Test
    void givenUnknownQualifier_whenParseDockerRegistryRepository_thenException() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/unknown";
        assertThrows(ServerSideBadRequestException.class,
                () -> parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString));
    }

    @Test
    void givenMorePartsThatExpected_whenParseDockerRegistryRepository_thenException() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/231939082811342849/unknown";
        assertThrows(ServerSideBadRequestException.class,
                () -> parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString));
    }

    @Test
    void givenLessPartsThatExpected_whenParseDockerRegistryRepository_thenException() {
        final var repositoryString =
                "omgservers/231939082811342849/unknown";
        assertThrows(ServerSideBadRequestException.class,
                () -> parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString));
    }
}