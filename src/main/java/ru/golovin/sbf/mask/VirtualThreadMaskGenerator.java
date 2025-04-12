package ru.golovin.sbf.mask;

import lombok.RequiredArgsConstructor;
import ru.golovin.sbf.Property;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class VirtualThreadMaskGenerator implements MaskGenerator {

    private static final Executor VIRTUAL_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    private final MaskGenerationOptionContainer optionContainer;

    @Override
    public Set<List<MaskType>> generate() {
        List<Integer> keyWordCounts = getAllowedCounts(optionContainer.getKeyWordBlockAmount());
        List<Integer> specialCounts = getAllowedCounts(optionContainer.getSpecialBlockAmount());
        List<Integer> dictionaryCounts = getAllowedCounts(optionContainer.getDictionaryBlockAmount());
        Set<List<MaskType>> allPermutations = ConcurrentHashMap.newKeySet();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int keyWordCount : keyWordCounts) {
            for (int specialCount : specialCounts) {
                for (int dictionaryCount : dictionaryCounts) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        List<MaskType> maskTypes = new ArrayList<>();
                        maskTypes.addAll(Collections.nCopies(keyWordCount, MaskType.KEY_WORD));
                        maskTypes.addAll(Collections.nCopies(specialCount, MaskType.SPECIAL));
                        maskTypes.addAll(Collections.nCopies(dictionaryCount, MaskType.DICTIONARY));
                        Set<List<MaskType>> permutations = new HashSet<>();
                        permuteUniqueIterative(maskTypes, permutations, Comparator.comparing(MaskType::toString));
                        allPermutations.addAll(permutations);
                    }, VIRTUAL_EXECUTOR);
                    futures.add(future);
                }
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return allPermutations;
    }

    private List<Integer> getAllowedCounts(MaskGenerationOption option) {
        int fix = option.getFix();
        if (fix >= 0) {
            return List.of(fix);
        }
        int min = option.getMin();
        int max = option.getMax() < 0 ? Property.getInstance().getDefaultMaxMaskAmount() : option.getMax();
        return IntStream.rangeClosed(min, max)
                .boxed()
                .toList();
    }

    private void permuteUniqueIterative(List<MaskType> list, Set<List<MaskType>> result, Comparator<MaskType> comparator) {
        list.sort(comparator);
        do {
            result.add(new ArrayList<>(list));
        } while (nextPermutation(list, comparator));
    }

    private boolean nextPermutation(List<MaskType> list, Comparator<MaskType> comparator) {
        int n = list.size();
        int i = n - 1;
        while (i > 0 && comparator.compare(list.get(i - 1), list.get(i)) >= 0) {
            i--;
        }
        if (i <= 0) return false;
        int j = n - 1;
        while (comparator.compare(list.get(j), list.get(i - 1)) <= 0) {
            j--;
        }
        Collections.swap(list, i - 1, j);
        Collections.reverse(list.subList(i, n));
        return true;
    }
}
