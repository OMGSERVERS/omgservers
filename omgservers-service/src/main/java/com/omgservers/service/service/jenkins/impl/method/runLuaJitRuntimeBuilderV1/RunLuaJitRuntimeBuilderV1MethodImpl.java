package com.omgservers.service.service.jenkins.impl.method.runLuaJitRuntimeBuilderV1;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Response;
import com.omgservers.service.service.jenkins.operation.GetJenkinsClientOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class RunLuaJitRuntimeBuilderV1MethodImpl implements RunLuaJitRuntimeBuilderV1Method {

    final GetJenkinsClientOperation getJenkinsClientOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<RunLuaJitRuntimeBuilderV1Response> runLuaJitRuntimeBuilderV1(
            final RunLuaJitRuntimeBuilderV1Request request) {
        log.trace("{}", request);

        final var builderUri = getServiceConfigOperation.getServiceConfig().builder().uri();
        final var jenkinsClient = getJenkinsClientOperation.getClient(builderUri);

        final var groupId = request.getGroupId();
        final var containerName = request.getContainerName();
        final var versionId = request.getVersionId();
        final var base64Archive = request.getBase64Archive();

        return jenkinsClient.runLuaJitRuntimeBuilderV1(groupId, containerName, versionId, base64Archive)
                .map(response -> {
                    final var locationHeader = response.getHeaderString("Location");
                    log.debug("Got jenkins response, locationHeader={}", locationHeader);
                    final var itemNumber = parseItemNumber(locationHeader);
                    return itemNumber;
                })
                // TODO: use more smart approach
                .onItem().delayIt().by(Duration.ofSeconds(1))
                .flatMap(itemNumber -> jenkinsClient.getQueueItem(itemNumber)
                        .map(response -> {
                            log.debug("Got queue item, response={}", response);

                            final var cancelled = response.getCancelled();
                            if (Objects.nonNull(cancelled) && cancelled.equals(Boolean.TRUE)) {
                                throw new ServerSideBadRequestException(
                                        ExceptionQualifierEnum.JENKINS_REQUEST_FAILED, "Job was cancelled");
                            }

                            if (Objects.isNull(response.getExecutable())) {
                                throw new ServerSideBadRequestException(
                                        ExceptionQualifierEnum.JENKINS_REQUEST_FAILED,
                                        "Job executable was not found");
                            }

                            final var buildNumber = response.getExecutable().getNumber();
                            if (Objects.isNull(buildNumber)) {
                                throw new ServerSideBadRequestException(
                                        ExceptionQualifierEnum.JENKINS_REQUEST_FAILED,
                                        "Build number was not found");
                            }

                            log.debug("LuaJit jenkins jobs \"{}\" started", buildNumber);

                            return buildNumber;
                        }))
                .map(RunLuaJitRuntimeBuilderV1Response::new);
    }

    Integer parseItemNumber(final String locationHeader) {
        // Example - "http://localhost:7070/queue/item/15/"

        if (Objects.isNull(locationHeader)) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_REQUEST_FAILED,
                    "Location header was not found");
        }

        final var parts = locationHeader.split("/");
        if (parts.length == 0) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_REQUEST_FAILED,
                    "Location header has wrong format, " + locationHeader);
        }

        try {
            final var itemNumber = Integer.valueOf(parts[parts.length - 1]);
            return itemNumber;
        } catch (NumberFormatException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_REQUEST_FAILED,
                    "Item number couldn't be parsed, " + locationHeader);
        }
    }
}
