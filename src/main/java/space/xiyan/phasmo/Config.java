package space.xiyan.phasmo;

import net.minecraft.util.ResourceLocation;

public class Config {
    private int radius = 10;
    private ResourceLocation tracingEntity = new ResourceLocation("minecraft:pig");
    private static final Config INSTANCE = new Config();

    public static Config getInstance() {
        return INSTANCE;
    }

    public void setRadius(int radius) {
        this.radius = Math.max(radius, 0);
    }

    public int getRadius() {
        return radius;
    }

    public ResourceLocation getTracingEntity() {
        return tracingEntity;
    }

    public void setTracingEntity(ResourceLocation tracingEntity) {
        this.tracingEntity = tracingEntity;
    }

    private Config() {}
}
