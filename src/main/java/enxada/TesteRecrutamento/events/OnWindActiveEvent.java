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

            if (entity instanceof WindCharge windCharge) {
                Entity shooter = (Entity) windCharge.getShooter();
                if (shooter instanceof Player player) {
                    double force = plugin.getWindPower();
                    List<Entity> nearbyEntities = (List<Entity>) Objects.requireNonNull(explodeLocation.getWorld()).getNearbyEntities(explodeLocation, (force),force, force);
                    for (Entity nearbyEntity : nearbyEntities) {
                        if (nearbyEntity instanceof LivingEntity livingEntity) {
                            if (!nearbyEntity.equals(player)) {
                                applyKnockback(explodeLocation, livingEntity, force);
                            }
                        }
                    }

                    // Verificar se a explosão ocorre nos pés do jogador
                    if (explodeLocation.distance(player.getLocation()) < 1) {
                        player.setVelocity(new Vector(0, force, 0));
                    } else {
                        // Calcular dano de queda
                        Location initialLocation = player.getLocation();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            double fallDistance = initialLocation.distance(player.getLocation());
                            player.damage(fallDistance / 2.0);
                        }, 1L);
                    }

                    // Exibir partículas, se configurado
                    String particle = plugin.getWindParticles().toString();
                    if (!particle.equalsIgnoreCase("FALSE")) {
                        Objects.requireNonNull(explodeLocation.getWorld()).spawnParticle(plugin.getWindParticles(), explodeLocation, plugin.getWindCoutParticles());
                        Objects.requireNonNull(explodeLocation.getWorld()).spawnParticle(Particle.EXPLOSION, explodeLocation, plugin.getWindCoutParticles());
                    }
                }
            }
        }
    }

    private void applyKnockback(Location explosionLocation, Entity entity, double power) {
        Location entityLoc = entity.getLocation();
        double distance = entityLoc.distanceSquared(explosionLocation) / power;
        double dx = entityLoc.getX() - explosionLocation.getX();
        double dy = entityLoc.getY() - explosionLocation.getY();
        double dz = entityLoc.getZ() - explosionLocation.getZ();
        double dq = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (dq != 0.0D) {
            dx /= dq;
            dy /= dq;
            dz /= dq;
            double face = (1.0D - distance) * power;
            Vector vec = new Vector(dx * face, dy * face, dz * face);
            entity.setVelocity(entity.getVelocity().add(vec).multiply(power));
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
