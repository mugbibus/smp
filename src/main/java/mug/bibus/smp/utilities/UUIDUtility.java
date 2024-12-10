package mug.bibus.smp.utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UUIDUtility {
    public static CompletableFuture<UUID> getFromMojang(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final URL url = URI.create("https://api.mojang.com/users/profiles/minecraft/" + name).toURL();
                final InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
                final JsonObject object = JsonParser.parseReader(inputStreamReader).getAsJsonObject();

                final String id = object.get("id").getAsString();
                final String uuid = id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32);

                return UUID.fromString(uuid);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }
}
