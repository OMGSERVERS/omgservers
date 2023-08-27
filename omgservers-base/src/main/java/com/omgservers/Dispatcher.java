package com.omgservers;

import com.omgservers.operation.getConfig.GetConfigOperation;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

@Slf4j
@Startup
@ApplicationScoped
public class Dispatcher {

    final Map<Long, Deque<Long>> eventsByGroupId;
    final TreeSet<EventGroup> sortedGroups;
    final int postponeInterval;

    Dispatcher(final GetConfigOperation getConfigOperation) {
        postponeInterval = getConfigOperation.getConfig().postponeInterval();
        sortedGroups = new TreeSet<>(Comparator.comparing(EventGroup::getAvailableAt));
        eventsByGroupId = new HashMap<>();
    }

    public synchronized void addEvent(final Long eventId, final Long groupId) {
        if (eventId == null) {
            throw new IllegalArgumentException("eventId is null");
        }
        if (groupId == null) {
            throw new IllegalArgumentException("groupId is null");
        }

        if (eventsByGroupId.containsKey(groupId)) {
            eventsByGroupId.get(groupId).add(eventId);
        } else {
            final var events = new LinkedList<Long>();
            events.add(eventId);
            eventsByGroupId.put(groupId, events);
            sortedGroups.add(new EventGroup(groupId));
        }
    }

    public synchronized EventGroup pollGroup() {
        final var group = sortedGroups.pollFirst();
        if (group != null) {
            if (group.isAvailableNow()) {
                final var events = eventsByGroupId.get(group.getGroupId());
                group.eventId = events.poll();
                return group;
            } else {
                // return group back
                sortedGroups.add(group);
                return null;
            }
        } else {
            return null;
        }
    }

    public synchronized void returnGroup(final EventGroup group) {
        if (group == null) {
            throw new IllegalArgumentException("group is null");
        }

        final var groupId = group.getGroupId();
        final var events = eventsByGroupId.get(groupId);
        if (events.isEmpty()) {
            eventsByGroupId.remove(groupId);
        } else {
            group.availableAt = Instant.now();
            sortedGroups.add(group);
        }
    }

    public synchronized void postponeGroup(final EventGroup group) {
        if (group == null) {
            throw new IllegalArgumentException("group is null");
        }

        group.postpone(postponeInterval);
        sortedGroups.add(group);
        if (group.eventId != null) {
            final var events = eventsByGroupId.get(group.getGroupId());
            events.addFirst(group.eventId);
            group.eventId = null;
        }
    }

    public static class EventGroup {
        final Long groupId;
        Instant availableAt;
        Long eventId;

        public EventGroup(final Long groupId) {
            if (groupId == null) {
                throw new IllegalArgumentException("groupId is null");
            }

            this.groupId = groupId;
            availableAt = Instant.now();
        }

        public Long getGroupId() {
            return groupId;
        }

        public Long getEventId() {
            return eventId;
        }

        boolean isAvailableNow() {
            return Instant.now().isAfter(availableAt);
        }

        Instant getAvailableAt() {
            return availableAt;
        }

        void postpone(long interval) {
            availableAt = Instant.now().plusSeconds(interval);
        }
    }
}
