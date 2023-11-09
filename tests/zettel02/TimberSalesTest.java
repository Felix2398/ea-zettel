package zettel02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimberSalesTest {
    int[] prices = {2,6,8,10,0};

    @Test
    public void cutNaive_lengthIs4() {
        Assertions.assertEquals(12, TimberSales.cutNaive(prices, 4));
    }

    @Test
    public void cutNaive_lengthIs3() {
        Assertions.assertEquals(8, TimberSales.cutNaive(prices, 3));
    }

    @Test
    public void cutNaive_lengthIs2() {
        Assertions.assertEquals(6, TimberSales.cutNaive(prices, 2));
    }

    @Test
    public void cutNaive_lengthIs1() {
        Assertions.assertEquals(2, TimberSales.cutNaive(prices, 1));
    }

    @Test
    public void cutNaive_lengthIs5() {
        Assertions.assertEquals(14, TimberSales.cutNaive(prices, 5));
    }

    @Test
    public void cutDynamic_lengthIs4() {
        Assertions.assertEquals(12, TimberSales.cutDynamic(prices, 4));
    }

    @Test
    public void cutDynamic_lengthIs3() {
        Assertions.assertEquals(8, TimberSales.cutDynamic(prices, 3));
    }

    @Test
    public void cutDynamic_lengthIs2() {
        Assertions.assertEquals(6, TimberSales.cutDynamic(prices, 2));
    }

    @Test
    public void cutDynamic_lengthIs1() {
        Assertions.assertEquals(2, TimberSales.cutDynamic(prices, 1));
    }

    @Test
    public void cutDynamic_lengthIs5() {
        Assertions.assertEquals(14, TimberSales.cutDynamic(prices, 5));
    }
}