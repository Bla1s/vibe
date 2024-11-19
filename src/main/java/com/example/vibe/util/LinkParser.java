package com.example.vibe.util;

import com.example.vibe.model.Track;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;


public class LinkParser {

    public static Track parseYouTubeLink(String link) {
        try {
            YoutubeDownloader downloader = new YoutubeDownloader();
            String videoId = extractVideoId(link);

            RequestVideoInfo request = new RequestVideoInfo(videoId);
            Response<VideoInfo> response = downloader.getVideoInfo(request);
            VideoInfo video = response.data();

            VideoDetails details = video.details();

            Track track = new Track();
            track.setTitle(details.title());
            track.setArtist(details.author());
            track.setDuration(details.lengthSeconds());
            track.setUrl(link);
            track.setSource("youtube");

            return track;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse YouTube link", e);
        }
    }

    private static String extractVideoId(String link) {
        if (link.contains("youtu.be/")) {
            return link.substring(link.lastIndexOf("/") + 1);
        }

        String[] params = link.split("[&?]");
        for (String param : params) {
            if (param.startsWith("v=")) {
                return param.substring(2);
            }
        }

        throw new IllegalArgumentException("Invalid YouTube URL");
    }

// TODO
//    private static Track parseSoundCloudLink(String link) throws IOException {
//    }
}