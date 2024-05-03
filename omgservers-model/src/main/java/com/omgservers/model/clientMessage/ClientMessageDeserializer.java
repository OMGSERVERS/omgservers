package com.omgservers.model.clientMessage;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.omgservers.model.message.MessageQualifierEnum;

import java.io.IOException;
import java.time.Instant;

public class ClientMessageDeserializer extends StdDeserializer<ClientMessageModel> {

    public ClientMessageDeserializer() {
        this(null);
    }

    public ClientMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClientMessageModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    ClientMessageModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var clientMessage = new ClientMessageModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            clientMessage.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            clientMessage.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var clientIdNode = root.get("client_id");
        if (clientIdNode != null) {
            clientMessage.setClientId(Long.valueOf(clientIdNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            clientMessage.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            clientMessage.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = MessageQualifierEnum.valueOf(qualifierNode.asText());
            clientMessage.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                clientMessage.setBody(body);
            }
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            clientMessage.setDeleted(Boolean.valueOf(deletedNode.asText()));
        }

        return clientMessage;
    }
}
