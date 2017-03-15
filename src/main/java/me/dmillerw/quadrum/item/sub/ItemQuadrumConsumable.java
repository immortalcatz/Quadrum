package me.dmillerw.quadrum.item.sub;

import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.property.data.Consumable;
import me.dmillerw.quadrum.item.IQuadrumItem;
import me.dmillerw.quadrum.item.lib.CommonItemMethods;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemQuadrumConsumable extends ItemFood implements IQuadrumItem {

    private final ItemData itemData;
    private final Consumable consumable;

    public ItemQuadrumConsumable(ItemData itemData) {
        // Send dummy impl to the constructor, everything is handled via stack-sensitive getters
        super(0, 0, false);

        this.itemData = itemData;
        this.consumable = itemData.getPropertyData();

        CommonItemMethods.construct(this);
    }

    @Override
    public ItemData getObject() {
        return this.itemData;
    }

    /* PROPERTY - CONSUMABLE */
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return consumable.type;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return consumable.duration;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (consumable.stats == null || consumable.stats.canAlwaysEat) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode)
            stack.shrink(1);

        if (entityLiving instanceof EntityPlayer && consumable.stats != null) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            entityplayer.getFoodStats().addStats(this, stack);
            worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        if (!consumable.resultingItem.isEmpty() && stack.getCount() <= 0)
            stack = consumable.resultingItem.copy();

        return stack;
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        return consumable.stats.health;
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        return consumable.stats.saturation;
    }

    /* TRAIT - TOOLTIP */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        CommonItemMethods.addLoreToTooltip(this, stack, tooltip);
    }

    /* TRAIT - VISUAL */
    @Override
    public boolean hasEffect(ItemStack stack) {
        return CommonItemMethods.isEnchanted(this, stack);
    }

    /* PROPERTY / VARIANTS */
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return CommonItemMethods.getUnlocalizedStackName(this, stack);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        CommonItemMethods.getItemsForCreativeTab(this, subItems);
    }
}
