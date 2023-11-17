package com.moodify.backend.api.transferobjects;

public class UserTO implements Cloneable {
    private String email;
    private String username;

    private PersonalLibraryTO personalLibraryTO;
    public UserTO() {
        this.personalLibraryTO = new PersonalLibraryTO();
    }

    public String getEmail() {
        return email;
    }

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
            for (TrackTO trackTO: this.personalLibraryTO.likedTracks) {
                clone.personalLibraryTO.likedTracks.add(trackTO.clone());
            }
            for (ArtistTO artistTO: this.personalLibraryTO.likedArtists) {
                clone.personalLibraryTO.likedArtists.add(artistTO.clone());
            }
            for (PlaylistTO playlistTO: this.personalLibraryTO.likedPlaylists) {
                clone.personalLibraryTO.likedPlaylists.add(playlistTO.clone());
            }
            for (AlbumTO albumTO: this.personalLibraryTO.likedAlbums) {
                clone.personalLibraryTO.likedAlbums.add(albumTO.clone());
            }
            for (PlaylistTO playlistTO: this.personalLibraryTO.customPlaylists) {
                clone.personalLibraryTO.customPlaylists.add(playlistTO.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
