package com.bugval.services;

import com.bugval.domain.Player;

/**
 * Service responsible for fetching a {@link Player} given the user's ID
 */
public interface PlayerFetcher {
    Player fetchPlayer(String id);
}
