package me.dmillerw.quadrum.item.lib;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.feature.trait.TraitContainer;
import me.dmillerw.quadrum.feature.trait.TraitHolder;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.feature.trait.impl.item.ItemVisual;
import me.dmillerw.quadrum.item.IQuadrumItem;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import me.dmillerw.quadrum.lib.ModInfo;
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
public class CommonItemMethods {

    public static void construct(IQuadrumItem item) {
        ItemData data = item.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        if (propertyHandler.hasSubtypes()) {
            ((Item)item).setHasSubtypes(true);
        }

        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(data.creativeTab)) {
                ((Item) item).setCreativeTab(tab);
                break;
            }
        }

        if (((Item) item).getCreativeTab() == null) ((Item) item).setCreativeTab(ModCreativeTab.TAB);
    }

    /* TRAIT - TOOLTIP */
    public static void addLoreToTooltip(IQuadrumItem item, ItemStack stack, List<String> tooltip) {
        TraitHolder<List<String>> trait = item.getObject().getTrait(Traits.COMMON_LORE, TraitContainer.TYPE_LIST);
        if (trait != null) {
            tooltip.addAll(trait.getValueFromItemStack(stack));
        }
    }

    /* TRAIT - VISUAL */
    public static boolean isEnchanted(IQuadrumItem item, ItemStack stack) {
        TraitHolder<ItemVisual> trait = item.getObject().getTrait(Traits.ITEM_VISUAL);
        if (trait != null) {
            return trait.getValueFromItemStack(stack).enchanted;
        }

        return stack.isItemEnchanted();
    }

    /* PROPERTY / VARIANTS */
    public static void getItemsForCreativeTab(IQuadrumItem item, NonNullList<ItemStack> list) {
        ItemData data = item.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        if (((Item)item).getHasSubtypes()) {
            for (int i = 0; i<propertyHandler.getSubtypes().length; i++) {
                list.add(new ItemStack((Item)item, 1, i));
            }
        } else {
            list.add(new ItemStack((Item)item));
        }
    }

    public static String getUnlocalizedStackName(IQuadrumItem item, ItemStack stack) {
        ItemData data = item.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        String[] variants = propertyHandler.getSubtypes();

        if (((Item)item).getHasSubtypes() && stack.getMetadata() < variants.length) {
            return String.format("item.%s:%s", ModInfo.MOD_ID, variants[Math.abs(stack.getMetadata() % variants.length)]);
        } else {
            return String.format("item.%s:%s", ModInfo.MOD_ID, data.name);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initializeModels(IQuadrumItem item) {
        ItemData data = item.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        String[] variants = propertyHandler.getSubtypes();

        if (((Item)item).getHasSubtypes()) {
            List<ModelResourceLocation> modelResources = Lists.newArrayList();

            for (int i = 0; i < variants.length; i++) {
                modelResources.add(new ModelResourceLocation(ModInfo.MOD_ID + ":" + variants[i]));
                ModelLoader.setCustomModelResourceLocation((Item)item, i, new ModelResourceLocation(ModInfo.MOD_ID + ":" + variants[i]));
            }

            ModelBakery.registerItemVariants((Item)item, modelResources.toArray(new ModelResourceLocation[0]));
        } else {
            ModelLoader.setCustomModelResourceLocation((Item)item, 0, new ModelResourceLocation(((Item)item).getRegistryName().toString()));
        }
    }
}
