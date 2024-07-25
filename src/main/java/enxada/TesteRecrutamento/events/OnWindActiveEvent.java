package enxada.TesteRecrutamento.events;

import enxada.TesteRecrutamento.Wind_Change;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;


public class OnWindActiveEvent  implements Listener {
    private  final Wind_Change plugin;

    public OnWindActiveEvent(Wind_Change plugin) {
        this.plugin = plugin;

    }
    @EventHandler
    public void onWindActiveEvent(ProjectileLaunchEvent event){
        if((!(event.getEntity().getShooter() instanceof Player shooter)) ||(event.getEntityType() != EntityType.WIND_CHARGE)) return;
        double velocity = plugin.getWindSpeed();
        WindCharge windCharge = (WindCharge) event.getEntity();
        windCharge.setAcceleration(shooter.getLocation().getDirection().multiply(velocity));
        shooter.sendMessage("Wind ativo com :" +velocity + " de velocidade");
    }
    @EventHandler
    public void onWindExplodeEvent(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.WIND_CHARGE) {
            event.setCancelled(true);
            Location explodeLocation = event.getLocation();
            Entity entity = event.getEntity();


                    // Exibir partículas, se configurado
                    String particle = plugin.getWindParticles().toString();
                    if (!particle.equalsIgnoreCase("FALSE")) {
                        Objects.requireNonNull(explodeLocation.getWorld()).spawnParticle(plugin.getWindParticles(), explodeLocation, plugin.getWindCoutParticles());
                        Objects.requireNonNull(explodeLocation.getWorld()).spawnParticle(Particle.EXPLOSION, explodeLocation, plugin.getWindCoutParticles());
                    }
        }
    }
}

//    @EventHandler
//    public void onWindHitEvent(ProjectileHitEvent event){
//        if(event.getEntityType() == EntityType.WIND_CHARGE){
//            //Objects.requireNonNull(event.getEntity().getLocation().getWorld()).createExplosion(event.getEntity().getLocation(), (float) plugin.getWindPower());
//           // broadcastMessage("Wind atingiu algo");
//        }
//    }
