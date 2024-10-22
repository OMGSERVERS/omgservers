package com.omgservers.service.service.jenkins.impl.method.getLuaJitRuntimeBuilderV1;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Response;
import com.omgservers.service.service.jenkins.dto.getJobByBuildNumber.ResultEnum;
import com.omgservers.service.service.jenkins.operation.GetJenkinsClientOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GetLuaJitRuntimeBuilderV1MethodImpl implements GetLuaJitRuntimeBuilderV1Method {

    final GetJenkinsClientOperation getJenkinsClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<GetLuaJitRuntimeBuilderV1Response> getLuaJitRuntimeBuilderV1(
            final GetLuaJitRuntimeBuilderV1Request request) {
        log.debug("Requested, {}", request);

        final var builderUri = getConfigOperation.getServiceConfig().builder().uri();
        final var jenkinsClient = getJenkinsClientOperation.getClient(builderUri);

        final var buildNumber = request.getBuildNumber();

        return jenkinsClient.getLuaJitRuntimeBuilderV1(buildNumber)
                .flatMap(response -> {
                    if (Objects.nonNull(response.getInProgress()) && response.getInProgress().equals(Boolean.TRUE)) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_JOB_UNFINISHED,
                                "jenkins job is still in progress");
                    }

                    if (Objects.nonNull(response.getResult()) && !response.getResult().equals(ResultEnum.SUCCESS)) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_JOB_FAILED,
                                "jenkins job failed, result=" + response.getResult());
                    }

                    return jenkinsClient.getLuaJitRuntimeBuilderV1ImageArtifact(buildNumber);
                })
                .map(GetLuaJitRuntimeBuilderV1Response::new);
    }
}
