import java.util.Arrays;

import static utils.Matrices.*;


public class Golay {
    public static int[] encode(int[] input) {
        return append(input, multiply(input, A));
    }

    public static int[] decode(int[] input) {
        int[] s1 = multiply(input, transpose(G));
        int[] s2 = multiply(input, transpose(H));

        int w1 = weight(s1);
        int w2 = weight(s2);

        int[] e0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        if (w1 == 0 && w2 == 0) {
            return input;
        }

        if (w1 <= 3 && w2 >= 5) {
            return add(input, append(s1, e0));
        }

        if (w2 <= 3 && w1 >= 5) {
            return add(input, append(e0, s2));
        }

        if (w1 >= 5 && w2 >= 5) {
            int countLessThan2 = 0;
            int countMoreThan4 = 0;
            int[] ej = null;

            int[] e = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            for (int i = 1; i <= 12; i++) {
                int[] si = multiply(add(input, append(e, e0)), transpose(H));
                int wi = weight(si);

                if (wi <= 2) {
                    countLessThan2++;
                    ej = Arrays.copyOf(e, e.length);
                } else if (wi >= 4) {
                    countMoreThan4++;
                }

                e[i - 1] = 0;
                if (i < 12) {
                    e[i] = 1;
                }
            }

            if (countLessThan2 == 1 && countMoreThan4 == 11) {
                int[] x = add(input, append(ej, e0));

                s1 = multiply(x, transpose(G));
                s2 = multiply(x, transpose(H));

                w1 = weight(s1);
                w2 = weight(s2);

                if (w1 <= 3 && w2 >= 5) {
                    return add(x, append(s1, e0));
                }

                if (w2 <= 3 && w1 >= 5) {
                    return add(x, append(e0, s2));
                }
            }

            countLessThan2 = 0;
            countMoreThan4 = 0;
            ej = null;

            e = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            for (int i = 1; i <= 12; i++) {
                int[] si = multiply(add(input, append(e0, e)), transpose(G));
                int wi = weight(si);

                if (wi <= 2) {
                    countLessThan2++;
                    ej = Arrays.copyOf(e, e.length);
                } else if (wi >= 4) {
                    countMoreThan4++;
                }

                e[i - 1] = 0;
                if (i < 12) {
                    e[i] = 1;
                }
            }

            if (countLessThan2 == 1 && countMoreThan4 == 11) {
                int[] x = add(input, append(e0, ej));

                s1 = multiply(x, transpose(G));
                s2 = multiply(x, transpose(H));

                w1 = weight(s1);
                w2 = weight(s2);

                if (w1 <= 3 && w2 >= 5) {
                    return add(input, append(s1, ej));
                }

                if (w2 <= 3 && w1 >= 5) {
                    return add(input, append(ej, s2));
                }
            }
        }

        throw new IllegalArgumentException("Unable to decode");
    }
}
