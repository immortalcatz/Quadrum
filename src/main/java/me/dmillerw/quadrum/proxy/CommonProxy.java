package me.dmillerw.quadrum.proxy;

import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.feature.trait.TraitHolder;
import me.dmillerw.quadrum.item.IQuadrumItem;
import me.dmillerw.quadrum.network.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * @author dmillerw
 */
public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Quadrum.INSTANCE, new GuiHandler());

//        BlockLoader.getBlocks().forEach(CommonProxy::registerOreDictionaryTags);
//        ItemLoader.getItems().forEach(CommonProxy::registerOreDictionaryTags);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private static void registerOreDictionaryTags(Block block) {
//        BlockData data =((BlockQuadrum)block).getObject();
//        for (int i=0; i<data.variants.length; i++) {
//            ItemStack stack = new ItemStack(block, 1, i);
//            registerOreDictionaryTags(data.getTrait(Traits.COMMON_ORE_DICTIONARY), stack);
//        }
    }

    private static void registerOreDictionaryTags(IQuadrumItem item) {
//        ItemData data = item.getObject();
//        for (int i=0; i<data.variants.length; i++) {
//            ItemStack stack = new ItemStack((Item)item, 1, i);
//            registerOreDictionaryTags(data.getTrait(Traits.COMMON_ORE_DICTIONARY), stack);
//        }
    }

    private static void registerOreDictionaryTags(TraitHolder<List<String>> trait, ItemStack stack) {
        if (trait == null)
            return;

        List<String> tags = trait.getValueFromItemStack(stack);
        if (!tags.isEmpty()) {
            for (String tag : tags) {
                OreDictionary.registerOre(tag, stack);
            }
        }
    }
}
