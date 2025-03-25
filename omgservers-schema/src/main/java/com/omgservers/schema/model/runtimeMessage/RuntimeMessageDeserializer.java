package com.omgservers.schema.model.runtimeMessage;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.omgservers.schema.message.MessageQualifierEnum;

import java.io.IOException;
import java.time.Instant;

public class RuntimeMessageDeserializer extends StdDeserializer<RuntimeMessageModel> {

    public RuntimeMessageDeserializer() {
        this(null);
    }

    public RuntimeMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RuntimeMessageModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    RuntimeMessageModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var commandModel = new RuntimeMessageModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            commandModel.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            commandModel.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var runtimeIdNode = root.get("runtime_id");
        if (runtimeIdNode != null) {
            commandModel.setRuntimeId(Long.valueOf(runtimeIdNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            commandModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            commandModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = MessageQualifierEnum.valueOf(qualifierNode.asText());
            commandModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                commandModel.setBody(body);
            }
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            commandModel.setDeleted(Boolean.valueOf(deletedNode.asText()));
        }

        return commandModel;
    }
}
