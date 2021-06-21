package com.discord.widgets.user.usersheet;

import com.discord.api.channel.Channel;
import com.discord.api.role.GuildRole;
import com.discord.models.guild.Guild;
import com.discord.models.member.GuildMember;
import com.discord.models.user.MeUser;
import com.discord.models.user.User;
import com.discord.widgets.user.profile.UserProfileAdminView;

import java.util.Map;

@SuppressWarnings("unused")
public final class WidgetUserSheetViewModel {
    public static abstract class ViewState {
        public static final class Loaded extends ViewState {
            public final User getUser() { return null; }
            public final Channel getChannel() { return null; }
            public final UserProfileAdminView.ViewState getAdminViewState() {
                return null;
            }
        }
    }
    public static final class StoreState {
        public final User getUser() { return null; }
        public final MeUser getMe() { return null; }
        public final Guild getGuild() { return null; }
        public final Channel getChannel() { return null; }
        public final Map<Long, GuildMember> getComputedMembers() {
            return null;
        }
        public final Map<Long, GuildRole> getGuildRoles() {
            return null;
        }
        public final Long getPermissions() {
            return null;
        }
    }
}
