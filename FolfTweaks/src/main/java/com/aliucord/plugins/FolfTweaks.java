package com.aliucord.plugins;

import android.content.Context;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import com.aliucord.Constants;
import com.aliucord.Utils;
import com.aliucord.entities.Plugin;
import com.aliucord.patcher.PinePatchFn;
import com.aliucord.patcher.PinePrePatchFn;
import com.aliucord.plugins.folftweaks.FolfTweaksSettings;
import com.aliucord.plugins.folftweaks.SettingsPage;
import com.aliucord.utils.ReflectUtils;
import com.aliucord.wrappers.ChannelWrapper;
import com.discord.api.role.GuildRole;
import com.discord.models.domain.ModelUserProfile;
import com.discord.models.domain.emoji.Emoji;
import com.discord.models.guild.Guild;
import com.discord.models.member.GuildMember;
import com.discord.models.user.CoreUser;
import com.discord.models.user.MeUser;
import com.discord.models.user.User;
import com.discord.stores.StoreStream;
import com.discord.utilities.color.ColorCompat;
import com.discord.utilities.icon.IconUtils;
import com.discord.utilities.permissions.ManageUserContext;
import com.discord.widgets.chat.input.emoji.EmojiPickerListener;
import com.discord.widgets.chat.input.emoji.WidgetEmojiPickerSheet;
import com.discord.widgets.settings.WidgetSettings;
import com.discord.widgets.user.Badge;
import com.discord.widgets.user.profile.UserProfileAdminView;
import com.discord.widgets.user.usersheet.WidgetUserSheet;
import com.discord.widgets.user.usersheet.WidgetUserSheetViewModel;
import com.lytefast.flexinput.R$b;
import com.lytefast.flexinput.R$d;
import com.lytefast.flexinput.R$h;

import java.util.*;

@SuppressWarnings("unused")
public class FolfTweaks extends Plugin {
    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{new Manifest.Author("Folf", 96609345684406272L)};
        manifest.description = "Folf tweaks";
        manifest.version = "1.0.2";
        manifest.updateUrl = "https://raw.githubusercontent.com/fuwwy/FolfTweaks/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        uwu();
        patchSettingsTab();
        patchAlwaysAnimate();
        patchAdminWidget();
        patchEmojiPicker();
    }

    private void uwu() {
        patcher.patch(Badge.Companion.class, "getBadgesForUser", new Class<?>[]{User.class, ModelUserProfile.class, int.class, boolean.class, boolean.class, Context.class}, new PinePatchFn(callFrame -> {
            System.out.println();
            if (callFrame.args.length > 0 && callFrame.args[0] instanceof CoreUser) {
                CoreUser user = (CoreUser) callFrame.args[0];
                if (user.getId() == 96609345684406272L) {
                    ((List<Badge>) callFrame.getResult()).add(new Badge(R$d.ic_profile_badge_staff_32dp, "Folf", null, false, null, 28, null));
                }
            }
        }));
    }
    private void patchSettingsTab() {
        patcher.patch(WidgetSettings.class, "onViewBound", new Class<?>[]{View.class}, new PinePatchFn(callFrame -> {
            Context viewContext = ((WidgetSettings) callFrame.thisObject).requireContext();
            CoordinatorLayout view = (CoordinatorLayout) callFrame.args[0];
            LinearLayoutCompat v = (LinearLayoutCompat) ((NestedScrollView)
                    view.getChildAt(1)).getChildAt(0);

            int baseIndex = v.indexOfChild(v.findViewById(Utils.getResId("activity_status", "id")));
            Typeface font = ResourcesCompat.getFont(viewContext, Constants.Fonts.whitney_medium);
            TextView updater = new TextView(viewContext, null, 0, R$h.UiKit_Settings_Item_Icon);
            updater.setText("FolfTweaks");
            updater.setTypeface(font);
            int iconColor = ColorCompat.getThemedColor(viewContext, R$b.colorInteractiveNormal);
            Drawable icon = ContextCompat.getDrawable(viewContext, R$d.ic_activity_status_24dp);
            if (icon != null) {
                Drawable copy = icon.mutate();
                copy.setTint(iconColor);
                updater.setCompoundDrawablesRelativeWithIntrinsicBounds(copy, null, null, null);
            }
            SettingsPage settingsPage = new SettingsPage();
            updater.setOnClickListener(e -> Utils.openPageWithProxy(e.getContext(), settingsPage));
            v.addView(updater, baseIndex + 1);
        }));

    }
    private void patchAlwaysAnimate() {
        patcher.patch(IconUtils.class, "getForGuild", new Class<?>[]{Long.class, String.class, String.class, boolean.class, Integer.class}, new PinePrePatchFn(callFrame -> {
            callFrame.args[3] = ((boolean) callFrame.args[3]) || FolfTweaksSettings.alwaysAnimateGuildIcons;
        }));


       /* patcher.patch(IconUtils.class, "getForUser", new Class<?>[]{Long.class, String.class, Integer.class, boolean.class, Integer.class}, new PinePrePatchFn(callFrame -> {
            callFrame.args[3] = true;
        }));
        patcher.patch(IconUtils.class, "getForGuildMember", new Class<?>[]{GuildMember.class, Integer.class, boolean.class}, new PinePrePatchFn(callFrame -> {
            callFrame.args[3] = true;
        }));
        patcher.patch(IconUtils.class, "setIcon$default", new Class<?>[]{ImageView.class, String.class, int.class, int.class, boolean.class, Function1.class, MGImages.ChangeDetector.class, int.class, Object.class}, new PinePrePatchFn(callFrame -> {
            callFrame.args[4] = true;
        }));

        float _3dp = Utils.dpToPx(25);
        Logger logger = new Logger("SquareAvatars");
        patcher.patch("com.airbnb.lottie.parser.AnimatableValueParser", "I2", new Class<?>[]{ a.class, Context.class, AttributeSet.class }, new PinePatchFn(callFrame -> {
            AttributeSet attrs = (AttributeSet) callFrame.args[2];
            if (attrs == null) return;

            try {
                a builder = (a) callFrame.getResult();
                c roundingParams = builder.r;

                if (roundingParams != null && roundingParams.b) {
                    Context context = (Context) callFrame.args[1];
                    int id = attrs.getAttributeResourceValue(Constants.NAMESPACE_ANDROID, "id", 0);
                    if (id != 0 && contains(context.getResources().getResourceName(id))) {
                        roundingParams.b = false;

                        // round corners
                        float[] radii = roundingParams.c;
                        roundingParams.a = 1;
                        roundingParams.d = ColorCompat.getThemedColor(context, R$b.colorBackgroundPrimary);
                        if (radii == null) {
                            radii = new float[8];
                            roundingParams.c = radii;
                        }
                        Arrays.fill(radii, _3dp);
                    }
                }
            } catch (Throwable e) { logger.error(e); }
        }));*/
    }

    private void patchAdminWidget() {
        patcher.patch(WidgetUserSheet.class, "configureGuildSection", new Class<?>[]{ WidgetUserSheetViewModel.ViewState.Loaded.class }, new PinePrePatchFn(callFrame -> {
            if (!FolfTweaksSettings.alwaysShowGuildProfileAdminWidget) return;
            WidgetUserSheetViewModel.ViewState.Loaded state = (WidgetUserSheetViewModel.ViewState.Loaded) callFrame.args[0];
            if (state.getAdminViewState().isAdminSectionEnabled()) return;

            ChannelWrapper activeChannel = new ChannelWrapper(state.getChannel());
            Guild guild = StoreStream.getGuilds().getGuild(activeChannel.getGuildId());
            User targetUser = state.getUser();
            MeUser meUser = StoreStream.getUsers().getMe();
            GuildMember meMember = StoreStream.getGuilds().getMember(guild.getId(), meUser.getId());
            Map<Long, GuildRole> guildRoles = StoreStream.getGuilds().getRoles().get(guild.getId());

            ManageUserContext userContext = ManageUserContext.Companion.from(guild, meUser, targetUser,
                    meMember.getRoles(), new ArrayList<>(),
                    StoreStream.getPermissions().getGuildPermissions().get(guild.getId()),
                    guildRoles);

            if (userContext.getCanBan() || userContext.getCanKick()) {
                UserProfileAdminView.ViewState newViewState = new UserProfileAdminView.ViewState(false,
                        userContext.getCanKick(),
                        activeChannel.getType() == 3,
                        userContext.getCanBan(),
                        false, false, false, false, false, true,
                        meUser.getId() == targetUser.getId());

                try {
                    ReflectUtils.setField(state, "adminViewState", newViewState, true);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                callFrame.args[0] = state;
            }
        }));
    }

    private void patchEmojiPicker() {
        patcher.patch(WidgetEmojiPickerSheet.class, "onEmojiPicked", new Class<?>[]{Emoji.class}, new PinePrePatchFn(callFrame -> {
            if (FolfTweaksSettings.autoCloseReactPicker) return;
            try {
                EmojiPickerListener emojiPickerListener  = (EmojiPickerListener) ReflectUtils.getField(callFrame.thisObject, "emojiPickerListenerDelegate", true);

                if (emojiPickerListener != null) {
                    emojiPickerListener.onEmojiPicked((Emoji) callFrame.args[0]);
                }

                callFrame.setResult(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void stop(Context context) {
        patcher.unpatchAll();
    }
}
