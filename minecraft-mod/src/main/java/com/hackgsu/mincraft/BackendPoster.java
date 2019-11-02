package com.hackgsu.mincraft;

import com.google.gson.Gson;
import net.minecraft.advancements.Advancement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BackendPoster
{
    private static final Logger LOGGER = LogManager.getLogger();

    private final Gson gson;
    private final URL postEndpoint;

    public BackendPoster() throws MalformedURLException
    {
        gson = new Gson();
        postEndpoint = new URL("https://us-central1-hackgsu2020.cloudfunctions.net/test1");
    }


    public void postAdvancement(Advancement adv, String username)
    {
        MincraftAction payload = new MincraftAction();
        payload.setAction("advancement");
        payload.setData(adv.getId().toString());
        payload.setUsername(username);

        try
        {
            String jsonPayload = gson.toJson(payload);

            LOGGER.info("Sending payload " + jsonPayload + " to endpoint");

            createAndPostTo(postEndpoint, jsonPayload);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void createAndPostTo(URL url, String payload) throws IOException
    {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = payload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }
}
