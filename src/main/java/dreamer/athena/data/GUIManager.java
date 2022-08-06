package dreamer.athena.data;

import dreamer.athena.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class GUIManager implements Listener {
    private final Main plugin;

    public Inventory inv;

    public GUIManager(Main plugin) {
        this.plugin = plugin;
    }

    public void createGUI(Player p, String title) {
        inv = Bukkit.getServer().createInventory(p, 54, title);
        p.openInventory(inv);
        for (int i = 0; i < 54; i++) {
            if (i != 10 && i != 11) {
                inv.setItem(i, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "§a", "§a"));
            }
        }
    }

    public void editItem(String type, String name) {

    }

    public void itemList(String type, int page) {
        if (page == 1) {
            int count = 8;
            for (String key : plugin.data.getConfig("items/" + type + ".yml").getKeys(false)) {
                count++;
                if (count >= 9 && count <= 35) {
                    Material material = Material.valueOf(plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.material"));
                    ItemStack item = new ItemStack(material, 1);
                    ItemMeta meta = item.getItemMeta();

                    assert meta != null;
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(
                            plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.name"))));

                    ArrayList<String> lore = new ArrayList<>();
                    if (plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.damage") != null) {
                        String damageLang = plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-damage");
                        String damageItem = plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.damage");

                        assert damageLang != null;
                        lore.add(damageLang.replace("#", Objects.requireNonNull(damageItem)));
                    }
                    if (plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.critical-chance") != null) {
                        String criticalChanceLang = plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-critical-chance");
                        String criticalChanceItem = plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.critical-chance");

                        assert criticalChanceLang != null;
                        lore.add(criticalChanceLang.replace("#", Objects.requireNonNull(criticalChanceItem)));
                    }
                    if (plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.critical-damage") != null) {
                        String criticalDamageLang = plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-critical-damage");
                        String criticalDamageItem = plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.critical-damage");

                        assert criticalDamageLang != null;
                        lore.add(criticalDamageLang.replace("#", Objects.requireNonNull(criticalDamageItem)));
                    }
                    for (String s : plugin.data.getConfig(plugin.getConfig().getString("items/" + type + ".yml")).getStringList(key + ".base.lore")) {
                        if (s != null) {
                            lore.add("");
                            lore.add(ChatColor.translateAlternateColorCodes('&', s));
                        }
                    }

                    String rarity = plugin.data.getConfig("items/" + type + ".yml").getString(key + ".base.rarity");

                    if (plugin.rarity.getConfig().getKeys(false).contains(rarity)) {
                        if (rarity != null) {
                            lore.add("");
                            lore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.rarity.getConfig().getString(rarity + ".name"))));
                        }
                    }
                    lore.add("");
                    lore.add("§eRight-Click to obtain this item");
                    lore.add("§eLeft-Click to edit this item");
                    meta.setLore(lore);
                    meta.setLocalizedName(key);
                    item.setItemMeta(meta);
                    inv.setItem(count, item);

                }
            }
        }
    }

    public void browseGUI(Player p){
        inv = Bukkit.getServer().createInventory(p, 54, "§e§lAthena Items Browse");
        for(int i = 0; i < 54; i++){
            inv.setItem(i, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "§a", "§a"));
            if(i == 10){
                inv.setItem(i, createGuiItem(Material.IRON_AXE, "§aAxe", "§eClick to browse"));
            }
        }
        p.openInventory(inv);
    }
    public ItemStack createGuiItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        ArrayList<String> Lore = new ArrayList<>();
        Lore.add(lore);
        meta.setLore(Lore);

        item.setItemMeta(meta);

        return item;
    }
    @EventHandler
    public void GUIBrowse(InventoryClickEvent e){
        if (e.getView().getTitle().equalsIgnoreCase("§e§lAthena Items Browse")){
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getSlot() == 10){
                createGUI(p, "§a§lAxes");
                itemList("AXE", 1);
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§a§lAxes")){
            e.setCancelled(true);
            if (Objects.requireNonNull(e.getClickedInventory()).getItem(e.getSlot()) != null){
                if (Objects.requireNonNull(e.getClickedInventory().getItem(e.getSlot())).hasItemMeta()){
                    if (Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory().getItem(e.getSlot())).getItemMeta()).hasLocalizedName()){
                        if (e.getClick().isRightClick()) {
                            String type = "AXES";
                            String name = Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory().getItem(e.getSlot())).getItemMeta()).getLocalizedName();
                            Player p = (Player) e.getWhoClicked();

                            Material material = Material.valueOf(plugin.data.getConfig("items/"+type+".yml").getString(name + ".base.material"));
                            ItemStack item = new ItemStack(material, 1);
                            ItemMeta itemmeta = item.getItemMeta();

                            assert itemmeta != null;

                            ArrayList<String> lore = new ArrayList<>();

                            if (plugin.data.getConfig("items/" +type+ ".yml").getString(name + ".base.damage") != null) {
                                String damageLang = plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-damage");
                                String damageItem = plugin.data.getConfig("items/" + type + ".yml").getString(name + ".base.damage");

                                assert damageLang != null;
                                lore.add(damageLang.replace("#", Objects.requireNonNull(damageItem)));
                            }
                            if (plugin.data.getConfig("items/" +type+ ".yml").getString(name + ".base.critical-chance") != null) {
                                String criticalChanceLang = plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-critical-chance");
                                String criticalChanceItem = plugin.data.getConfig("items/" + type + ".yml").getString(name + ".base.critical-chance");

                                assert criticalChanceLang != null;
                                lore.add(criticalChanceLang.replace("#", Objects.requireNonNull(criticalChanceItem)));
                            }
                            if (plugin.data.getConfig("items/" +type+ ".yml").getString(name + ".base.critical-damage") != null) {
                                String criticalDamageLang = plugin.language.getConfig(plugin.getConfig().getString("language")).getString("items-critical-damage");
                                String criticalDamageItem = plugin.data.getConfig("items/" + type + ".yml").getString(name + ".base.critical-damage");

                                assert criticalDamageLang != null;
                                lore.add(criticalDamageLang.replace("#", Objects.requireNonNull(criticalDamageItem)));
                            }

                            itemmeta.setLore(lore);
                            itemmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.data.getConfig("items/AXES.yml").getString(name + ".base.name"))));
                            itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            item.setItemMeta(itemmeta);
                            p.getInventory().addItem(item);
                        }
                    }
                }

            }
        }
    }
}
