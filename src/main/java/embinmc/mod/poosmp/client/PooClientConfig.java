package embinmc.mod.poosmp.client;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public record PooClientConfig(boolean eatPacketErrors, boolean showBalanceAboveZero) {
    public static final String PATH = "config/poosmp_client.json";
    public static final Codec<PooClientConfig> CODEC = RecordCodecBuilder.create(pcc -> pcc.group(
            Codec.BOOL.fieldOf("eat_packet_errors").forGetter(PooClientConfig::eatPacketErrors),
            Codec.BOOL.fieldOf("show_balance_above_zero").forGetter(PooClientConfig::showBalanceAboveZero)
    ).apply(pcc, PooClientConfig::new));

    public static void save(Boolean ignored) {
        try {
            createAndWriteFile(PATH, saveToString());
        } catch (Exception e) {
            PooSMPClient.LOGGER.error("Error occurred when trying to save config", e);
        }
    }

    public static void read() {
        try {
            File configFile = new File(PATH);
            try {
                InputStream inputStream = configFile.toURL().openStream();
                JsonElement json = fromInputStream(inputStream);
                inputStream.close();
                DataResult<PooClientConfig> result = CODEC.parse(JsonOps.INSTANCE, json);
                if (result.error().isPresent()) {
                    PooSMPClient.LOGGER.error(String.valueOf(result.error().get()));
                    return;
                }
                PooClientConfig config = result.getOrThrow();
                PooSMPClient.EAT_PACKET_ERRORS.set(config.eatPacketErrors());
                PooSMPClient.SHOW_BALANCE_ABOVE_ZERO.set(config.showBalanceAboveZero());
            } catch (Exception e) {
                if (!(e instanceof FileNotFoundException))
                    PooSMPClient.LOGGER.error("Failed to get PooSMP client config!", e);
                save(false);
            }
        } catch (Exception e) {
            PooSMPClient.LOGGER.error("Exception occurred trying to read config file!", e);
        }
    }

    private static void createAndWriteFile(String path, String content) {
        try {
            Files.createDirectories(Paths.get("config"));
        } catch (IOException e) {
            PooSMPClient.LOGGER.error("Couldn't create config directory!", e);
            return;
        }
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(content);
        } catch (IOException e) {
            PooSMPClient.LOGGER.error("Failed to create config file!", e);
            return;
        }
        PooSMPClient.LOGGER.info("Wrote to file \"{}\"", path);
    }

    private static String toFancyJsonString(JsonElement json) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setFormattingStyle(FormattingStyle.PRETTY);
        jsonWriter.setStrictness(Strictness.LENIENT);
        try {
            Streams.write(json, jsonWriter);
        } catch (IOException e) {
            PooSMPClient.LOGGER.error("Failed to format config json!", e);
            return json.toString();
        }
        return stringWriter.toString();
    }

    private static String saveToString() {
        PooClientConfig config = new PooClientConfig(
                PooSMPClient.eatPacketErrors(),
                PooSMPClient.showBalanceAboveZero()
        );
        JsonElement json = CODEC.encodeStart(JsonOps.INSTANCE, config).getOrThrow();
        return toFancyJsonString(json);
    }

    private static JsonElement fromInputStream(InputStream inputStream) {
        return new GsonBuilder().create().fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonElement.class);
    }
}
