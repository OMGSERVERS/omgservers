package com.omgservers.service.operation.parseDockerRepository;

import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.service.registry.dto.DockerRegistryContainerQualifierEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ParseDockerRegistryRepositoryOperationTest extends Assertions {

    @Inject
    ParseDockerRepositoryOperation parseDockerRepositoryOperation;

    @Test
    void givenUniversal_whenParseDockerRegistryRepository_thenParsed() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/universal";
        final var repositoryModel = parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
        assertEquals("omgservers", repositoryModel.getNamespace());
        assertEquals(245515657456648192L, repositoryModel.getTenantId());
        assertEquals(231077687903387648L, repositoryModel.getProjectId());
        assertEquals(DockerRegistryContainerQualifierEnum.UNIVERSAL, repositoryModel.getContainer());
    }

    @Test
    void givenLobby_whenParseDockerRegistryRepository_thenParsed() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/lobby";
        final var repositoryModel = parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
        assertEquals("omgservers", repositoryModel.getNamespace());
        assertEquals(245515657456648192L, repositoryModel.getTenantId());
        assertEquals(231077687903387648L, repositoryModel.getProjectId());
        assertEquals(DockerRegistryContainerQualifierEnum.LOBBY, repositoryModel.getContainer());
    }

    @Test
    void givenMatch_whenParseDockerRegistryRepository_thenParsed() {
        final var repositoryString =
                "omgservers/245515657456648192/231077687903387648/match";
        final var repositoryModel = parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
        assertEquals("omgservers", repositoryModel.getNamespace());
        assertEquals(245515657456648192L, repositoryModel.getTenantId());
        assertEquals(231077687903387648L, repositoryModel.getProjectId());
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

    @Test
    void givenWrongTenantId_whenParseDockerRegistryRepository_thenException() {
        final var repositoryString =
                "omgservers/aaaaaaaaaaaaaaaaaa/231077687903387648/universal";
        assertThrows(ServerSideBadRequestException.class,
                () -> parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString));
    }

    @Test
    void givenWrongProjectId_whenParseDockerRegistryRepository_thenException() {
        final var repositoryString =
                "omgservers/245515657456648192/bbbbbbbbbbbbbbbb/universal";
        assertThrows(ServerSideBadRequestException.class,
                () -> parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString));
    }
}