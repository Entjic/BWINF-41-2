package com.franosch.paul.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class FlippingOrderPancakeStackTuple {
    private final FlippingOrder flippingOrder;
    private final PancakeStack solved;
}
