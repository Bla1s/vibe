package com.example.vibe.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class LinkValidator {

    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(
            "^(https?://)?(www\\.)?(youtube\\.com|youtu\\.?be)/.+$");
    private static final Pattern SOUNDCLOUD_PATTERN = Pattern.compile(
            "^(https?://)?(www\\.)?(soundcloud\\.com)/.+$");

    public static boolean isValidLink(String link) {
        return YOUTUBE_PATTERN.matcher(link).matches() || SOUNDCLOUD_PATTERN.matcher(link).matches();
    }

    public static boolean containsValidContent(String link) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }
}