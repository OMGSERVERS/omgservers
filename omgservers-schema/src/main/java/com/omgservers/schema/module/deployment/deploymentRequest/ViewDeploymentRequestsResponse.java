package com.omgservers.schema.module.deployment.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentRequestsResponse {

    List<DeploymentRequestModel> deploymentRequests;
}
