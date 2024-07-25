package enxada.TesteRecrutamento.command;

import enxada.TesteRecrutamento.Wind_Change;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WindChargerTestCommand implements CommandExecutor, TabCompleter {
    private final Wind_Change plugin;

    public WindChargerTestCommand(Wind_Change plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        if (args.length == 0) {
            ItemStack item = new ItemStack(Material.WIND_CHARGE, 64);
            player.getInventory().addItem(item);
            return true;
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("configure")) {
                switch (args[1]) {
                    case "power":
                        try {
                            double power = Double.parseDouble(args[2]);
                            if (power < 0) {
                                player.sendMessage("O valor de power não pode ser negativo.");
                                return true;
                            }
                            plugin.getConfig().set("wind-power", power);
                            plugin.saveConfig();
                            plugin.reloadConfig();
                            player.sendMessage("Wind Power: " + plugin.getWindPower());
                        } catch (NumberFormatException e) {
                            player.sendMessage("Valor inválido para power. Por favor, insira um número válido.");
                        }
                        break;
                    case "particles":
                        String particle = args[2].toUpperCase();
                        if (isValidParticle(particle)) {
                            plugin.getConfig().set("wind-particles", particle);
                            plugin.saveConfig();
                            plugin.reloadConfig();
                            player.sendMessage("wind-particles: " + plugin.getWindParticles());
                        } else {
                            player.sendMessage("Partícula inválida. Por favor, insira uma partícula válida.");
                        }
                        break;
                    case "count-particles":
                        try {
                            int count = Integer.parseInt(args[2]);
                            if (count < 0) {
                                player.sendMessage("O valor de count-particles não pode ser negativo.");
                                return true;
                            }
                            plugin.getConfig().set("wind-count-particles", count);
                            plugin.saveConfig();
                            plugin.reloadConfig();
                            player.sendMessage("Wind Count Particles: " + plugin.getWindCoutParticles());
                        } catch (NumberFormatException e) {
                            player.sendMessage("Valor inválido para count-particles. Por favor, insira um número válido.");
                        }
                        break;
                    case "speed":
                        try {
                            double speed = Double.parseDouble(args[2]);
                            if (speed < 0) {
                                player.sendMessage("O valor de speed não pode ser negativo.");
                                return true;
                            }
                            plugin.getConfig().set("wind-speed", speed);
                            plugin.saveConfig();
                            plugin.reloadConfig();
                            player.sendMessage("Wind Speed: " + plugin.getWindSpeed());
                        } catch (NumberFormatException e) {
                            player.sendMessage("Valor inválido para speed. Por favor, insira um número válido.");
                        }
                        break;
                    default:
                        player.sendMessage("Comando inválido.");
                        break;
                }
                return true;
            }
        } else {
            player.sendMessage("Comando escrito incorretamente. Forma correta: \n" +
                    " /windcharger configure <power/particles/count-particles/speed>");
        }
        return true;
    }

    private boolean isValidParticle(String particle) {
        String[] validParticles = {
                "FALSE", "ELECTRIC_SPARK", "BUBBLE", "CLOUD", "CRIT", "CRIT_MAGIC", "CURRENT_DOWN", "DAMAGE_INDICATOR",
                "DRAGON_BREATH", "DRIP_LAVA", "DRIP_WATER", "ENCHANTMENT_TABLE", "END_ROD", "EXPLOSION_HUGE",
                "EXPLOSION_LARGE", "EXPLOSION_NORMAL", "FIREWORKS_SPARK", "FLAME", "FOOTSTEP", "HEART", "ITEM_CRACK",
                "LAVA", "MOB_APPEARANCE", "NAUTILUS", "NOTE", "PORTAL", "REDSTONE", "SLIME", "SMOKE_LARGE",
                "SMOKE_NORMAL", "SNOW_SHOVEL", "SNOWBALL", "SPELL", "SPELL_INSTANT", "SPELL_MOB", "SPELL_MOB_AMBIENT",
                "SPELL_WITCH", "SPIT", "SQUID_INK", "SWEEP_ATTACK", "TOTEM", "TOWN_AURA", "VILLAGER_ANGRY", "VILLAGER_HAPPY",
                "WATER_BUBBLE", "WATER_DROP", "WATER_SPLASH", "WATER_WAKE"
        };
        for (String validParticle : validParticles) {
        if (particle.equals(validParticle)) {
        return true;}
        }
        return false;
        }

@Override
public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 1) {
        return List.of("configure");
    } else if (args.length == 2) {
        return List.of("power", "particles", "count-particles", "speed");
    } else if (args.length == 3 && args[1].equalsIgnoreCase("particles")) {
        return List.of(
                "FALSE", "ELECTRIC_SPARK", "BUBBLE", "CLOUD", "CRIT", "CRIT_MAGIC", "CURRENT_DOWN", "DAMAGE_INDICATOR",
                "DRAGON_BREATH", "DRIP_LAVA", "DRIP_WATER", "ENCHANTMENT_TABLE", "END_ROD", "EXPLOSION_HUGE",
                "EXPLOSION_LARGE", "EXPLOSION_NORMAL", "FIREWORKS_SPARK", "FLAME", "FOOTSTEP", "HEART", "ITEM_CRACK",
                "LAVA", "MOB_APPEARANCE", "NAUTILUS", "NOTE", "PORTAL", "REDSTONE", "SLIME", "SMOKE_LARGE",
                "SMOKE_NORMAL", "SNOW_SHOVEL", "SNOWBALL", "SPELL", "SPELL_INSTANT", "SPELL_MOB", "SPELL_MOB_AMBIENT",
                "SPELL_WITCH", "SPIT", "SQUID_INK", "SWEEP_ATTACK", "TOTEM", "TOWN_AURA", "VILLAGER_ANGRY",
                "VILLAGER_HAPPY", "WATER_BUBBLE", "WATER_DROP", "WATER_SPLASH", "WATER_WAKE"
        );
    }
    return List.of();
}
}