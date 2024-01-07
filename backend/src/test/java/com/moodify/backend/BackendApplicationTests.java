package com.moodify.backend;

import com.moodify.backend.api.controllers.MusicController;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.api.util.Source;
import com.moodify.backend.services.music.deezer.DeezerApi;
import com.moodify.backend.services.music.deezer.DeezerApiRequester;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan(basePackages = "com.moodify.backend")
class BackendApplicationTests {



}
