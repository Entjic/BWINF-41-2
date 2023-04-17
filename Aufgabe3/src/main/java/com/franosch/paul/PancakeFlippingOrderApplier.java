package com.franosch.paul;

import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class PancakeFlippingOrderApplier {

    private final PancakeFlipper flipper;

    public PancakeStack apply(PancakeStack pancakeStack, FlippingOrder flippingOrder) {
        if (flippingOrder.getFlippingOperations().size() == 0) {
            return pancakeStack;
        }
        for (Byte flippingOperation : flippingOrder.getFlippingOperations()) {
            flipper.flip(pancakeStack, flippingOperation);
        }
        return pancakeStack;
    }

    public PancakeStack applyAndPrintDebug(PancakeStack pancakeStack, FlippingOrder flippingOrder) {
        if (flippingOrder.getFlippingOperations().size() == 0) {
            System.out.println(Arrays.toString(pancakeStack.getPancakes().getPancakes()) + " ist bereits gelöst.");
            return pancakeStack;
        }
        for (final Byte flippingOperation : flippingOrder.getFlippingOperations()) {
            final PancakeStack copy = pancakeStack.clone();
            flipper.flip(pancakeStack, flippingOperation);
            System.out.println("Wende-und-Essoperation an Index " + flippingOperation + ": " +
                    Arrays.toString(copy.getPancakes().getPancakes()) + " -> " +
                    Arrays.toString(pancakeStack.getPancakes().getPancakes()));
        }
        return pancakeStack;
    }

}
