package space.xiyan.phasmo;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = Phasmo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PhasmoConfig {

    public static ForgeConfigSpec COMMON_SPEC;
    public static Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    private PhasmoConfig() {
    }

    public static class Common {

        public final ForgeConfigSpec.IntValue radius;
        public final ForgeConfigSpec.IntValue timeGapMax;
        public final ForgeConfigSpec.IntValue timeGapMin;
        public final ForgeConfigSpec.DoubleValue maxPitch;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("PhasmoCraft Mod Configuration")
                    .push("General");
            radius = builder.defineInRange("detection_radius", 10, 0, Integer.MAX_VALUE);
            timeGapMax = builder.defineInRange("max_beep_gap", 20, 0, Integer.MAX_VALUE);
            timeGapMin = builder.defineInRange("min_beep_gap", 3, 0, Integer.MAX_VALUE);
            maxPitch = builder.defineInRange("max_pitch", 2.0, 1, Float.MAX_VALUE);
            builder.pop();
        }
    }
}
