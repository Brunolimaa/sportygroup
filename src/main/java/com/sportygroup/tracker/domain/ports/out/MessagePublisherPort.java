package com.sportygroup.tracker.domain.ports.out;

import com.sportygroup.tracker.domain.model.LiveSports;

/**
 * Port for interacting with an external API client.
 * This interface defines methods to fetch event data from an external API.
 */
public interface MessagePublisherPort {
    void publish(LiveSports liveSports);
}
