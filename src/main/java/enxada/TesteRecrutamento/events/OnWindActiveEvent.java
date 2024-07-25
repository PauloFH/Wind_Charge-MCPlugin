package enxada.TesteRecrutamento.events;

import enxada.TesteRecrutamento.Wind_Change;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.util.Vector;
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
    }

    @EventHandler
    public void onWindExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof WindCharge) {
            //se o player tiver no chão e explodir nos pés
            if(((WindCharge) event.getEntity()).getShooter() instanceof Player player){
                if (player.isOnGround() && (player.getLocation().distance( event.getLocation())) < 2 ){
                Vector vector = new Vector(0, plugin.getWindPower()/2, 0);
                player.setVelocity(vector);
                }
            }
            for (Entity entidade : event.getEntity().getNearbyEntities(3, 3, 3)) {
                Vector velocidade = new Vector(entidade.getLocation().getX() - event.getEntity().getLocation().getX(), entidade.getLocation().getY() - event.getLocation().getY(), entidade.getLocation().getZ() - event.getEntity().getLocation().getZ());
                velocidade.multiply(plugin.getWindPower()/2);
                entidade.setVelocity(velocidade);
            }
            String particle = plugin.getWindParticles().toString();
            if (!particle.equalsIgnoreCase("FALSE")) {
                Objects.requireNonNull(event.getLocation().getWorld()).spawnParticle(plugin.getWindParticles(),event.getLocation(), plugin.getWindCoutParticles());
                Objects.requireNonNull(event.getLocation().getWorld()).spawnParticle(Particle.LARGE_SMOKE, event.getLocation(), 10);
            }
            soundplay(event.getLocation());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (event.getEntity() instanceof Player player) {
                double altura = player.getFallDistance();
                double dano = altura * 0.5;
                event.setDamage(dano);
            }
        }
    }
    private  void soundplay(Location location){
        Objects.requireNonNull(location.getWorld()).playSound(location, "entity.generic.explode", 1.0F, 1.0F);
    }
}

