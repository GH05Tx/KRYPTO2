import Library.Matma;
import java.math.BigInteger;

public class JacobiSymbol {
    Matma EIGHT = new Matma("8");
    Matma SEVEN = new Matma("7");
    Matma FOUR = new Matma("4");
    Matma THREE = new Matma("3");
    Matma TWO = new Matma("2");

    public int computeJacobiSymbol(Matma initial_a, Matma n) {
        // Step 1: a = a mod n
        Matma a = initial_a.mod(n);
        // Step 2: if a = 1 or n = 1 return 1
        if (a.equals(Matma.ONE) || n.equals(Matma.ONE)) {
            return 1;
        }
        // Step 3: if a = 0 return 0
        if (a.equals(Matma.ZERO)) {
            return 0;
        }
        // Step 4: define e and a_1 such that a = 2^e * a_1 where a_1 is odd
        int e = 0;
        Matma a_1 = a;
        while (a_1.remainder(TWO).equals(Matma.ZERO)) {
            e++;
            a_1 = a_1.divide(TWO);
        }
        // Step 5: if e is even, then s = 1;
        //          else if n mod 8 = 1 or n mod 8 = 7, then s = 1
        //          else if n mod 8 = 3 or n mod 8 = 5, then s = -1
        int s;
        if (e % 2 == 0) {
            s = 1;
        } else {
            Matma n_mod_eight = n.mod(EIGHT);
            if (n_mod_eight.equals(Matma.ONE) || n_mod_eight.equals(SEVEN)) {
                s = 1;
            } else { // n_mod_eight.equals(THREE) || n_mod_eight.equals(FIVE)
                s = -1;
            }
        }
        // Step 6: if n mod 4 = 3 and a_1 mod 4 = 3, then s = -s
        if (n.mod(FOUR).equals(THREE) && a_1.mod(FOUR).equals(THREE)) {
            s = -s;
        }
        // Step 7: n_1 = n mod a_1
        Matma n_1 = n.mod(a_1);
        // Step 8: return s * JacobiSymbol(n_1, a_1)
        return s * computeJacobiSymbol(n_1, a_1);
    }
}
