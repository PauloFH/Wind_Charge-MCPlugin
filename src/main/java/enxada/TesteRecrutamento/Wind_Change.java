package enxada.TesteRecrutamento;

import enxada.TesteRecrutamento.command.WindChargerTestCommand;
import enxada.TesteRecrutamento.events.OnWindActiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Wind_Change extends JavaPlugin {
   private double windPower;
    private String windParticles;
    private double windSpeed;
    private int windCoutParticles;

    @Override
    public void onEnable() {
        //puxar e carregar as configurações
        configurations();

        //registra o evento e comando
        Objects.requireNonNull(getCommand("windcharger")).setExecutor(new WindChargerTestCommand(this));
        Objects.requireNonNull(getCommand("windcharger")).setTabCompleter(new WindChargerTestCommand(this));
        Bukkit.getPluginManager().registerEvents(new OnWindActiveEvent(this), this);
        getLogger().info(this.toString() + ":: Plugin habilitado!");

    }

    @Override
    public void onDisable() {
        getLogger().info(this.toString()+ ":: plugin foi desabilitado com sucesso!!");
    }
    public void configurations(){
        saveConfig();
        FileConfiguration config = getConfig();
        windPower = config.getDouble("wind-power", 0.5);
        windParticles = config.getString("wind-particles", "false");
        windCoutParticles = config.getInt("wind-count-particles", 10);
        windSpeed = config.getDouble("wind-speed", 0.5);
        config.addDefault("wind-power", windPower);
        config.addDefault("wind-particles", windParticles);
        config.addDefault("wind-speed", windSpeed);
        config.addDefault("wind-count-particles", windCoutParticles);
        config.options().copyDefaults(true);
        saveConfig();
    }

    public float getWindPower() {
        return (float)(this.getConfig().getDouble("wind-power", 0.5));
    }
    public Particle getWindParticles(){
        return Particle.valueOf(this.getConfig().getString("wind-particles"));
    }
    public int getWindCoutParticles(){
        return this.getConfig().getInt("wind-count-particles");
    }
    public double getWindSpeed(){
        return this.getConfig().getDouble("wind-speed");
    }
    public void setWindPower(double windPower){
        this.getConfig().set("wind-power", windPower);
        saveConfig();
        reloadConfig();
    }
    public void setWindParticles(boolean windParticles){
        this.getConfig().set("wind-particles", windParticles);
        saveConfig();
        reloadConfig();
    }
    public void setWindCoutParticles(int windCoutParticles){
        this.getConfig().set("wind-count-particles", windCoutParticles);
        saveConfig();
        reloadConfig();
    }
    public void setWindSpeed(double windSpeed){
        this.getConfig().set("wind-speed", windSpeed);
        saveConfig();
        reloadConfig();
    }
}
