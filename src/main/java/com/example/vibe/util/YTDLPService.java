package com.example.vibe.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class YTDLPService {

    public Map<String, String> fetchVideoMetadata(String videoUrl) {
        Map<String, String> metadata = new HashMap<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("yt-dlp", "--dump-json", videoUrl);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String json = reader.readLine();
            process.waitFor();

            // Parse the JSON response to extract metadata
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            metadata.put("title", (String) map.get("title"));
            metadata.put("url", (String) map.get("webpage_url"));
            metadata.put("duration", String.valueOf(map.get("duration")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metadata;
    }

    public String fetchAudioStreamUrl(String videoUrl) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("yt-dlp", "-f", "bestaudio", "--get-url", videoUrl);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String audioUrl = reader.readLine();
            process.waitFor();
            return audioUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}