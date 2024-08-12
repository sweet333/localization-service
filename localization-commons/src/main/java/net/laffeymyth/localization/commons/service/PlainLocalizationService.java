package net.laffeymyth.localization.commons.service;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.List;

public class PlainLocalizationService implements LocalizationService<String> {
    private final ComponentLocalizationService componentLocalizationService = new ComponentLocalizationService();
    private final PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

    @Override
    public String getMessage(String messageKey, String language, TagResolver... tagResolvers) {
        return serializer.serialize(
                componentLocalizationService.getMessage(messageKey, language, tagResolvers)
        );
    }

    @Override
    public List<String> getMessageList(String messageListKey, String language, TagResolver... tagResolvers) {
        return componentLocalizationService.getMessageList(messageListKey, language, tagResolvers)
                .stream()
                .map(serializer::serialize)
                .toList();
    }

    @Override
    public String getWord(String key, int number, String language) {
        return serializer.serialize(componentLocalizationService.getWord(key, number, language));
    }
}
