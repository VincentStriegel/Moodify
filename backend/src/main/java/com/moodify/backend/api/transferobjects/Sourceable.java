package com.moodify.backend.api.transferobjects;

import com.moodify.backend.api.util.Source;

/**
 * This interface represents a sourceable entity in the Moodify application.
 * It is used to ensure that all transfer objects (TOs) that implement this interface have a getSource method.
 * The getSource method is used to get the source of the data for the TO.
 * The Source returned by the getSource method is an enumeration that represents the source of the data.
 */
public interface Sourceable {
     Source getSource();
}
