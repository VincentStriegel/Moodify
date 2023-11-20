package com.moodify.backend.api.transferobjects;

public class UserTO implements Cloneable {

    private long id;
    private String email;
    private String username;
    private PersonalLibraryTO personalLibraryTO;

    public UserTO() {
        this.personalLibraryTO = new PersonalLibraryTO();
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PersonalLibraryTO getPersonalLibrary() {
        return personalLibraryTO;
    }

    public void setPersonalLibrary(PersonalLibraryTO personalLibraryTO) {
        this.personalLibraryTO = personalLibraryTO;
    }

    @Override
    public UserTO clone() {
        try {
            UserTO clone = (UserTO) super.clone();
            clone.email = this.email;
            clone.username = this.username;
            clone.personalLibraryTO = this.personalLibraryTO.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
