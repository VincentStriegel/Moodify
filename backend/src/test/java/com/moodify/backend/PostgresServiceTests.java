package com.moodify.backend;

import com.moodify.backend.services.database.exceptions.login.UserCredentialsException;
import com.moodify.backend.services.database.exceptions.login.WrongPasswordException;
import com.moodify.backend.services.database.exceptions.profiles.*;
import com.moodify.backend.services.database.exceptions.registration.*;
import com.moodify.backend.services.database.objects.PersonalLibraryDO;
import com.moodify.backend.services.database.objects.PlaylistDO;
import com.moodify.backend.services.database.objects.UserDO;
import com.moodify.backend.services.database.postgres.PlaylistRepository;
import com.moodify.backend.services.database.postgres.PostgresService;
import com.moodify.backend.services.database.postgres.UserRepository;
import com.moodify.backend.services.database.security.PasswordEncoder;
import com.moodify.backend.services.database.util.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the PostgresService class in the Moodify application.
 * It uses the Mockito framework to mock dependencies and isolate the class under test.
 * The class tests various scenarios including successful user creation, user creation with existing email or username,
 * user creation with invalid email, username or password, successful user login, user login with wrong password or non-existing email or username,
 * finding a user by id, searching for artists, getting an artist, searching for playlists, creating a custom playlist, and deleting a custom playlist.
 * Each test method in this class represents a test case for a specific scenario.
 * The class uses the JUnit 5 framework for structuring and running the tests.
 * The class uses assertions to verify the expected results.
 */
public class PostgresServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PostgresService postgresService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserSuccessfully() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsUserByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");

        UserDO createdUser = null;
        try {
             createdUser = postgresService.createUser(user);
        } catch (Exception e) {
            fail("Exception thrown");
        }


        assertEquals("hashedPassword", createdUser.getPassword());
    }

    @Test
    public void createUserWithExistingEmailThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(true);

        assertThrows(RegisteredEmailException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithExistingUsernameThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsUserByUsername(user.getUsername())).thenReturn(true);

        assertThrows(RegisteredUsernameException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidEmailNoPrecedingSymbolsBeforeAtSignThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("@Email");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        assertThrows(InvalidEmailException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidEmailNoFollowingSymbolsBeforeAtSignThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("email@");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        assertThrows(InvalidEmailException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidUsernameStartThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("_invalidStart");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidUsernameEndThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("invalidStart_");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidUsernameTooLongThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("toolongstring123456789012345toolongstring123456789012345");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }


    @Test
    public void createUserWithInvalidUsernameIncludesAsteriskThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("a.b-c_d*e");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidUsernameContainsExclamationMarkThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("abc!def_123");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidUsernameConsecutiveDotsThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("example..string");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidUsernameMultipleUnderscoresThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("abc.def__123_456_789");
        user.setPassword("testPassword");

        assertThrows(InvalidUsernameException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void createUserWithInvalidPasswordNoUpperCaseThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("nouppercase");

        assertThrows(InvalidPasswordException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidPasswordNoLowerCaseThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("NOLOWERCASE!");

        assertThrows(InvalidPasswordException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidPasswordTooShortThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("Short!");

        assertThrows(InvalidPasswordException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidPasswordNoSpecialCharacterThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("alllowercasealluppercase");

        assertThrows(InvalidPasswordException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidPasswordNoUpperAndLowerCaseThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("12345678");

        assertThrows(InvalidPasswordException.class, () -> postgresService.createUser(user));
    }
    @Test
    public void createUserWithInvalidPasswordNoUppperCaseThrowsException() {
        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setPassword("nonpecialnharacter");

        assertThrows(InvalidPasswordException.class, () -> postgresService.createUser(user));
    }

    @Test
    public void loginUserSuccessfullyWithEmail() {
        LoginUser loginUser = new LoginUser();
        loginUser.setCredential("test@test.com");
        loginUser.setPassword("testPassword");

        UserDO user = new UserDO();

        user.setEmail("test@test.com");
        user.setPassword("hashedPassword");

        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.compare(loginUser.getPassword(), user.getPassword())).thenReturn(true);

        try {
            long userId = postgresService.loginUser(loginUser);
            assertEquals(0L, userId);
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void loginUserSuccessfullyWithUsername() {
        LoginUser loginUser = new LoginUser();
        loginUser.setCredential("testUser");
        loginUser.setPassword("testPassword");

        UserDO user = new UserDO();

        user.setUsername("testUser");
        user.setPassword("hashedPassword");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.compare(loginUser.getPassword(), user.getPassword())).thenReturn(true);

        try {
            long userId = postgresService.loginUser(loginUser);
            assertEquals(0L, userId);
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void loginUserWithWrongPasswordThrowsException() {
        LoginUser loginUser = new LoginUser();
        loginUser.setCredential("test@test.com");
        loginUser.setPassword("wrongPassword");

        UserDO user = new UserDO();
        user.setEmail("test@test.com");
        user.setPassword("hashedPassword");

        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.compare(loginUser.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(WrongPasswordException.class, () -> postgresService.loginUser(loginUser));
    }

    @Test
    public void loginUserWithNonExistingEmailThrowsException() {
        LoginUser loginUser = new LoginUser();
        loginUser.setCredential("nonExisting@test.com");
        loginUser.setPassword("testPassword");

        when(userRepository.getUserByEmail(loginUser.getCredential())).thenReturn(null);

        assertThrows(UserCredentialsException.class, () -> postgresService.loginUser(loginUser));
    }

    @Test
    public void loginUserWithNonExistingUsernameThrowsException() {
        LoginUser loginUser = new LoginUser();
        loginUser.setCredential("nonExistingUser");
        loginUser.setPassword("testPassword");

        when(userRepository.getUserByUsername(loginUser.getCredential())).thenReturn(null);

        assertThrows(UserCredentialsException.class, () -> postgresService.loginUser(loginUser));
    }


    @Test
    public void findUserByIdReturnsUserWhenUserExists() {
        long userId = 0L;
        UserDO user = new UserDO();


        when(userRepository.findById(userId)).thenReturn(user);

        try {
            UserDO returnedUser = postgresService.findUserById(userId);
            assertEquals(user, returnedUser);
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void findUserByIdThrowsUserNotFoundExceptionWhenUserDoesNotExist() {
        long userId = 0L;

        when(userRepository.findById(userId)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> postgresService.findUserById(userId));
    }

    @Test
    public void searchArtistsReturnsMatchingArtists() {
        String query = "testArtist";
        String wildcard = "%" + query + "%";
        List<UserDO> expectedArtists = Arrays.asList(new UserDO(), new UserDO());

        when(userRepository.findAllByUsernameLikeAndDiscographyIsNotNull(wildcard)).thenReturn(expectedArtists);

        List<UserDO> returnedArtists = postgresService.searchArtists(query);

        assertEquals(expectedArtists, returnedArtists);
    }

    @Test
    public void getArtistReturnsArtistWhenExists() {
        long artistId = 0L;
        UserDO expectedArtist = new UserDO();

        when(userRepository.findByIdAndDiscographyIsNotNull(artistId)).thenReturn(expectedArtist);

        UserDO returnedArtist = null;
        try {
            returnedArtist = postgresService.getArtist(artistId);
            assertEquals(expectedArtist, returnedArtist);
        } catch (Exception e) {
            fail("Exception thrown");
        }


        assertEquals(expectedArtist, returnedArtist);
    }

    @Test
    public void getArtistThrowsExceptionWhenDoesNotExist() {
        long artistId = 0L;

        when(userRepository.findByIdAndDiscographyIsNotNull(artistId)).thenReturn(null);

        assertThrows(ArtistNotFoundException.class, () -> postgresService.getArtist(artistId));
    }

    @Test
    public void searchPlaylistsReturnsMatchingPlaylists() {
        String query = "testPlaylist";
        String wildcard = "%" + query + "%";
        List<PlaylistDO> expectedPlaylists = Arrays.asList(new PlaylistDO(), new PlaylistDO());

        when(playlistRepository.findAllByTitleLike(wildcard)).thenReturn(expectedPlaylists);

        List<PlaylistDO> returnedPlaylists = postgresService.searchPlaylists(query);

        assertEquals(expectedPlaylists, returnedPlaylists);
    }

    @Test
    public void createCustomPlaylistReturnsPlaylistIdWhenNotExists() {
        long userId = 0L;
        String playlistTitle = "testPlaylist";
        UserDO user = new UserDO();
        user.setPersonalLibrary(new PersonalLibraryDO());

        when(userRepository.findById(userId)).thenReturn(user);

        Long returnedPlaylistId = null;
        try {
            returnedPlaylistId = postgresService.createCustomPlaylist(userId, playlistTitle);
            assertEquals(0L, returnedPlaylistId);
        } catch (Exception e) {
            fail("Exception thrown");
        }

        assertEquals(0L, returnedPlaylistId);
    }

    @Test
    public void deleteCustomPlaylistThrowsExceptionWhenDefaultPlaylist() {
        long userId = 0L;
        long playlistId = 0L;
        UserDO user = new UserDO();
        user.setPersonalLibrary(new PersonalLibraryDO());
        PlaylistDO playlist = new PlaylistDO();

        playlist.setLikedTrackPlaylist(true);
        user.getPersonalLibrary().getPlaylists().add(playlist);

        when(userRepository.findById(userId)).thenReturn(user);

        assertThrows(DefaultPlaylistException.class, () -> postgresService.deleteCustomPlaylist(userId, playlistId));
    }


}
