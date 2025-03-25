package com.omgservers.schema.model.deploymentCommand;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class DeploymentCommandDeserializer extends StdDeserializer<DeploymentCommandModel> {

    public DeploymentCommandDeserializer() {
        this(null);
    }

    public DeploymentCommandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DeploymentCommandModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    DeploymentCommandModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var tenantDeploymentCommandModel = new DeploymentCommandModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            tenantDeploymentCommandModel.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            tenantDeploymentCommandModel.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var deploymentIdNode = root.get("deployment_id");
        if (deploymentIdNode != null) {
            tenantDeploymentCommandModel.setDeploymentId(Long.valueOf(deploymentIdNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            tenantDeploymentCommandModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            tenantDeploymentCommandModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = DeploymentCommandQualifierEnum.valueOf(qualifierNode.asText());
            tenantDeploymentCommandModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                tenantDeploymentCommandModel.setBody(body);
            }
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            tenantDeploymentCommandModel.setDeleted(Boolean.valueOf(deletedNode.asText()));
        }

        return tenantDeploymentCommandModel;
    }
}
