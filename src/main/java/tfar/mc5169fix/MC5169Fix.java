package tfar.mc5169fix;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MC5169Fix.MODID)
public class MC5169Fix {

	public static final String MODID = "mc5169fix";
	public static int distSquared = 0;
	private static ForgeConfigSpec.IntValue textRenderDistance;

	public MC5169Fix() {
		final Pair<MC5169Fix, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(this::buildConfigs);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::bakeConfigs);
	}

	public static MC5169Fix CLIENT;
	public static ForgeConfigSpec CLIENT_SPEC;

	private MC5169Fix buildConfigs(ForgeConfigSpec.Builder builder) {
		builder.push("client");
		textRenderDistance = builder
						.comment("Render distance of text on signs")
						.defineInRange("text_render_distance", 16, 0, 512);
		builder.pop();
		return this;
	}

	private void bakeConfigs(ModConfig.ModConfigEvent e) {
		if (e.getConfig().getModId().equals(MODID)) {
			distSquared = textRenderDistance.get() * textRenderDistance.get();
		}
	}
}
