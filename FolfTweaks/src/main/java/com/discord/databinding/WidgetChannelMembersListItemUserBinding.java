package com.discord.databinding;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.discord.utilities.view.text.SimpleDraweeSpanTextView;
import com.discord.views.StatusView;
import com.discord.views.UsernameView;
import com.facebook.drawee.view.SimpleDraweeView;

public class WidgetChannelMembersListItemUserBinding {
    public final SimpleDraweeView b;

    public WidgetChannelMembersListItemUserBinding(@NonNull ConstraintLayout constraintLayout, @NonNull SimpleDraweeView simpleDraweeView, @NonNull ImageView imageView, @NonNull SimpleDraweeSpanTextView simpleDraweeSpanTextView, @NonNull ImageView imageView2, @NonNull UsernameView usernameView, @NonNull StatusView statusView, @NonNull ImageView imageView3) {
        this.b = simpleDraweeView;
    }
}
