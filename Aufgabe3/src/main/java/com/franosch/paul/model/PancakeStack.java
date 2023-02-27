package com.franosch.paul.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PancakeStack implements Cloneable {
    private PancakeStackData normalizedPancakes; // bottom to top
    private PancakeStackData pancakes; // bottom to top, using actual pancake sizes

    public PancakeStack(Integer[] pancakes) {
        this.normalizedPancakes = new PancakeStackData(pancakes);
        this.pancakes = new PancakeStackData(pancakes);
    }

    public PancakeStack(Integer[] normalizedPancakes, Integer[] pancakes) {
        this.normalizedPancakes = new PancakeStackData(normalizedPancakes);
        this.pancakes = new PancakeStackData(pancakes);
    }

    public boolean isSolved() {
        int last = - 1;
        for (final Integer pancake : this.normalizedPancakes.getPancakes()) {
            if (last == - 1) {
                last = pancake;
                continue;
            }
            if (last <= pancake) {
                return false;
            }
            last = pancake;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PancakeStack{" +
                "normalizedPancakes=" + normalizedPancakes +
                ", pancakes=" + pancakes +
                '}';
    }

    @Override
    public PancakeStack clone() {
        return new PancakeStack(this.normalizedPancakes.clone(), this.pancakes.clone());
    }
}
