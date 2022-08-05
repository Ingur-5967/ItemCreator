package ru.solomka.items.config.enums;

import lombok.Getter;

public enum DirectorySource {

    PLAYER("playerdata"),
    MENU("menu"),
    DATA("data"),
    LANG("lang"),
    NONE("");

    @Getter private final String type;

    DirectorySource(String type) {
        this.type = type;
    }

}
