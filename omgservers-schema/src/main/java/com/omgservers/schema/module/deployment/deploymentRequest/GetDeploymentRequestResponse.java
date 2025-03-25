package com.omgservers.schema.module.deployment.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentRequestResponse {

    DeploymentRequestModel deploymentRequest;
}
