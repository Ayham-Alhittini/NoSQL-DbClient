package com.decentraldbcluster.dbclient.odm.database;

import com.decentraldbcluster.dbclient.odm.statetracker.DatabaseStateTracker;

public abstract class Database {
    private static volatile boolean isTracked = false;

    public Database() {
        DatabaseInitializer.initialize(this);
        trackDatabaseState();
    }

    private synchronized void trackDatabaseState() {
        if (!isTracked) {
            DatabaseStateTracker.track(this.getClass());
            isTracked = true;
        }
    }
}
