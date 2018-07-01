package com.otus.homework9.config;

/**
 * @author Evgeny Borisov
 */
public interface ObjectConfigurator {
    <T> void configure(T t);
}
