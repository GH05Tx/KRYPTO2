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
            P = BigInteger.probablePrime(512, rnd);
            //P.isProbablePrime(100);
            //System.out.println("Heja");
        } while (!P.mod(FOUR).equals(THREE) && P.isProbablePrime(10000));
        return P;

    }

    ///wyznaczanie klucza publicznego N=P*Q
    public BigInteger getN(BigInteger p, BigInteger q){
        BigInteger n;
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
        BigInteger pom, pom2, pom3, yp, yq;
        BigInteger tmp[]=new BigInteger[2];
        ///4 pierwiastki kwadratowe z c(mod n)
        ///tylko jeden z nich zawiera zaszyfrowaną wiadomość
        BigInteger x1[]=new BigInteger[ciphered.length];
        BigInteger x2[]=new BigInteger[ciphered.length];
        BigInteger x3[]=new BigInteger[ciphered.length];
        BigInteger x4[]=new BigInteger[ciphered.length];

        tab[0] = BigInteger.valueOf(0);
        tab[1] = BigInteger.valueOf(1);
        BigInteger FOUR = new BigInteger("4");
        pom = p.add(BigInteger.ONE);
        pom = pom.divide(FOUR);
        pom2 = q.add(BigInteger.ONE);
        pom2 = pom2.divide(FOUR);
        tmp=gcd(p,q);
        yp=tmp[1];
        yq=tmp[0];
        for(int i=0 ;i<ciphered.length ;i++)
        {
///zabawa zaczyna się od obliczenia pierwiastków kwadratowych liczby c(mod p) i c(mod q)
///tzn mp=c^((p+1)/4)(mod p) i mq=c^((q+1)/4)(mod q)

            //BigInteger FOUR = new BigInteger("4");
            pom = p.add(BigInteger.ONE);
            pom = pom.divide(FOUR);
            mp1[i] = ciphered[i].modPow(pom, p);

            pom2 = q.add(BigInteger.ONE);
            pom2 = pom2.divide(FOUR);
            mq1[i] = ciphered[i].modPow(pom2, q);
///wykorzystujemy algo euklidesa żeby wyznaczyć liczby spełniające warunek yp*p+yq*q=1



            //System.out.println("Yp "+ yp);
            //System.out.println("Yq "+ yq);

            pom = (yp.multiply(p)).multiply(mq1[i]);
            pom2 = (yq.multiply(q)).multiply(mp1[i]);
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
