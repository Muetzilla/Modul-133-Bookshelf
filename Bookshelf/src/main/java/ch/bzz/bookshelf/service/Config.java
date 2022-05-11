package ch.bzz.bookshelf.service;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
* configure the web services and properties
*/

@ApplicationPath("/resource")

public class Config extends Application {

    /**
     * define all provider classes
     *
     * @return set of classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> providers = new HashSet<>();
        providers.add(TestService.class);
        return providers;
    }

}
