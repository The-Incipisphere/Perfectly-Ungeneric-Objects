package tld.yourusernameororgname;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(ReplaceMeWithModName.MOD_ID)
public class ReplaceMeWithModName {
    public static final String MOD_ID = "replaceme";
    public static final String MOD_NAME = "ReplaceMe";

    private static final Logger LOGGER = LogUtils.getLogger();

    public FMLJavaModLoadingContext modLoadingContext = FMLJavaModLoadingContext.get();
    public IEventBus modBus = modLoadingContext.getModEventBus();

    public ReplaceMeWithModName() {
        ReplaceMeWithModName.init();

        // DistExecutor allows you to make sure that the correct code is being loaded for the current side that the mod is running on.
        // DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static void init() {
        LOGGER.info("We're loading {} on the {}", MOD_NAME, FMLEnvironment.dist);
    }
}
