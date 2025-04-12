package ru.golovin.sbf.mask;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpecialOption {
    ONE("dictionary/spec-1.lst"),
    TWO("dictionary/spec-2.lst"),
    THREE("dictionary/spec-3.lst"),
    FOUR("dictionary/spec-4.lst"),
    FIVE("dictionary/spec-5.lst");

    private final String path;
}
