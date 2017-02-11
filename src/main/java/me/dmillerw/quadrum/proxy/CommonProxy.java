package me.dmillerw.quadrum.proxy;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.block.data.BlockLoader;
import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.item.data.ItemData;
import me.dmillerw.quadrum.item.data.ItemLoader;
import me.dmillerw.quadrum.lib.trait.Trait;
import me.dmillerw.quadrum.lib.trait.impl.StringListTrait;
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
            registerOreDictionaryTags(data.traits.get(Trait.ORE_DICTIONARY), stack);
        }
    }

    private static void registerOreDictionaryTags(Item item) {
        ItemData data = ((ItemQuadrum)item).getObject();
        for (int i=0; i<data.variants.length; i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            registerOreDictionaryTags(data.traits.get(Trait.ORE_DICTIONARY), stack);
        }
    }

    private static void registerOreDictionaryTags(StringListTrait trait, ItemStack stack) {
        List<String> tags = trait.getValueFromItemStack(stack);
        if (!tags.isEmpty()) {
            for (String tag : tags) {
                OreDictionary.registerOre(tag, stack);
            }
        }
    }
}
