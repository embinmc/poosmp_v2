package embinmc.mod.poosmp.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.OptionInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooSMPClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("poosmp/client");
    public static final OptionInstance<Boolean> EAT_PACKET_ERRORS = createBoolOption("option.poosmp.eat_packet_errors", true);

    @Override
    public void onInitializeClient() {
        PooClientConfig.read();
    }

    private static OptionInstance<Boolean> createBoolOption(String id, boolean defaultValue) {
        return OptionInstance.createBoolean("option.poosmp." + id, defaultValue, PooClientConfig::save);
    }

    public static boolean eatPacketErrors() {
        return EAT_PACKET_ERRORS.get();
    }
}
