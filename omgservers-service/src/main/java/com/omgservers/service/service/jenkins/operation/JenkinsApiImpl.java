package com.omgservers.service.service.jenkins.operation;

import com.omgservers.service.service.jenkins.dto.getJobByBuildNumber.GetJobByBuildNumberResponse;
import com.omgservers.service.service.jenkins.dto.getQueueItemResponse.GetQueueItemResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JenkinsApiImpl implements JenkinsApi {

    @Override
    public Uni<GetQueueItemResponse> getQueueItem(Integer itemNumber) {
        return null;
    }

    @Override
    public Uni<Response> runLuaJitWorkerBuilderV1(String groupId,
                                                  String containerName,
                                                  String versionId,
                                                  String base64Archive) {
        return null;
    }

    @Override
    public Uni<GetJobByBuildNumberResponse> getLuaJitWorkerBuilderV1(Integer buildNumber) {
        return null;
    }

    @Override
    public Uni<String> getLuaJitWorkerBuilderV1ImageArtifact(Integer buildNumber) {
        return null;
    }
}
