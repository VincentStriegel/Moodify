package com.moodify.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.PlaylistTO;
import com.moodify.backend.services.music.deezer.DeezerApiRequester;
import com.moodify.backend.services.music.util.TransferObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.api.util.Source;
import com.moodify.backend.services.music.deezer.DeezerApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains unit tests for the DeezerApi class in the Moodify application.
 * It uses the JUnit 5 framework for structuring and running the tests.
 * The class tests various scenarios including getting a track by its ID, getting an artist by their ID,
 * getting a playlist by its ID, and getting an album by its ID.
 * Each test method in this class represents a test case for a specific scenario.
 * The class uses assertions to verify the expected results.
 * The class is annotated with @Component and @Transactional to indicate that it is a Spring Bean and that
 * its methods should be executed within a transaction context.
 */
@Component
@Transactional
public class DeezerApiTests {

    private static final DeezerApi DEEZER_API_TESTS = new DeezerApi(new DeezerApiRequester("${deezerApiKey}"));

    private static final DeezerApiRequester DEEZER_API_REQUESTER = new DeezerApiRequester("${deezerApiKey}");

    @Test
    void testGetTrackById() {

        int expectedId = 3135556;
        String expectedTitle = "Harder, Better, Faster, Stronger";
        String expectedReleaseDate = "2005-01-24";
        int expectedDuration = 224;
        String expectedPreview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-10.mp3";
        Source expectedSource = Source.DEEZER;
        int expectedArtistId = 27;
        String expectedArtistName = "Daft Punk";
        String expectedArtistPictureSmall = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/56x56-000000-80-0-0.jpg";
        String expectedArtistPictureBig = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/500x500-000000-80-0-0.jpg";


        TrackTO actualTrackTO = DEEZER_API_TESTS.getTrack(expectedId);

        assertEquals(expectedId, actualTrackTO.getId());
        assertEquals(expectedTitle, actualTrackTO.getTitle());
        assertEquals(expectedReleaseDate, actualTrackTO.getRelease_date());
        assertEquals(expectedDuration, actualTrackTO.getDuration());
        assertEquals(expectedPreview, actualTrackTO.getPreview());
        assertEquals(expectedSource, actualTrackTO.getSource());
        assertEquals(expectedArtistId, actualTrackTO.getArtist().getId());
        assertEquals(expectedArtistName, actualTrackTO.getArtist().getName());
        assertEquals(expectedArtistPictureSmall, actualTrackTO.getArtist().getPicture_small());
        assertEquals(expectedArtistPictureBig, actualTrackTO.getArtist().getPicture_big());
    }

    @Test
    void testGetArtistById() {
        int expectedId = 27;
        String expectedName = "Daft Punk";
        String expectedPictureSmall = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/56x56-000000-80-0-0.jpg";
        String expectedPictureBig = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/500x500-000000-80-0-0.jpg";
        Source expectedSource = Source.DEEZER;

        ArtistTO actualArtistTO = DEEZER_API_TESTS.getArtist(expectedId);

        assertEquals(expectedId, actualArtistTO.getId());
        assertEquals(expectedName, actualArtistTO.getName());
        assertEquals(expectedPictureSmall, actualArtistTO.getPicture_small());
        assertEquals(expectedPictureBig, actualArtistTO.getPicture_big());
        assertEquals(expectedSource, actualArtistTO.getSource());
    }

    @Test
    void getPlaylistById() {
        int expectedId = 908622995;
        String expectedTitle = "En mode 60";
        String expectedPictureSmall = "https://e-cdns-images.dzcdn.net/images/playlist/4fd375a41c7779ed7deab64fb1194099/56x56-000000-80-0-0.jpg";
        String expectedPictureMedium = "https://e-cdns-images.dzcdn.net/images/playlist/4fd375a41c7779ed7deab64fb1194099/250x250-000000-80-0-0.jpg";
        String expectedPictureBig = "https://e-cdns-images.dzcdn.net/images/playlist/4fd375a41c7779ed7deab64fb1194099/500x500-000000-80-0-0.jpg";
        Source expectedSource = Source.DEEZER;


        PlaylistTO actualPlaylistTO = DEEZER_API_TESTS.getPlaylist(expectedId);

        assertEquals(expectedId, actualPlaylistTO.getId());
        assertEquals(expectedTitle, actualPlaylistTO.getTitle());
        assertEquals(expectedPictureSmall, actualPlaylistTO.getPicture_small());
        assertEquals(expectedPictureBig, actualPlaylistTO.getPicture_big());
        assertEquals(expectedPictureMedium, actualPlaylistTO.getPicture_medium());
        assertEquals(expectedSource, actualPlaylistTO.getSource());
    }

    @Test
    void getAlbumById() {
        int expectedId = 302127;
        String expectedTitle = "Discovery";
        String expectedPictureSmall = "https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/56x56-000000-80-0-0.jpg";
        String expectedPictureBig = "https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/500x500-000000-80-0-0.jpg";
        String expectedReleaseDate = "2001-03-07";
        int expectedNumberOfSongs = 14;
        Source expectedSource = Source.DEEZER;

        AlbumTO actualAlbumTO = DEEZER_API_TESTS.getAlbum(expectedId);

        assertEquals(expectedId, actualAlbumTO.getId());
        assertEquals(expectedTitle, actualAlbumTO.getTitle());
        assertEquals(expectedPictureSmall, actualAlbumTO.getCover_small());
        assertEquals(expectedPictureBig, actualAlbumTO.getCover_big());
        assertEquals(expectedReleaseDate, actualAlbumTO.getRelease_date());
        assertEquals(expectedNumberOfSongs, actualAlbumTO.getNumber_of_songs());
        assertEquals(expectedSource, actualAlbumTO.getSource());
    }

}
