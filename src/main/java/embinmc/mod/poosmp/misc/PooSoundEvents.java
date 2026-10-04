package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public interface PooSoundEvents {
    SoundEvent MUSIC_DISC_TRIFECTA_CAP = registerSound("music_disc.trifecta_cap");
    SoundEvent MUSIC_DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = registerSound("music_disc.butterflies_and_hurricanes_instrumental");
    SoundEvent MUSIC_DISC_BUDDY_HOLLY = registerSound("music_disc.buddy_holly");
    SoundEvent MUSIC_DISC_STEREO_MADNESS = registerSound("music_disc.stereo_madness");
    SoundEvent MUSIC_DISC_NOT_LIKE_US = registerSound("music_disc.not_like_us");
    SoundEvent MUSIC_DISC_RESISTANCE_INSTRUMENTAL = registerSound("music_disc.resistance_instrumental");
    SoundEvent MUSIC_DISC_BLISS_INSTRUMENTAL = registerSound("music_disc.bliss_instrumental");
    SoundEvent MUSIC_DISC_ENDLESSLY_INSTRUMENTAL = registerSound("music_disc.endlessly_instrumental");
    SoundEvent MUSIC_DISC_ENDLESSLY = registerSound("music_disc.endlessly");
    SoundEvent MUSIC_DISC_ENDLESSLY_STEREO = registerSound("music_disc.endlessly.stereo");
    SoundEvent SUS = registerSound("sus");
    SoundEvent MUSIC_DISC_SOU = registerSound("music_disc.story_of_undertale");
    SoundEvent OOBLEP = registerSound("annoyance.ooblep");

    private static SoundEvent registerSound(String namespace) {
        Identifier id = PooSMPMod.id(namespace);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP Mod sounds! Help me.");
    }
}
