package com.decentraldbcluster.dbclient.odm.database;

public abstract class Database {
    public Database() {
        DatabaseInitializer.initialize(this);
    }
}
