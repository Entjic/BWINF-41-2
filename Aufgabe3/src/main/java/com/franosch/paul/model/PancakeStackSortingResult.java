package com.franosch.paul.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class PancakeStackSortingResult {
    private final FlippingOrder flippingOrder;
    private final PancakeStack solved;
    private final PancakeStack previous;

}
