package me.dmillerw.quadrum.item;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.item.data.ItemData;
import me.dmillerw.quadrum.lib.IQuadrumObject;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.trait.QuadrumTrait;
import me.dmillerw.quadrum.trait.Traits;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author dmillerw
 */
public interface IQuadrumItem extends IQuadrumObject<ItemData> {

    public default void construct() {
        final ItemData itemData = getObject();

        // Variants
        if (getObject().variants.length > 1)
            ((Item)this).setHasSubtypes(true);

        // Creative Tabs
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(itemData.creativeTab)) {
                ((Item)this).setCreativeTab(tab);
                break;
            }
        }

        if (((Item)this).getCreativeTab() == null) ((Item)this).setCreativeTab(ModCreativeTab.TAB);
    }

    public default void addLoreToTooltip(ItemStack stack, List<String> tooltip) {
        QuadrumTrait<List<String>> trait = getObject().traits.get(Traits.LORE, Traits.TYPE_LIST);
        if (trait != null) {
            tooltip.addAll(trait.getValueFromItemStack(stack));
        }
    }

    public default String getUnlocalizedStackName(ItemStack stack) {
        final ItemData itemData = getObject();
        if (((Item)this).getHasSubtypes() && stack.getMetadata() < itemData.variants.length) {
            return String.format("item.%s:%s", ModInfo.MOD_ID, itemData.variants[Math.abs(stack.getMetadata() % itemData.variants.length)]);
        } else {
            return String.format("item.%s:%s", ModInfo.MOD_ID, itemData.name);
        }
    }

    public default void getItemsForCreativeTab(List<ItemStack> list) {
        if (((Item)this).getHasSubtypes()) {
            for (int i=0; i<getObject().variants.length; i++) {
                list.add(new ItemStack((Item)this, 1, i));
            }
        } else {
            list.add(new ItemStack((Item)this));
        }
    }

    @SideOnly(Side.CLIENT)
    public default void initializeModels() {
        if (((Item)this).getHasSubtypes()) {
            List<ModelResourceLocation> modelResources = Lists.newArrayList();

            for (int i = 0; i < this.getObject().variants.length; i++) {
                modelResources.add(new ModelResourceLocation(ModInfo.MOD_ID + ":" + this.getObject().variants[i]));
                ModelLoader.setCustomModelResourceLocation((Item)this, i, new ModelResourceLocation(ModInfo.MOD_ID + ":" + this.getObject().variants[i]));
            }

            ModelBakery.registerItemVariants((Item)this, modelResources.toArray(new ModelResourceLocation[0]));
        } else {
            ModelLoader.setCustomModelResourceLocation((Item)this, 0, new ModelResourceLocation(((Item)this).getRegistryName().toString()));
        }
    }
}
