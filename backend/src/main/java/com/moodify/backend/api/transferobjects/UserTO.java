package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTO {

    private long id;
    private String email;
    private String username;
    private PersonalLibraryTO personalLibrary;

    public UserTO() {
        this.personalLibrary = new PersonalLibraryTO();
    }


}
