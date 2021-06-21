package com.aliucord.plugins.folftweaks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import com.aliucord.Constants;
import com.aliucord.PluginManager;
import com.aliucord.Utils;
import com.aliucord.api.SettingsAPI;
import com.discord.views.CheckedSetting;
import com.lytefast.flexinput.R$h;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.aliucord.plugins.folftweaks.FolfTweaksSettings.*;

public class SettingsPage extends com.aliucord.fragments.SettingsPage {

    public SettingsPage() {
        SettingsAPI sets = Objects.requireNonNull(PluginManager.plugins.get("FolfTweaks")).sets;
        try {
            for (Field declaredField : FolfTweaksSettings.class.getDeclaredFields()) {
                if (declaredField.getType() == boolean.class) {
                    declaredField.set(null, sets.getBool(declaredField.getName(), (boolean) declaredField.get(null)));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setActionBarTitle("FolfTweaks Settings");
        setActionBarSubtitle("Aliucord");
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onViewBound(View view) {
        super.onViewBound(view);

        Context context = view.getContext();
        LinearLayout layout = (LinearLayout) ((NestedScrollView) ((CoordinatorLayout) view).getChildAt(1)).getChildAt(0);
        int padding = Utils.getDefaultPadding();

        TextView header = new TextView(context, null, 0, R$h.UiKit_Settings_Item_Header);
        header.setText("UI");
        header.setTypeface(ResourcesCompat.getFont(context, Constants.Fonts.whitney_semibold));
        layout.addView(header);

        SettingsAPI sets = Objects.requireNonNull(PluginManager.plugins.get("FolfTweaks")).sets;
        CheckedSetting alwaysAnimateGuildIconsSetting = Utils.createCheckedSetting(context, CheckedSetting.ViewType.SWITCH, "Always animate guild icons", null);
        alwaysAnimateGuildIconsSetting.setChecked(alwaysAnimateGuildIcons);
        alwaysAnimateGuildIconsSetting.setOnCheckedListener(c -> {
            sets.setBool("alwaysAnimateGuildIcons", c);
            alwaysAnimateGuildIcons = c;
        });
        layout.addView(alwaysAnimateGuildIconsSetting);

        CheckedSetting alwaysShowGuildProfileAdminWidgetSetting = Utils.createCheckedSetting(context, CheckedSetting.ViewType.SWITCH, "Always show guild profile admin widget", "Always show the ban and kick buttons, even for non-members.");
        alwaysShowGuildProfileAdminWidgetSetting.setChecked(alwaysShowGuildProfileAdminWidget);
        alwaysShowGuildProfileAdminWidgetSetting.setOnCheckedListener(c -> {
            sets.setBool("alwaysShowGuildProfileAdminWidget", c);
            alwaysShowGuildProfileAdminWidget = c;
        });
        layout.addView(alwaysShowGuildProfileAdminWidgetSetting);

        header = new TextView(context, null, 0, R$h.UiKit_Settings_Item_Header);
        header.setText("Behavior");
        header.setTypeface(ResourcesCompat.getFont(context, Constants.Fonts.whitney_semibold));
        layout.addView(header);

        CheckedSetting autoCloseReactPickerSetting = Utils.createCheckedSetting(context, CheckedSetting.ViewType.SWITCH, "Auto-close emoji picker when adding a reaction", null);
        autoCloseReactPickerSetting.setChecked(autoCloseReactPicker);
        autoCloseReactPickerSetting.setOnCheckedListener(c -> {
            sets.setBool("autoCloseReactPicker", c);
            autoCloseReactPicker = c;
        });
        layout.addView(autoCloseReactPickerSetting);

    }

}
