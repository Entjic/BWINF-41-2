package com.franosch.paul.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public class PancakeStackData implements Cloneable {
    private Integer[] pancakes; // bottom to top

    @Override
    public String toString() {
        return "PancakeStackData{" +
                "pancakes=" + Arrays.toString(pancakes) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof PancakeStackData that)) return false;
        return Arrays.equals(getPancakes(), that.getPancakes());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getPancakes());
    }

    @Override
    protected PancakeStackData clone() {
        return new PancakeStackData(Arrays.copyOf(this.getPancakes(), this.getPancakes().length));
    }
}
