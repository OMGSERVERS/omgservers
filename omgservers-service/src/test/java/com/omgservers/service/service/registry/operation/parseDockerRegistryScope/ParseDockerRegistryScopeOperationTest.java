package com.omgservers.service.service.registry.operation.parseDockerRegistryScope;

import com.omgservers.BaseTestClass;
import com.omgservers.service.service.registry.dto.DockerRegistryActionEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryContainerQualifierEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceTypeEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ParseDockerRegistryScopeOperationTest extends BaseTestClass {

    @Inject
    ParseDockerRegistryScopeOperation parseDockerRegistryScopeOperation;

    @Test
    void givenRepositoryScope() {
        final var scope =
                "repository:omgservers/245515657456648192/231077687903387648/universal:push,pull " +
                        "repository(plugin):localhost:5000/omgservers/245515657456648193/231077687903387649/lobby:pull";
        final var dockerRegistryScope = parseDockerRegistryScopeOperation.parseDockerRegistryScope(scope);
        assertEquals(2, dockerRegistryScope.getResourceScopes().size());

        final var resourceScope1 = dockerRegistryScope.getResourceScopes().getFirst();
        assertEquals(DockerRegistryResourceTypeEnum.REPOSITORY, resourceScope1.getResourceType());
        assertNull(resourceScope1.getResourceName().getHostname());
        assertEquals("omgservers", resourceScope1.getResourceName().getRepository().getNamespace());
        assertEquals("245515657456648192", resourceScope1.getResourceName().getRepository().getTenant());
        assertEquals("231077687903387648", resourceScope1.getResourceName().getRepository().getProject());
        assertEquals(DockerRegistryContainerQualifierEnum.UNIVERSAL,
                resourceScope1.getResourceName().getRepository().getContainer());
        assertEquals(2, resourceScope1.getActions().size());
        assertEquals(DockerRegistryActionEnum.PUSH, resourceScope1.getActions().getFirst());
        assertEquals(DockerRegistryActionEnum.PULL, resourceScope1.getActions().getLast());

        final var resourceScope2 = dockerRegistryScope.getResourceScopes().getLast();
        assertEquals(DockerRegistryResourceTypeEnum.REPOSITORY_PLUGIN, resourceScope2.getResourceType());
        assertEquals("localhost:5000", resourceScope2.getResourceName().getHostname());
        assertEquals("omgservers", resourceScope2.getResourceName().getRepository().getNamespace());
        assertEquals("245515657456648193", resourceScope2.getResourceName().getRepository().getTenant());
        assertEquals("231077687903387649", resourceScope2.getResourceName().getRepository().getProject());
        assertEquals(DockerRegistryContainerQualifierEnum.LOBBY,
                resourceScope2.getResourceName().getRepository().getContainer());
        assertEquals(1, resourceScope2.getActions().size());
        assertEquals(DockerRegistryActionEnum.PULL, resourceScope2.getActions().getLast());
    }
}