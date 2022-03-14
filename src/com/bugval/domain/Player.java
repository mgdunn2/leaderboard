package com.bugval.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Domain object that represents a single player.
 */
@Value.Immutable
@Gson.TypeAdapters
public interface Player {
    String getName();
    String getId();
    float getAge();
}
