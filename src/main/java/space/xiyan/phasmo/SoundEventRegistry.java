package space.xiyan.phasmo;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundEventRegistry {
  public static final DeferredRegister SOUNDS =
          DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Phasmo.MOD_ID);
  public static RegistryObject<SoundEvent> meaSound =
          SOUNDS.register("mea", () -> new SoundEvent(new ResourceLocation(Phasmo.MOD_ID, "mea")));
}
