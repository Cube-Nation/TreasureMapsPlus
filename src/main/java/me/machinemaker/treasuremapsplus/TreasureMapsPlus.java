/*
 * GNU General Public License v3
 *
 * TreasureMapsPlus, a plugin to alter treasure maps
 *
 * Copyright (C) 2023 Machine-Maker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.machinemaker.treasuremapsplus;

import me.machinemaker.treasuremapsplus.listener.ServerLoad;
import org.bukkit.plugin.java.JavaPlugin;

public final class TreasureMapsPlus extends JavaPlugin {

    private final boolean replaceMonuments;
    private final boolean replaceMansions;
    private final boolean replaceTrialChambers;

    public TreasureMapsPlus() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.replaceMonuments = this.getConfig().getBoolean("replace.villagers.monument", false);
        this.replaceMansions = this.getConfig().getBoolean("replace.villagers.mansion", false);
        this.replaceTrialChambers = this.getConfig().getBoolean("replace.villagers.trial_chamber", false);
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ServerLoad(this), this);

    }

    public boolean shouldReplaceMonuments() {
        return this.replaceMonuments;
    }

    public boolean shouldReplaceMansions() {
        return this.replaceMansions;
    }

    public boolean shouldReplaceTrialChambers() {
        return this.replaceTrialChambers;
    }

}
