package enxada.TesteRecrutamento.command;

import enxada.TesteRecrutamento.Wind_Charge;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WindChargerTestCommand implements CommandExecutor, TabCompleter {
    private final Wind_Charge plugin;

    public WindChargerTestCommand(Wind_Charge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        if (args.length == 0) {
            // Give player a Wind Charge item
            ItemStack item = new ItemStack(Material.WIND_CHARGE, 64);
            player.getInventory().addItem(item);
            player.sendMessage("Você recebeu 64 Wind Charges.");
            return true;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("configure")) {
            String setting = args[1].toLowerCase();
            String value = args[2];

            switch (setting) {
                case "power":
                    try {
                        double power = Double.parseDouble(value);
                        if (power < 0) {
                            player.sendMessage("O valor de power não pode ser negativo.");
                            return true;
                        }
                        plugin.getConfig().set("wind-power", power);
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        player.sendMessage("Wind Power alterado para " + plugin.getWindPower());
                    } catch (NumberFormatException e) {
                        player.sendMessage("Valor inválido para power. Insira um número válido.");
                    }
                    break;

                case "particles":
                    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                        plugin.getConfig().set("wind-particles", Boolean.parseBoolean(value));
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        player.sendMessage("Exibição de partículas alterada para " + plugin.getWindParticles());
                    } else {
                        player.sendMessage("Valor inválido para particles. Use 'true' ou 'false'.");
                    }
                    break;

                case "count-particles":
                    try {
                        int count = Integer.parseInt(value);
                        if (count < 0) {
                            player.sendMessage("O valor de count-particles não pode ser negativo.");
                            return true;
                        }
                        plugin.getConfig().set("wind-count-particles", count);
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        player.sendMessage("Quantidade de partículas alterada para " + plugin.getWindCoutParticles());
                    } catch (NumberFormatException e) {
                        player.sendMessage("Valor inválido para count-particles. Insira um número válido.");
                    }
                    break;

                case "speed":
                    try {
                        double speed = Double.parseDouble(value);
                        if (speed < 0) {
                            player.sendMessage("O valor de speed não pode ser negativo.");
                            return true;
                        }
                        plugin.getConfig().set("wind-speed", speed);
                        plugin.saveConfig();
                        plugin.reloadConfig();
                        player.sendMessage("Velocidade alterada para " + plugin.getWindSpeed());
                    } catch (NumberFormatException e) {
                        player.sendMessage("Valor inválido para speed. Insira um número válido.");
                    }
                    break;

                default:
                    player.sendMessage("Configuração desconhecida. Use: power, particles, count-particles ou speed.");
                    break;
            }
            return true;
        } else {
            player.sendMessage("Uso incorreto. Formato correto: /windcharger configure <power/particles/count-particles/speed> <valor>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return List.of();
        }

        if (args.length == 1) {
            return List.of("configure");
        } else if (args.length == 2) {
            return Arrays.asList("power", "particles", "count-particles", "speed");
        } else if (args.length == 3) {
            String subCommand = args[1].toLowerCase();
            switch (subCommand) {
                case "particles":
                    return Arrays.asList("true", "false");
                case "power":
                case "count-particles":
                case "speed":
                    return Arrays.asList("0", "1", "10", "100", "1000"); // Values can be adjusted
                default:
                    return List.of();
            }
        }
        return List.of();
    }
}