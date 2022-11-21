package rip.alpha.hcf.timer.impl;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketCooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rip.alpha.hcf.HCF;
import rip.alpha.hcf.profile.TeamProfile;
import rip.alpha.hcf.profile.settings.Setting;
import rip.alpha.hcf.timer.type.TimestampTimer;

import java.util.concurrent.TimeUnit;

public class EnderpearlTimer extends TimestampTimer {

    public EnderpearlTimer() {
        this(HCF.getInstance().getConfiguration().getEnderpearlTimer());
    }

    public EnderpearlTimer(int seconds) {
        this(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
    }

    public EnderpearlTimer(long time) {
        super(5, false, time);
    }

    @Override
    public void onApply(TeamProfile profile) {
        Player player = profile.toPlayer();
        if (player == null) {
            return;
        }
        TeamProfile teamProfile = HCF.getInstance().getProfileHandler().getProfile(player);
        if (!teamProfile.getSetting(Setting.LUNAR_TIMERS)) {
            return;
        }
        LCPacketCooldown packetCooldown = new LCPacketCooldown("Enderpearl", this.getRemaining(), Material.ENDER_PEARL.getId());
        LunarClientAPI.getInstance().sendPacket(player, packetCooldown);
    }

    @Override
    public void onExtend(TeamProfile profile) {
        Player player = profile.toPlayer();
        if (player == null) {
            return;
        }
        TeamProfile teamProfile = HCF.getInstance().getProfileHandler().getProfile(player);
        if (!teamProfile.getSetting(Setting.LUNAR_TIMERS)) {
            return;
        }
        LCPacketCooldown packetCooldown = new LCPacketCooldown("Enderpearl", this.getRemaining(), Material.ENDER_PEARL.getId());
        LunarClientAPI.getInstance().sendPacket(player, packetCooldown);
    }

    @Override
    public void onRemove(TeamProfile profile) {
        Player player = profile.toPlayer();
        if (player == null) {
            return;
        }
        LCPacketCooldown packetCooldown = new LCPacketCooldown("Enderpearl", 0L, Material.ENDER_PEARL.getId());
        LunarClientAPI.getInstance().sendPacket(player, packetCooldown);
    }

    @Override
    public void onExpire(TeamProfile profile) {

    }

    @Override
    public void onTimerUpdate(TeamProfile profile) {

    }
}
