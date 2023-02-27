package com.franosch.paul;

import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PancakeFlippingOrderApplier {

    private final PancakeFlipper flipper;

    public PancakeStack apply(PancakeStack pancakeStack, FlippingOrder flippingOrder) {
        if (flippingOrder.getFlippingOperations().size() == 0) {
            return pancakeStack;
        }
        for (Integer flippingOperation : flippingOrder.getFlippingOperations()) {
            flipper.flip(pancakeStack, flippingOperation);
        }
        return pancakeStack;
    }

}
