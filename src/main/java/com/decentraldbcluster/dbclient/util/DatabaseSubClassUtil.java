package com.decentraldbcluster.dbclient.util;

import com.decentraldbcluster.dbclient.odm.database.Database;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class DatabaseSubClassUtil {

    private static Class<? extends Database> subclass = null;

    public static Class<? extends Database> getDatabaseSubClass() {

        if (subclass != null) return subclass;

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .addScanners(new SubTypesScanner()));

        Set<Class<? extends Database>> subclasses = reflections.getSubTypesOf(Database.class);

        if (subclasses == null) throw new RuntimeException("Unexpected to work without subclasses!!.");

        return subclass = subclasses.iterator().next();
    }
}
