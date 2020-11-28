package space.xiyan.phasmo;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Phasmo.MOD_ID)
public class Phasmo {

  static final String MOD_ID = "phasmo";

  public Phasmo() {
    ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    SoundEventRegistry.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    ModLoadingContext.get()
            .registerConfig(ModConfig.Type.COMMON, PhasmoConfig.COMMON_SPEC, "phasmo_config.toml");
  }
}
