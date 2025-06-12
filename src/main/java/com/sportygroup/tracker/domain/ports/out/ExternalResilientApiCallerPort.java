package com.sportygroup.tracker.domain.ports.out;

import com.sportygroup.tracker.domain.model.LiveSports;

/**
 * Port for resiliently calling an external API to fetch live sports data.
 */
public interface ExternalResilientApiCallerPort {

    LiveSports fetch(String eventId);

}
