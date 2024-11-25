package com.example.vibe.util;

import java.util.regex.Pattern;

public class LinkValidator {

    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(
            "^(https?://)?(www\\.)?(youtube\\.com|youtu\\.?be)/.+$");
    private static final Pattern SOUNDCLOUD_PATTERN = Pattern.compile(
            "^(https?://)?(www\\.)?(soundcloud\\.com)/.+$");

    public static boolean isValidLink(String link) {
        return YOUTUBE_PATTERN.matcher(link).matches() || SOUNDCLOUD_PATTERN.matcher(link).matches();
    }

}