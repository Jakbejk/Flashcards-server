package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.enums;

import org.apache.commons.lang3.StringUtils;

public enum RequestSource {

    GOOGLE, FLASHCARD;

    public static RequestSource getFromString(String provider) {
        for (RequestSource value : values()) {
            if (StringUtils.equals(value.toString(), StringUtils.upperCase(provider))) {
                return value;
            }
        }
        return null;
    }
}
