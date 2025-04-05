package ru.golovin.sbf.mask;

import java.util.Set;

public record Mask(MaskType type, Set<MaskOption> options) {
}
