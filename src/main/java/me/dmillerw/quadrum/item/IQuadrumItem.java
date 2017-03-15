package me.dmillerw.quadrum.item;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.data.IQuadrumObject;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.feature.trait.TraitContainer;
import me.dmillerw.quadrum.feature.trait.TraitHolder;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.feature.trait.impl.item.ItemVisual;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
        PropertyHandler propertyHandler = itemData.properties.propertyHandler;

        // Variants
        if (propertyHandler.hasSubtypes(itemData))
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
        TraitHolder<List<String>> trait = getObject().getTrait(Traits.COMMON_LORE, TraitContainer.TYPE_LIST);
        if (trait != null) {
            tooltip.addAll(trait.getValueFromItemStack(stack));
        }
    }

    public default String getUnlocalizedStackName(ItemStack stack) {
        final ItemData itemData = getObject();
        PropertyHandler propertyHandler = itemData.properties.propertyHandler;
        String[] variants = propertyHandler.getSubtypes(itemData);

        if (((Item)this).getHasSubtypes() && stack.getMetadata() < variants.length) {
            return String.format("item.%s:%s", ModInfo.MOD_ID, variants[Math.abs(stack.getMetadata() % variants.length)]);
        } else {
            return String.format("item.%s:%s", ModInfo.MOD_ID, itemData.name);
        }
    }

    public default void getItemsForCreativeTab(NonNullList<ItemStack> list) {
        PropertyHandler propertyHandler = getObject().properties.propertyHandler;

        if (((Item)this).getHasSubtypes()) {
            for (int i=0; i<propertyHandler.getSubtypes(getObject()).length; i++) {
                list.add(new ItemStack((Item)this, 1, i));
            }
        } else {
            list.add(new ItemStack((Item)this));
        }
    }

    public default boolean isEnchanted(ItemStack stack) {
        TraitHolder<ItemVisual> trait = getObject().getTrait(Traits.ITEM_VISUAL);
        if (trait != null) {
            ItemVisual visual = trait.getValueFromItemStack(stack);
            if (visual != null) {
                return visual.enchanted;
            }
        }

        return stack.isItemEnchanted();
    }

    @SideOnly(Side.CLIENT)
    public default void initializeModels() {
        PropertyHandler propertyHandler = getObject().properties.propertyHandler;
        String[] variants = propertyHandler.getSubtypes(getObject());

        if (((Item)this).getHasSubtypes()) {
            List<ModelResourceLocation> modelResources = Lists.newArrayList();

            for (int i = 0; i < variants.length; i++) {
                modelResources.add(new ModelResourceLocation(ModInfo.MOD_ID + ":" + variants[i]));
                ModelLoader.setCustomModelResourceLocation((Item)this, i, new ModelResourceLocation(ModInfo.MOD_ID + ":" + variants[i]));
            }

            ModelBakery.registerItemVariants((Item)this, modelResources.toArray(new ModelResourceLocation[0]));
        } else {
            ModelLoader.setCustomModelResourceLocation((Item)this, 0, new ModelResourceLocation(((Item)this).getRegistryName().toString()));
        }
    }
}
