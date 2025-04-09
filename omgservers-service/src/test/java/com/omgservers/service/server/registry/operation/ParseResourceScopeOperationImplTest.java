package com.omgservers.service.server.registry.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.server.registry.dto.RegistryActionEnum;
import com.omgservers.service.server.registry.dto.RegistryResourceTypeEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ParseResourceScopeOperationImplTest extends BaseTestClass {

    @Inject
    ParseResourceScopeOperation parseResourceScopeOperation;

    @Test
    void givenResourceScopeWithNamespace_thenParsed() {
        final var resourceScope = "repository:namespace/image:pull,push";

        final var result = parseResourceScopeOperation.execute(resourceScope);

        assertEquals(RegistryResourceTypeEnum.REPOSITORY, result.resourceType());
        assertEquals("namespace", result.namespace());
        assertEquals("image", result.image());
        assertEquals(2, result.actions().size());
        assertTrue(result.actions().contains(RegistryActionEnum.PULL));
        assertTrue(result.actions().contains(RegistryActionEnum.PUSH));
    }

    @Test
    void givenResourceScopeWithoutNamespace_thenParsed() {
        final var resourceScope = "repository:image:pull,push";

        final var result = parseResourceScopeOperation.execute(resourceScope);

        assertEquals(RegistryResourceTypeEnum.REPOSITORY, result.resourceType());
        assertEquals("image", result.image());
        assertEquals(2, result.actions().size());
        assertTrue(result.actions().contains(RegistryActionEnum.PULL));
        assertTrue(result.actions().contains(RegistryActionEnum.PUSH));
    }

    @Test
    void givenResourceScopeWithSingleAction_thenParsed() {
        final var resourceScope = "repository:namespace/image:push";

        final var result = parseResourceScopeOperation.execute(resourceScope);

        assertEquals(RegistryResourceTypeEnum.REPOSITORY, result.resourceType());
        assertEquals("namespace", result.namespace());
        assertEquals("image", result.image());
        assertEquals(1, result.actions().size());
        assertTrue(result.actions().contains(RegistryActionEnum.PUSH));
    }

    @Test
    void givenResourceScopeWithSubcategory_thenParsed() {
        final var resourceScope = "repository:namespace/subcategory/image:pull,push";

        final var result = parseResourceScopeOperation.execute(resourceScope);

        assertEquals(RegistryResourceTypeEnum.REPOSITORY, result.resourceType());
        assertEquals("namespace", result.namespace());
        assertEquals("subcategory/image", result.image());
        assertEquals(2, result.actions().size());
        assertTrue(result.actions().contains(RegistryActionEnum.PULL));
        assertTrue(result.actions().contains(RegistryActionEnum.PUSH));
    }

    @Test
    void givenResourceScopeWithRepositoryPluginType_thenParsed() {
        final var resourceScope = "repository(plugin):namespace/subcategory/image:pull,push";

        final var result = parseResourceScopeOperation.execute(resourceScope);

        assertEquals(RegistryResourceTypeEnum.REPOSITORY_PLUGIN, result.resourceType());
        assertEquals("namespace", result.namespace());
        assertEquals("subcategory/image", result.image());
        assertEquals(2, result.actions().size());
        assertTrue(result.actions().contains(RegistryActionEnum.PULL));
        assertTrue(result.actions().contains(RegistryActionEnum.PUSH));
    }

    @Test
    void givenResourceScopeWithWrongResourceType_thenException() {
        final var resourceScope = "wrongtype:namespace/image:pull,push";

        assertThrows(ServerSideBadRequestException.class, () -> parseResourceScopeOperation.execute(resourceScope));
    }

    @Test
    void givenResourceScopeWithoutActions_thenException() {
        final var resourceScope = "repository:namespace/image:";

        assertThrows(ServerSideBadRequestException.class, () -> parseResourceScopeOperation.execute(resourceScope));
    }
}