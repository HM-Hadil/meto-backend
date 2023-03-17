package com.innovup.meto.users.core.data;

import org.springframework.data.domain.Persistable;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

public abstract class EntityWithSelfAssignedId<ID> implements Persistable<ID> {

    @Transient
    private boolean persisted;

    @Override
    public boolean isNew() { return !persisted; }

    @PostPersist
    @PostLoad
    private void updatePersisted() {
        persisted = true;
    }
}
