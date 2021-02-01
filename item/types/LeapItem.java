package me.winterguardian.mobracers.item.types;

import me.winterguardian.core.particle.ParticleType;
import me.winterguardian.core.particle.ParticleUtil;
import me.winterguardian.mobracers.CourseMessage;
import me.winterguardian.mobracers.MobRacersPlugin;
import me.winterguardian.mobracers.item.ItemResult;
import me.winterguardian.mobracers.state.game.GameState;
import me.winterguardian.mobracers.vehicle.Vehicle;
import me.winterguardian.mobracers.item.Item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class LeapItem extends Item
{

    public LeapItem()
    {
    }

    @Override
    public String getName()
    {
        return CourseMessage.ITEM_SPECIAL_PIG.toString();
    }

    @Override
    public me.winterguardian.mobracers.item.ItemType getType()
    {
        return me.winterguardian.mobracers.item.ItemType.LEAP;
    }

    @Override
    public ItemResult onUse(Player player, Vehicle vehicle, GameState game)
    {
        if(vehicle == null || vehicle.getEntity() == null || game.getPlayerData(player).isFinished())
            return ItemResult.KEEP;


        vehicle.getEntity().setVelocity(new Vector(Math.sin(Math.toRadians(-player.getLocation().getYaw())) * 2.1, 0.8, Math.cos(Math.toRadians(-player.getLocation().getYaw())) * 1.8));

        Location loc = player.getEyeLocation();

        for(Player p : MobRacersPlugin.getGame().getPlayers())
        {
            p.playSound(player.getLocation(), Sound.STEP_GRASS, 1f, 1f);

            p.playSound(player.getLocation().clone().add(2, 0, 0), Sound.STEP_GRASS, 1f, 1f);
            p.playSound(player.getLocation().clone().add(-2, 0, 0), Sound.STEP_GRASS, 1f, 1f);
            p.playSound(player.getLocation().clone().add(0, 0, 2), Sound.STEP_GRASS, 1f, 1f);
            p.playSound(player.getLocation().clone().add(0, 0, -2), Sound.STEP_GRASS, 1f, 1f);

            ParticleUtil.playBlockParticles(p, player.getLocation(), ParticleType.SLIME, 1.5f, 1.5f, 1.5f, 0, 100, Material.AIR.getId(), 0);
        }


        for(int x = loc.getBlockX() - 2; x <= loc.getBlockX() + 2; x++)
            for(int z = loc.getBlockZ() - 2; z <= loc.getBlockZ() + 2; z++)
            {
                if (Math.abs(x - loc.getBlockX()) == 2) {
                    loc.getBlockZ();
                }

            }

        return ItemResult.DISCARD;
    }

    @Override
    public ItemStack getItemStack()
    {
        ItemStack item = new ItemStack(Material.RABBIT_FOOT, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§r§6" + CourseMessage.ITEM_SPECIAL_PIG.toString());
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public void cancel()
    {

    }

}
