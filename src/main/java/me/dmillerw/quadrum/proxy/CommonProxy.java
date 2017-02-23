package me.dmillerw.quadrum.proxy;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.loader.BlockLoader;
import me.dmillerw.quadrum.feature.loader.ItemLoader;
import me.dmillerw.quadrum.feature.trait.QuadrumTrait;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.item.IQuadrumItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * @author dmillerw
 */
public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BlockLoader.getBlocks().forEach(CommonProxy::registerOreDictionaryTags);
        ItemLoader.getItems().forEach(CommonProxy::registerOreDictionaryTags);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    private static void registerOreDictionaryTags(Block block) {
        BlockData data =((BlockQuadrum)block).getObject();
        for (int i=0; i<data.variants.length; i++) {
            ItemStack stack = new ItemStack(block, 1, i);
            registerOreDictionaryTags(data.traits.get(Traits.COMMON_ORE_DICTIONARY), stack);
        }
    }

    private static void registerOreDictionaryTags(IQuadrumItem item) {
        ItemData data = item.getObject();
        for (int i=0; i<data.variants.length; i++) {
            ItemStack stack = new ItemStack((Item)item, 1, i);
            registerOreDictionaryTags(data.traits.get(Traits.COMMON_ORE_DICTIONARY), stack);
        }
    }

    private static void registerOreDictionaryTags(QuadrumTrait<List<String>> trait, ItemStack stack) {
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
