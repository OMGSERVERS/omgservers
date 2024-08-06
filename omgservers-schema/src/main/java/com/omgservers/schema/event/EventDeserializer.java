package com.omgservers.schema.event;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class EventDeserializer extends StdDeserializer<EventModel> {

    public EventDeserializer() {
        this(null);
    }

    public EventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EventModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    EventModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var eventModel = new EventModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            eventModel.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            eventModel.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            eventModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            eventModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var delayedNode = root.get("delayed");
        if (delayedNode != null) {
            eventModel.setDelayed(Instant.parse(delayedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = EventQualifierEnum.valueOf(qualifierNode.asText());
            eventModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                eventModel.setBody(body);
            }
        }

        final var availableNode = root.get("available");
        if (availableNode != null) {
            eventModel.setDelayed(Instant.parse(availableNode.asText()));
        }

        final var statusNode = root.get("status");
        if (statusNode != null) {
            eventModel.setStatus(EventStatusEnum.valueOf(statusNode.asText()));
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            final var deleted = Boolean.parseBoolean(deletedNode.asText());
            eventModel.setDeleted(deleted);
        }

        return eventModel;
    }
}
