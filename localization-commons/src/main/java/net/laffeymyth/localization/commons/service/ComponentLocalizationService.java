package net.laffeymyth.localization.commons.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class ComponentLocalizationService implements LocalizationService<TextComponent> {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    @Getter
    private final Map<String, LocalizationMessageSource> languageMap = new ConcurrentHashMap<>();

    @Override
    public TextComponent getMessage(String messageKey, String language, TagResolver... tagResolvers) {
        LocalizationMessageSource localizationMessageSource = languageMap.get(language);

        if (localizationMessageSource == null) {
            throw new RuntimeException("Источник сообщений язка" + language + " не найден!");
        }

        return Component.empty().append(miniMessage.deserialize(localizationMessageSource.getMessage(messageKey), tagResolvers));
    }

    @Override
    public List<TextComponent> getMessageList(String messageListKey, String language, TagResolver... tagResolvers) {
        LocalizationMessageSource localizationMessageSource = languageMap.get(language);

        if (localizationMessageSource == null) {
            throw new RuntimeException("Источник сообщений язка" + language + " не найден!");
        }

        localizationMessageSource.getMessageList(messageListKey);
        return localizationMessageSource.getMessageList(messageListKey)
                .stream().map(s -> Component.empty().append(miniMessage.deserialize(s, tagResolvers)))
                .toList();
    }

    @Override
    public TextComponent getWord(String key, int number, String language) {
        if (number % 100 > 10 && number % 100 < 15) {
            return getMessageList(key, language).get(3);
        } else {
            return switch (number % 10) {
                case 1 -> getMessageList(key, language).get(1);
                case 2, 3, 4 -> getMessageList(key, language).get(2);
                default -> getMessageList(key, language).get(3);
            };
        }
    }
}
