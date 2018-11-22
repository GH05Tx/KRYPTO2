import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;


///maina jeszcze nie ruszyłem - uzupełnie go adekwatnie do funkcji stąd
///dodam jeszcze jedno okno do wyświetlania klucza publicznego
///nie rozkminiłem nadal czy konwertowanie wszystkiego z tab byte na jakieś
///łańcuchy nie byłoby dobrym posunięciem
///chyba że ogarniemy jak wybierać który z 4 pierwiastków jest poprawny 

public class Rabin {

    BigInteger x1[], x2[], x3[], x4[];
    BigInteger tab[]=new BigInteger[2];

    ///losowanie klucza prywatnego P,Q=3(mod 4)
    public BigInteger getP(){
        /*Random rand = new Random();
        int p=rand.nextInt(700)*4+3;
        return p;*/
        BigInteger FOUR;
        FOUR = BigInteger.valueOf(4);
        BigInteger THREE;
        THREE = BigInteger.valueOf(3);
        BigInteger P;
        Random rnd = new Random();
        do {
            P = BigInteger.probablePrime(256, rnd);
            //System.out.println("Heja");
        } while (!P.mod(FOUR).equals(THREE));
        return P;

    }

    public boolean returnPrime(BigInteger number) {
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) {//start from 3, 5, etc. the odd number, and look for a divisor if any
            System.out.println("I: " + i);
            System.out.println("P: " + number);
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }

    public boolean Miller_Rabin(BigInteger P){
        Random rnd = new Random();
        int size = P.bitLength();
        BigInteger x = new BigInteger(size, rnd);
        System.out.println("P to: " + P);
        System.out.println("X to: " + x);
        BigInteger stepone = P.gcd(x);
        System.out.println("GCD x i P: " + stepone);
        BigInteger one = new BigInteger("1");
        BigInteger negativeone = new BigInteger("-1");
        BigInteger two = new BigInteger("2");
        BigInteger zero = new BigInteger("0");
        BigInteger m = P;
        m = P.subtract(one);
        int i=0;
        boolean z = true;
        while(z)
        {
            if(m.mod(two).equals(zero)){
                m = m.divide(two);
                System.out.println("m mod 2 " + P);
                i++;
                System.out.println("i " + i);
            }
            else{
                z = false;
            }
        }


        System.out.println("P to: " + P);
        System.out.println("m to: " + m);
        System.out.println("i to: " + i);
        System.out.println("P-1 = 2^" + i + "*" + m);

        BigInteger y = x.modPow(m,P);
        System.out.println("Y to: " + y);

        //while(!y.equals(one) || !y.equals(negativeone)){
         for( int klej =0 ; klej < 100; klej++) {


             y = y.modPow(two, P);
             System.out.println("Y jest równy: " + y);
         }
        //}
        return true;
    }


    ///wyznaczanie klucza publicznego N=P*Q
    public BigInteger getN(BigInteger p, BigInteger q){
        BigInteger tmp1, tmp2, n;
        n=p.multiply(q);
        return n;
    }
    ///szyfrowanie --> C=P^2(mod N)
    public BigInteger[] cipher(byte[] plain, BigInteger n){
        BigInteger[] ciphered = new BigInteger[plain.length];
        BigInteger temp;
        for(int i=0 ;i<plain.length ;i++)
        {
            byte pom = 0x00;
            short plainShort = (short)(((pom & 0xFF) << 8) | (plain[i] & 0xFF));
            int tmp;
            tmp=(plainShort*plainShort);
            temp=BigInteger.valueOf(tmp);
           // System.out.println("Tekst jawny o indeksie " + i + "to: " + plain[i]);
           // System.out.print("zkwadratowany element tekstu jawnego : ");
            //System.out.println(temp.mod(n));
            //System.out.println("N: " + n);
            ciphered[i]=temp.mod(n);
            //System.out.println("zaszyfrowany element: " + ciphered[i]);
        }
        return ciphered;
    }
    ///Algorytm euklidesa odnajdywania NWD
    ///wykorzystywany przy odszyfrowywaniu


    public BigInteger[] gcd(BigInteger a, BigInteger b) {

        if(!b.equals(BigInteger.ZERO)){
            gcd(b, a.mod(b));
            BigInteger pom = tab[0];
            tab[0] = tab[1].subtract((a.divide(b)).multiply(tab[0]));
            tab[1] = pom;
        }
        return tab;
    }
///ta kurwa jeszcze wymaga troche pracy
///te inty na koniec przekonwertuje do byte - przy obliczeniach na bajtach wypierdalało mi błąd
    public void decipher(BigInteger[] ciphered, BigInteger n, BigInteger p, BigInteger q){

        //byte[] deciphered = new byte[ciphered.length];
        BigInteger mp1[]=new BigInteger[ciphered.length];
        BigInteger mp2[]=new BigInteger[ciphered.length];
        BigInteger mq1[]=new BigInteger[ciphered.length];
        BigInteger mq2[]=new BigInteger[ciphered.length];
        BigInteger yp[]=new BigInteger[ciphered.length];
        BigInteger yq[]=new BigInteger[ciphered.length];
        BigInteger pom, pom2, pom3;
        BigInteger tmp[]=new BigInteger[2];
        ///4 pierwiastki kwadratowe z c(mod n)
        ///tylko jeden z nich zawiera zaszyfrowaną wiadomość
        BigInteger x1[]=new BigInteger[ciphered.length];
        BigInteger x2[]=new BigInteger[ciphered.length];
        BigInteger x3[]=new BigInteger[ciphered.length];
        BigInteger x4[]=new BigInteger[ciphered.length];

        for(int i=0 ;i<ciphered.length ;i++)
        {
///zabawa zaczyna się od obliczenia pierwiastków kwadratowych liczby c(mod p) i c(mod q)
///tzn mp=c^((p+1)/4)(mod p) i mq=c^((q+1)/4)(mod q)

            BigInteger FOUR = new BigInteger("4");
            pom = p.add(BigInteger.ONE);
            pom = pom.divide(FOUR);
            mp1[i] = ciphered[i].modPow(pom, p);

            pom2 = q.add(BigInteger.ONE);
            pom2 = pom2.divide(FOUR);
            mq1[i] = ciphered[i].modPow(pom2, q);
///wykorzystujemy algo euklidesa żeby wyznaczyć liczby spełniające warunek yp*p+yq*q=1
            tab[0] = BigInteger.valueOf(0);
            tab[1] = BigInteger.valueOf(1);

            tmp=gcd(p,q);
            yp[i]=tmp[1];
            yq[i]=tmp[0];
            //System.out.println("Yp "+ yp[i]);
            //System.out.println("Yq "+ yq[i]);

            pom = (yp[i].multiply(p)).multiply(mq1[i]);
            pom2 = (yq[i].multiply(q)).multiply(mp1[i]);
            pom3 = pom.add(pom2);
            x1[i] = pom3.mod(n);
            x2[i] = n.subtract(x1[i]);

            pom3 = pom.subtract(pom2);
            x3[i] = pom3.mod(n);
            x4[i] = n.subtract(x3[i]);
        }
        this.x1=x1;
        this.x2=x2;
        this.x3=x3;
        this.x4=x4;
    }


    public byte[] convert(BigInteger x[]){
        byte[] converted = new byte[x.length];
        for(int i=0; i<x.length; i++){
            converted[i] = x[i].byteValue();
        }
        return converted;
    }


    public void saveToFile(byte[] cipheredText, String filePath){
        Path path = Paths.get(filePath);
        try{
            Files.write(path, cipheredText);
        }
        catch (IOException e) {
            System.out.println("Exception Occurred:");
        }
    }

    public byte[] readFromFile(String filePath){
        File plik = new File(filePath);
        byte[] fileContent = new byte[(int) plik.length()];
        FileInputStream fin = null;
        try{
            fin = new FileInputStream(plik);
            fin.read(fileContent);
        }
        catch (Exception ae){
            System.out.println("Blad " + ae);
        }
        try {
            fin.close();
        }
        catch (Exception ea){
            System.out.println("Blad " + ea);
        }
        return fileContent;
    }

}
