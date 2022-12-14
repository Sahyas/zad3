package pl.lodz.p.it.isrp;

import java.util.Arrays;

/**
 * Uwaga: Klasa zawiera błędy, które trzeba zidentyfikować i usunąć z
 * zastosowaniem trybu debug (nadzorowane wykonanie z wykorzystanie debuggera), tak by 
 * tablica była inicjowana wartościami losowymi a następnie sortowana.
 */
public class SortTabNumbers {

    private final long tab[];

    public SortTabNumbers(final int max) {
        tab = new long[max];
        int i;
        for (i = 0; i < max;) {
            tab[i] = (long) (Math.random() * Long.MAX_VALUE);
            i++;
        }
    }

    /*
     * W metodzie należy zminimalizować liczbę wykonywanych porównań
     * opdpowiednio ustalając wartości początkową dla zmienej j.
     */
    public void sort() {
        for (int i = 0; i < tab.length; i++) {
            for (int j = i + 1; j < tab.length; j++) {
                if (tab[i] > tab[j]) {
                    swap(i, j);
                }
            }
        }
    }

    private void swap(final int i, final int j) {
        long tmp = tab[i];
        tab[i] = tab[j];
        tab[j] = tmp;
    }

    public boolean checkMinOrderSort() {
        for (int k = 0; k < tab.length - 1; k++) {
            if (tab[k] > tab[k + 1]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "tab=" + Arrays.toString(tab);
    }
}
