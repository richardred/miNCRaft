package com.hackgsu.mincraft;

import com.google.gson.Gson;
import com.hackgsu.mincraft.payload.MincraftAction;
import com.hackgsu.mincraft.payload.TextAction;
import net.minecraft.advancements.Advancement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackendPoster
{
    private static final Logger LOGGER = LogManager.getLogger();

    private final ExecutorService executorService;
    private final Gson gson;
    private final URL postEndpoint;

    public BackendPoster() throws MalformedURLException
    {
        executorService = Executors.newFixedThreadPool(5);
        gson = new Gson();
        postEndpoint = new URL("https://us-central1-hackgsu2020.cloudfunctions.net/test1");
    }

    public void postAdvancement(Advancement adv, String username)
    {
        MincraftAction payload = new MincraftAction();
        payload.setAction("advancement");
        payload.setData(adv.getId().toString());
        payload.setUsername(username);

        createAndPostTo(postEndpoint, gson.toJson(payload));
    }

    public void postBlockBreak(String blockName, String username)
    {
        MincraftAction payload = new MincraftAction();
        payload.setAction("blockBreak");
        payload.setData(blockName);
        payload.setUsername(username);

        createAndPostTo(postEndpoint, gson.toJson(payload));
    }

    public void postClickSign(String number, String message)
    {
        TextAction payload = new TextAction();
        payload.setNumber(number);
        payload.setMessage(message);

        LOGGER.info("Texting \"" + message + "\' to " + number);

        createAndPostTo(postEndpoint, gson.toJson(payload));
    }

    private void createAndPostTo(URL url, String payload)
    {
        executorService.submit(() ->
        {
            try
            {
                LOGGER.info("Sending payload " + payload + " to endpoint");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = payload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                conn.connect();
                LOGGER.info("Received response " + conn.getResponseCode());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
