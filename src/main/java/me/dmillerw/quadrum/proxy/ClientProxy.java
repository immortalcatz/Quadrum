package me.dmillerw.quadrum.proxy;

import me.dmillerw.quadrum.block.IQuadrumBlock;
import me.dmillerw.quadrum.feature.loader.BlockLoader;
import me.dmillerw.quadrum.feature.loader.ItemLoader;
import me.dmillerw.quadrum.item.IQuadrumItem;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        BlockLoader.getBlocks().forEach((t) -> ((IQuadrumBlock)t).initializeClient());
        ItemLoader.getItems().forEach((t) -> ((IQuadrumItem)t).initializeClient());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
