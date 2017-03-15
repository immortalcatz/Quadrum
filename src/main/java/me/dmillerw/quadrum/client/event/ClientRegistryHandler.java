package me.dmillerw.quadrum.client.event;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.loader.BlockLoader;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author dmillerw
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {
        for (ItemBlock block : BlockLoader.getItemBlocks()) {
            BlockData data = ((BlockQuadrum) block.block).getObject();
            PropertyHandler propertyHandler = data.properties.propertyHandler;

            if (propertyHandler.hasSubtypes(data)) {
                String[] variants = propertyHandler.getSubtypes(data);
                for (int i = 0; i < variants.length; i++) {
                    String state = variants[i];
                    ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName(), "variant=" + state);
                    ModelLoader.setCustomModelResourceLocation(block, i, location);
                }
            } else {
                ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName(), "inventory");
                ModelLoader.setCustomModelResourceLocation(block, 0, location);
            }
        }
    }
}
