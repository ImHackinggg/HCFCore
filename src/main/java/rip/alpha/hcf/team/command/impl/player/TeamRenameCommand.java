package rip.alpha.hcf.team.command.impl.player;

import net.mcscrims.command.annotation.Command;
import net.mcscrims.command.annotation.Param;
import net.mcscrims.libraries.util.CC;
import org.bukkit.entity.Player;
import rip.alpha.hcf.HCF;
import rip.alpha.hcf.team.Team;
import rip.alpha.hcf.team.TeamHandler;
import rip.alpha.hcf.team.command.TeamCommandConstants;
import rip.alpha.hcf.team.impl.PlayerTeam;

import java.util.concurrent.TimeUnit;

public class TeamRenameCommand {
    @Command(names = {"team rename", "t rename", "f rename", "faction rename"}, async = true)
    public static void teamRenameCommand(Player player, @Param(name = "name") String name) {
        if (name.length() > 16) {
            player.sendMessage(CC.RED + "Your team name cannot be above 16 characters.");
            return;
        }

        if (name.length() < 3) {
            player.sendMessage(CC.RED + "Your team name cannot be under 3 characters.");
            return;
        }

        if (TeamCommandConstants.ALPHANUMERIC_PATTERN.matcher(name).find()) {
            player.sendMessage(CC.RED + "Team names must be alphanumeric.");
            return;
        }

        TeamHandler teamHandler = HCF.getInstance().getTeamHandler();
        if (teamHandler.isBlacklistedTeamName(name)) {
            player.sendMessage(CC.RED + name + " is a blacklisted team name!");
            return;
        }

        PlayerTeam playerTeam = teamHandler.getPlayerTeamByPlayer(player);

        if (playerTeam == null) {
            player.sendMessage(CC.RED + "You are not in a team, use /team create <name> to create a team");
            return;
        }

        Team team = teamHandler.getTeamByName(name);

        if (team != null) {
            player.sendMessage(CC.RED + "There is already a team with this name ");
            return;
        }

        PlayerTeam.TeamMember member = playerTeam.getMember(player.getUniqueId());
        if (!member.isHigherOrEqual(PlayerTeam.TeamMember.TEAM_COLEADER)) {
            player.sendMessage(CC.RED + "You are not high enough in the team hierarchy to focus for the team");
            return;
        }

        if (playerTeam.getRenameTime() - System.currentTimeMillis() > 0) {
            player.sendMessage(CC.RED + "You are currently on rename cooldown.");
            return;
        }

        playerTeam.setName(name);
        playerTeam.setRenameTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30));
        playerTeam.setSave(true);
        playerTeam.broadcast("&a" + player.getName() + " &ehas renamed the team to &a" + name);
    }
}
