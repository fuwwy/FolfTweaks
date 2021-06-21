package com.discord.utilities.permissions;

import com.discord.api.role.GuildRole;
import com.discord.models.guild.Guild;
import com.discord.models.user.MeUser;
import com.discord.models.user.User;

import java.util.Collection;
import java.util.Map;

public final class ManageUserContext {
    public static final Companion Companion = new Companion();

    public static final class Companion {
        public final ManageUserContext from(Guild guild, MeUser meUser, User user, Collection<Long> collection, Collection<Long> collection2, Long l, Map<Long, GuildRole> map) {
            return null;
        }
    }

    public final boolean getCanKick() {
        return false;
    }

    public final boolean getCanBan() {
        return false;
    }

}
