package com.omgservers.service.server.service.jenkins.impl.method.getLuaJitWorkerBuilderV1;

import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Response;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.server.service.jenkins.operation.GetJenkinsClientOperation;
import com.omgservers.service.server.service.jenkins.dto.getJobByBuildNumber.ResultEnum;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
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
