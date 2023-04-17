package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;
import org.junit.jupiter.api.Test;

public class PancakeSorterTest {
    @Test
    public void testPancakeSorter(){
        final PancakeFlipper pancakeFlipper = new PancakeFlipper();
        final PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeStackSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);

        PancakeStackSortingResult sort = pancakeStackSorter.sort(new PancakeStack(new Byte[]{5,4,2,3,1}));

        System.out.println(sort.getPrevious());
        System.out.println(sort.getSolved());
        System.out.println(sort.getFlippingOrder());

    }
}
