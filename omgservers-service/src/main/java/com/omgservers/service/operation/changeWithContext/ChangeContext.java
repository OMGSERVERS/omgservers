package com.omgservers.service.operation.changeWithContext;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Context;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
public class ChangeContext<T> {

    final Context context;
    final List<EventModel> changeEvents;
    final List<LogModel> changeLogs;

    T result;

    public ChangeContext(Context context) {
        this.context = context;
        changeEvents = new ArrayList<>();
        changeLogs = new ArrayList<>();

        context.put("events", changeEvents);
        context.put("logs", changeLogs);
    }

    public List<EventModel> getChangeEvents() {
        return changeEvents;
    }

    public List<LogModel> getChangeLogs() {
        return changeLogs;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void add(EventModel changeEvent) {
        changeEvents.add(changeEvent);
    }

    public void add(LogModel changeLog) {
        changeLogs.add(changeLog);
    }

    public boolean contains(EventQualifierEnum qualifier) {
        return changeEvents.stream()
                .map(EventModel::getQualifier)
                .anyMatch(qualifier::equals);
    }
}
