//package com.example.vibe.util;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LinkParserTest {
//
//    @Test
//    void testParseYouTubeLink() throws IOException {
//        String youtubeLink = "https://www.youtube.com/watch?v=pvVhnZe4DdI";
//        Track track = LinkParser.parseYouTubeLink(youtubeLink);
//
//        assertNotNull(track);
//        assertEquals("FINALLY GONE", track.getTitle());
//        assertEquals("EXODIA", track.getArtist());
//        assertEquals("https://www.youtube.com/watch?v=pvVhnZe4DdI", track.getUrl());
//        assertEquals("youtube", track.getSource());
//        assertEquals(187, track.getDuration());
//    }
//// TODO
////    @Test
////    void testParseSoundCloudLink() throws IOException {
////        String soundCloudLink = "https://soundcloud.com/midnightcoupes/midnightfinally-gone";
////
////    }
//
////    @Test
////    void testUnsupportedLink() {
////        String unsupportedLink = "https://example.com";
////        assertThrows(IllegalArgumentException.class, () -> LinkParser.parseDataFromLink(unsupportedLink));
////    }
//}