package me.winterguardian.mobracers.item.types;

import me.winterguardian.core.util.PlayerUtil;
import me.winterguardian.core.util.SoundEffect;
import me.winterguardian.mobracers.CourseMessage;
import me.winterguardian.mobracers.MobRacersPlugin;
import me.winterguardian.mobracers.item.ItemResult;
import me.winterguardian.mobracers.item.types.ShieldItem;
import me.winterguardian.mobracers.state.game.GameState;
import me.winterguardian.mobracers.vehicle.Vehicle;
import me.winterguardian.mobracers.item.Item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LightningItem extends Item
{
    @Override
    public String getName()
    {
        return CourseMessage.ITEM_SPECIAL_UNDEADHORSE.toString();
    }

    @Override
    public me.winterguardian.mobracers.item.ItemType getType()
    {
        return me.winterguardian.mobracers.item.ItemType.LIGHTNING;
    }

    @Override
    public ItemStack getItemStack()
    {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§r§8" + CourseMessage.ITEM_SPECIAL_UNDEADHORSE.toString());
        item.setItemMeta(itemMeta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    @Override
    public ItemResult onUse(Player player, Vehicle vehicle, final GameState game)
    {
        for(final Player p : MobRacersPlugin.getGame().getPlayers())
        {
            if(p == player)
                continue;

            if(game.getPlayerData(p) == null)
                continue;

            if(game.getPosition(p) > game.getPosition(player))
                continue;

            if(!game.getPlayerData(p).isFinished())
            {
                if(ShieldItem.protect(p))
                    continue;

                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 0, false, false));

                new SoundEffect(Sound.AMBIENCE_THUNDER, 1, 1).play(p);

                final Vehicle victim_vehicle = game.getPlayerData(p).getVehicle();

                victim_vehicle.setSpeed(game.getPlayerData(p).getVehicle().getSpeed() - 0.5f);

                Bukkit.getScheduler().runTaskLater(MobRacersPlugin.getPlugin(), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        victim_vehicle.setSpeed(game.getPlayerData(p).getVehicle().getSpeed() + 0.5f);
                        PlayerUtil.heal(p);
                    }
                }, 100);
            }
        }

        return ItemResult.DISCARD;
    }

    @Override
    public void cancel()
    {

    }
}
