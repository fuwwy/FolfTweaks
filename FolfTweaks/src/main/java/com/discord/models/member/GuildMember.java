package com.discord.models.member;

import com.discord.api.role.GuildRole;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "InstantiationOfUtilityClass"})
public class GuildMember {
    public static final class Companion {
        public final GuildMember toModel(com.discord.api.guildmember.GuildMember member, Map<Long, GuildRole> roles) { return new GuildMember(); }
    }

    public static final Companion Companion = new Companion();
    public final List<Long> getRoles() {
        return null;
    }
}
