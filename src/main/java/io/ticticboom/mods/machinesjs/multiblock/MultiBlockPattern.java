package io.ticticboom.mods.machinesjs.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiBlockPattern {
    private final List<PlacedMultiBlockPart> parts;

    public MultiBlockPattern(List<PlacedMultiBlockPart> parts) {
        this.parts = parts;
    }

    public boolean matches(Level level, BlockPos pos) {
        for (var rot : Rotation.values()) {
            var transformedParts = new ArrayList<PlacedMultiBlockPart>();
            for (var part : parts) {
                var transformed = new PlacedMultiBlockPart(part.relativePos().rotate(rot), part.predicate());
                transformedParts.add(transformed);
            }
            if (innerMatches(level, pos, transformedParts)) {
                return true;
            }
        }
        return false;
    }

    protected boolean innerMatches(Level level, BlockPos pos, List<PlacedMultiBlockPart> transformedParts) {
        for (PlacedMultiBlockPart part : transformedParts) {
            if (!part.predicate().test(level, pos.offset(part.relativePos()))) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    public interface MultiBlockKeyPredicate {
        boolean test(Level level, BlockPos pos);
    }
    public static class Builder {
        public List<List<String>> layers = new ArrayList<>();
        public Map<Character, MultiBlockKeyPredicate> key = new HashMap<>();
        public Builder layer(String... columns) {
            this.layers.add(List.of(columns));
            return this;
        }

        public Builder key(String chr, MultiBlockKeyPredicate predicate) {
            key.put(chr.charAt(0), predicate);
            return this;
        }

        public MultiBlockPattern build() {
            var flattened = new ArrayList<PlacedMultiBlockPart>();
            BlockPos controller = null;
            for (int i = 0; i < layers.size(); i++) {
                for (int j = 0; j < layers.get(i).size(); j++) {
                    for (int k = 0; k < layers.get(i).get(j).length(); k++) {
                        char l = layers.get(i).get(j).charAt(k);
                        if (key.containsKey(l)) {
                            flattened.add(new PlacedMultiBlockPart(new BlockPos(i, j, k), key.get(l)));
                        }
                        if (l == 'C') {
                            controller = new BlockPos(i, j, k);
                        }
                    }
                }
            }
            if (controller == null) {
                throw new IllegalStateException("No controller found");
            }
            var result = new ArrayList<PlacedMultiBlockPart>();
            for (PlacedMultiBlockPart entry : flattened) {
                result.add(new PlacedMultiBlockPart(entry.relativePos().subtract(controller), entry.predicate()));
            }
            return new MultiBlockPattern(result);
        }
    }
}
