package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a user in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the personalLibrary property with a new instance of PersonalLibraryTO.
 * The properties of the class represent the data that is transferred.
 * The id property represents the unique identifier of the user.
 * The email property represents the email of the user.
 * The username property represents the username of the user.
 * The personalLibrary property represents the personal library of the user.
 * The discography property represents the discography of the user.
 */
@Getter
@Setter
public class UserTO {

    private long id;
    private String email;
    private String username;
    private PersonalLibraryTO personalLibrary;
    private DiscographyTO discography;

    public UserTO() {
        this.personalLibrary = new PersonalLibraryTO();
    }


}
