package com.discord.widgets.chat.input.emoji;

import com.discord.models.domain.emoji.Emoji;

public interface EmojiPickerListener {
    void onEmojiPicked(Emoji emoji);
}
