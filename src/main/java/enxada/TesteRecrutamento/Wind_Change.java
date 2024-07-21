package enxada.TesteRecrutamento;

import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wind_Change extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info(Color.YELLOW +this.toString()+Color.GREEN + ":: Plugin habilitado!");

    }

    @Override
    public void onDisable() {
        getLogger().info(Color.YELLOW +this.toString()+Color.GREEN + ":: plugin foi desabilitado com sucesso!!");
    }
}
