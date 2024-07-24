package com.omgservers.service.integration.jenkins.impl.service.jenkinsService.impl.method.getLuaJitWorkerBuilderV1;

import com.omgservers.model.dto.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.model.dto.jenkins.GetLuaJitWorkerBuilderV1Response;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.integration.jenkins.impl.operation.getJenkinsClient.GetJenkinsClientOperation;
import com.omgservers.service.integration.jenkins.impl.operation.getJenkinsClient.dto.getJobByBuildNumber.ResultEnum;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GetLuaJitWorkerBuilderV1MethodImpl implements GetLuaJitWorkerBuilderV1Method {

    final GetJenkinsClientOperation getJenkinsClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<GetLuaJitWorkerBuilderV1Response> getLuaJitWorkerBuilderV1(GetLuaJitWorkerBuilderV1Request request) {
        final var builderUri = getConfigOperation.getServiceConfig().builder().uri();
        final var jenkinsClient = getJenkinsClientOperation.getClient(builderUri);

        final var buildNumber = request.getBuildNumber();

        return jenkinsClient.getLuaJitWorkerBuilderV1(buildNumber)
                .flatMap(response -> {
                    if (Objects.nonNull(response.getInProgress()) && response.getInProgress().equals(Boolean.TRUE)) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_JOB_UNFINISHED,
                                "jenkins job is still in progress");
                    }

                    if (Objects.nonNull(response.getResult()) && !response.getResult().equals(ResultEnum.SUCCESS)) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_JOB_FAILED,
                                "jenkins job failed, result=" + response.getResult());
                    }

                    return jenkinsClient.getLuaJitWorkerBuilderV1ImageArtifact(buildNumber);
                })
                .map(GetLuaJitWorkerBuilderV1Response::new);
    }
}
