package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public interface PooSoundEvents {
    Holder<SoundEvent> MUSIC_DISC_TRIFECTA_CAP = registerHolder("music_disc.trifecta_cap");
    Holder<SoundEvent> MUSIC_DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = registerHolder("music_disc.butterflies_and_hurricanes_instrumental");
    Holder<SoundEvent> MUSIC_DISC_BUDDY_HOLLY = registerHolder("music_disc.buddy_holly");
    Holder<SoundEvent> MUSIC_DISC_STEREO_MADNESS = registerHolder("music_disc.stereo_madness");
    Holder<SoundEvent> MUSIC_DISC_NOT_LIKE_US = registerHolder("music_disc.not_like_us");
    Holder<SoundEvent> MUSIC_DISC_RESISTANCE_INSTRUMENTAL = registerHolder("music_disc.resistance_instrumental");
    Holder<SoundEvent> MUSIC_DISC_BLISS_INSTRUMENTAL = registerHolder("music_disc.bliss_instrumental");
    Holder<SoundEvent> MUSIC_DISC_ENDLESSLY_INSTRUMENTAL = registerHolder("music_disc.endlessly_instrumental");
    Holder<SoundEvent> MUSIC_DISC_ENDLESSLY = registerHolder("music_disc.endlessly");
    Holder<SoundEvent> MUSIC_DISC_ENDLESSLY_STEREO = registerHolder("music_disc.endlessly.stereo");
    SoundEvent SUS = registerSound("sus");
    Holder<SoundEvent> MUSIC_DISC_SOU = registerHolder("music_disc.story_of_undertale");
    SoundEvent OOBLEP = registerSound("annoyance.ooblep");

    private static SoundEvent registerSound(String namespace) {
        Identifier id = PooSMPMod.id(namespace);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static Holder<SoundEvent> registerHolder(String namespace) {
        Identifier id = PooSMPMod.id(namespace);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP Mod sounds! Help me.");
    }
}
