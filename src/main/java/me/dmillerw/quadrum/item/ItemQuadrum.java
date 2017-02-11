package me.dmillerw.quadrum.item;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.item.data.ItemData;
import me.dmillerw.quadrum.lib.IQuadrumObject;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.lib.trait.Trait;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemQuadrum extends Item implements IQuadrumObject<ItemData> {

    private final ItemData itemData;

    public ItemQuadrum(ItemData itemData) {
        super();

        this.itemData = itemData;

        // Variants
        if (itemData.variants.length > 1)
            setHasSubtypes(true);

        // Creative Tabs
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(itemData.creativeTab)) {
                setCreativeTab(tab);
                break;
            }
        }

        if (getCreativeTab() == null) setCreativeTab(ModCreativeTab.TAB);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.addAll(itemData.traits.get(Trait.LORE).getValueFromItemStack(stack));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (hasSubtypes && stack.getMetadata() < itemData.variants.length) {
            return String.format("item.%s:%s", ModInfo.MOD_ID, itemData.variants[Math.abs(stack.getMetadata() % itemData.variants.length)]);
        } else {
            return String.format("item.%s:%s", ModInfo.MOD_ID, itemData.name);
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        if (hasSubtypes) {
            for (int i = 0; i < itemData.variants.length; i++) {
                subItems.add(new ItemStack(this, 1, i));
            }
        } else {
            super.getSubItems(itemIn, tab, subItems);
        }
    }

    @SideOnly(Side.CLIENT)
    public void initializeModels() {
        if (hasSubtypes) {
            List<ModelResourceLocation> modelResources = Lists.newArrayList();

            for (int i = 0; i < itemData.variants.length; i++) {
                modelResources.add(new ModelResourceLocation(ModInfo.MOD_ID + ":" + itemData.variants[i]));
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(ModInfo.MOD_ID + ":" + itemData.variants[i]));
            }

            ModelBakery.registerItemVariants(this, modelResources.toArray(new ModelResourceLocation[0]));
        } else {
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
        }
    }

    @Override
    public ItemData getObject() {
        return itemData;
    }
}
