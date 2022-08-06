package dreamer.athena.addons;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import dreamer.athena.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;

public class Mana extends BukkitRunnable implements Listener {
    private final Main plugin;

    public static HashMap<Player, Integer> maxmana = new HashMap<>();
    public static HashMap<Player, Integer> mana = new HashMap<>();


    public Mana(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (mana.get(p) != null) {
                if (maxmana.get(p) != null) {

                    int health = (int) p.getHealth();
                    int maxhealth = (int) p.getMaxHealth();
                    double multiplier = Double.parseDouble(Objects.requireNonNull(plugin.getConfig().getString("action-bar.mana-regen-multiplier")));

                    if (health > maxhealth) {
                        p.setHealth(maxhealth);
                    }
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c" + health + "/" + maxhealth + "❤" + "          " + "§b" + mana.get(p) + "/" + (maxmana.get(p) + (int) AureliumAPI.getMaxMana(p)) + "✤"));
                    if (mana.get(p) + maxmana.get(p) * multiplier < (maxmana.get(p) + AureliumAPI.getMaxMana(p))) {
                        mana.put(p, (int) (mana.get(p) + (maxmana.get(p) + AureliumAPI.getMaxMana(p)) * multiplier));
                    } else {
                        mana.put(p, (int) ((maxmana.get(p) + AureliumAPI.getMaxMana(p)) - mana.get(p) + mana.get(p)));
                    }
                } else {
                    maxmana.put(p, Integer.valueOf(Objects.requireNonNull(plugin.getConfig().getString("action-bar.normal-max-mana"))));
                }
            } else {
                mana.put(p, 0);
            }
        }
    }

    @EventHandler
    public void ManaOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (p.getInventory().getHelmet() != null) {
                ItemStack helmet = p.getInventory().getHelmet();
                if (helmet.hasItemMeta()) {
                    if (Objects.requireNonNull(helmet.getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(helmet.getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = ChatColor.stripColor(itemLore.replace("Max Mana", "").replace("✤", "").replace("&", "")
                                        .replace("+", "").replace(" ", "").replace(":", ""));
                                int total = Integer.parseInt(count);
                                maxmana.put(p, maxmana.get(p) + total);
                            }
                        }
                    }
                }
            }
            if (p.getInventory().getChestplate() != null) {
                ItemStack chestplate = p.getInventory().getChestplate();
                if (chestplate.hasItemMeta()) {
                    if (Objects.requireNonNull(chestplate.getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(chestplate.getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = ChatColor.stripColor(itemLore.replace("Max Mana", "").replace("✤", "").replace("&", "")
                                        .replace("+", "").replace(" ", "").replace(":", ""));
                                int total = Integer.parseInt(count);
                                maxmana.put(p, maxmana.get(p) + total);
                            }
                        }
                    }
                }
            }
            if (p.getInventory().getLeggings() != null) {
                ItemStack leggings = p.getInventory().getLeggings();
                if (leggings.hasItemMeta()) {
                    if (Objects.requireNonNull(leggings.getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(leggings.getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = ChatColor.stripColor(itemLore.replace("Max Mana", "").replace("✤", "").replace("&", "")
                                        .replace("+", "").replace(" ", "").replace(":", ""));
                                int total = Integer.parseInt(count);
                                maxmana.put(p, maxmana.get(p) + total);
                            }
                        }
                    }
                }
            }
            if (p.getInventory().getBoots() != null) {
                ItemStack boots = p.getInventory().getBoots();
                if (boots.hasItemMeta()) {
                    if (Objects.requireNonNull(boots.getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(boots.getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = ChatColor.stripColor(itemLore.replace("Max Mana", "").replace("✤", "").replace("&", "")
                                        .replace("+", "").replace(" ", "").replace(":", ""));
                                int total = Integer.parseInt(count);
                                maxmana.put(p, maxmana.get(p) + total);
                            }
                        }
                    }
                }
            }
        }, 60);
    }

    @EventHandler
    public void ManaOnArmor(ArmorEquipEvent e) {
        if (e.getPlayer().isOnline()) {
            Player p = e.getPlayer();
            //On Equip Increase Mana
            if (e.getNewArmorPiece() != null) {
                if (e.getNewArmorPiece().hasItemMeta()) {
                    if (Objects.requireNonNull(e.getNewArmorPiece().getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(e.getNewArmorPiece().getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = itemLore.replace("Max Mana", "").replace("✤", "").replace("&", "")
                                        .replace("+", "").replace(" ", "").replace(":", "");
                                count = ChatColor.stripColor(count);
                                int total = Integer.parseInt(count);
                                maxmana.put(p, maxmana.get(p) + total);

                            }
                        }
                    }
                }
            }
            //On Unequip Decrease Mana
            if (e.getOldArmorPiece() != null) {
                if (e.getOldArmorPiece().hasItemMeta()) {
                    if (Objects.requireNonNull(e.getOldArmorPiece().getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(e.getOldArmorPiece().getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = itemLore.replace("Max Mana", "").replace("✤", "").replace("&", "")
                                        .replace("+", "").replace(" ", "").replace(":", "");
                                count = ChatColor.stripColor(count);
                                int total = Integer.parseInt(count);
                                maxmana.put(p, Math.max(maxmana.get(p) - total, 100));
                                if (mana.get(p) > maxmana.get(p) + AureliumAPI.getMaxMana(p)) {
                                    mana.put(p, (int) (maxmana.get(p) + AureliumAPI.getMaxMana(p)));
                                }

                            }

                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        if (Objects.requireNonNull(e.getOffHandItem()).hasItemMeta()) {
            if (Objects.requireNonNull(e.getOffHandItem().getItemMeta()).hasLore()) {
                if (!plugin.getConfig().getStringList("action-bar.blacklisted-item-held")
                        .contains(Objects.requireNonNull(e.getOffHandItem().getType().name()))) {
                    for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getOffHandItem().getItemMeta()).getLore()))) {
                        if (itemLore.contains("Max Mana:")) {
                            String count = itemLore.replace("Max Mana:", "").replace("✤", "").replace("+", "").replace(" ", "");
                            count = ChatColor.stripColor(count);
                            int total = Integer.parseInt(count);
                            maxmana.put(p, Math.max(maxmana.get(p) - total, 100));
                            if (mana.get(p) > maxmana.get(p) + AureliumAPI.getMaxMana(p)) {
                                mana.put(p, (int) (maxmana.get(p) + AureliumAPI.getMaxMana(p)));
                            }
                        }
                    }
                }
            }
        }
        if (Objects.requireNonNull(e.getMainHandItem()).hasItemMeta()) {
            if (Objects.requireNonNull(e.getMainHandItem().getItemMeta()).hasLore()) {
                if (!plugin.getConfig().getStringList("action-bar.blacklisted-item-held")
                        .contains(Objects.requireNonNull(e.getMainHandItem().getType().name()))) {
                    for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getMainHandItem().getItemMeta()).getLore()))) {
                        if (itemLore.contains("Max Mana:")) {
                            String count = itemLore.replace("Max Mana:", "").replace("✤", "").replace("+", "").replace(" ", "");
                            count = ChatColor.stripColor(count);
                            int total = Integer.parseInt(count);
                            maxmana.put(p, maxmana.get(p) + total);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void increaseOnhand(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItem(e.getNewSlot()) != null) {
            if (!plugin.getConfig().getStringList("action-bar.blacklisted-item-held")
                    .contains(Objects.requireNonNull(p.getInventory().getItem(e.getNewSlot())).getType().name())) {
                if (Objects.requireNonNull(p.getInventory().getItem(e.getNewSlot())).hasItemMeta()) {
                    if (Objects.requireNonNull(Objects.requireNonNull(p.getInventory().getItem(e.getNewSlot())).getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(p.getInventory().getItem(e.getNewSlot())).getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = itemLore.replace("Max Mana:", "").replace("✤", "").replace("+", "").replace(" ", "");
                                count = ChatColor.stripColor(count);
                                int total = Integer.parseInt(count);
                                maxmana.put(p, maxmana.get(p) + total);

                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void decreaseOnhand(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItem(e.getPreviousSlot()) != null) {
            if (!plugin.getConfig().getStringList("action-bar.blacklisted-item-held")
                    .contains(Objects.requireNonNull(p.getInventory().getItem(e.getPreviousSlot())).getType().name())) {
                if (Objects.requireNonNull(p.getInventory().getItem(e.getPreviousSlot())).hasItemMeta()) {
                    if (Objects.requireNonNull(Objects.requireNonNull(p.getInventory().getItem(e.getPreviousSlot())).getItemMeta()).hasLore()) {
                        for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(p.getInventory().getItem(e.getPreviousSlot())).getItemMeta()).getLore())) {
                            if (itemLore.contains("Max Mana:")) {
                                String count = itemLore.replace("Max Mana:", "").replace("✤", "").replace("+", "").replace(" ", "");
                                count = ChatColor.stripColor(count);
                                int total = Integer.parseInt(count);
                                maxmana.put(p, Math.max(maxmana.get(p) - total, 100));
                                if (mana.get(p) > maxmana.get(p) + AureliumAPI.getMaxMana(p)) {
                                    mana.put(p, (int) (maxmana.get(p) + AureliumAPI.getMaxMana(p)));
                                }
                            }
                        }

                    }

                }
            }
        }

    }

    @EventHandler
    public void decreaseOnclick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() != null) {
            ItemStack item = e.getCurrentItem();
            assert item != null;
            if (p.getInventory().getHeldItemSlot() == e.getSlot()) {
                if (!plugin.getConfig().getStringList("action-bar.blacklisted-item-held")
                        .contains(Objects.requireNonNull(item.getType().name()))) {
                    if (item.hasItemMeta()) {
                        if (Objects.requireNonNull(item.getItemMeta()).hasLore()) {
                            for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(item.getItemMeta()).getLore())) {
                                if (itemLore.contains("Max Mana:")) {
                                    String count = itemLore.replace("Max Mana:", "").replace("✤", "").replace("+", "").replace(" ", "");
                                    count = ChatColor.stripColor(count);
                                    int total = Integer.parseInt(count);
                                    maxmana.put(p, Math.max(maxmana.get(p) - total, 100));
                                    if (mana.get(p) > maxmana.get(p) + AureliumAPI.getMaxMana(p)) {
                                        mana.put(p, (int) (maxmana.get(p) + AureliumAPI.getMaxMana(p)));
                                    }
                                }
                            }
                        }
                    }
                }

            }
            if (Objects.requireNonNull(e.getCursor()).hasItemMeta()) {
                if (!plugin.getConfig().getStringList("action-bar.blacklisted-item-held")
                        .contains(Objects.requireNonNull(e.getCursor().getType().name()))) {
                    if (p.getInventory().getHeldItemSlot() == e.getSlot()) {
                        if (e.getCursor().hasItemMeta()) {
                            if (Objects.requireNonNull(e.getCursor().getItemMeta()).hasLore()) {
                                for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(e.getCursor().getItemMeta()).getLore())) {
                                    if (itemLore.contains("Max Mana:")) {
                                        String count = itemLore.replace("Max Mana:", "").replace("✤", "").replace("+", "").replace(" ", "");
                                        count = ChatColor.stripColor(count);
                                        int total = Integer.parseInt(count);
                                        maxmana.put(p, maxmana.get(p) + total);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    public static int getMaxMana(Player p){
        return (int) (maxmana.get(p) + AureliumAPI.getMaxMana(p));
    }
    public static int getMana(Player p){
        return mana.get(p);
    }
    public static int getManaCost(Player p){
        if (p.getItemInHand().hasItemMeta()){
            if (Objects.requireNonNull(p.getItemInHand().getItemMeta()).hasLore()){
                for (String itemLore : Objects.requireNonNull(Objects.requireNonNull(p.getItemInHand().getItemMeta().getLore()))) {
                    if (itemLore.contains("Skill Cost:")) {
                        String count = itemLore.replace("Skill Cost:", "").replace("✤", "").replace("Mana", "").replace(" ", "");
                        count = ChatColor.stripColor(count);
                        return Integer.parseInt(count);
                    }
                }
            }

        }
        return 0;
    }
    public static void removeMana(Player p, int total){
        if (p.isOnline()){
            if (total <= maxmana.get(p) + AureliumAPI.getMaxMana(p)){
                mana.put(p, mana.get(p)-total);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b-"+total+" Mana✤"));
            }else{
               notEnoughMana(p);
            }

        }
    }
    public static void notEnoughMana(Player p){
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lNOT ENOUGH MANA"));
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10F, 1F);
    }
}