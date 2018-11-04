package sample;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    ///losowanie klucza prywatnego P,Q=3(mod 4)
    public byte getP(){
        Random rand = new Random();
        int i=rand.nextInt(29)*4+3;
        byte p=(byte)i;
        return p;
    }
    ///wyznaczanie klucza publicznego N=P*Q
    public byte getN(byte p, byte q){
        int tmp;
        tmp=p*q;
        byte n=(byte)tmp;
        return n;
    }
    ///szyfrowanie --> C=P^2(mod N)
    public byte[] cipher(byte[] plain, byte n){
        byte[] ciphered = new byte[plain.length];
        for(int i=0 ;i<plain.length ;i++)
        {
            int tmp;
            tmp=(plain[i]*plain[i])%n;
            ciphered[i]=(byte) tmp;
        }
        return ciphered;
    }
    ///Algorytm euklidesa odnajdywania NWD
    ///wykorzystywany przy odszyfrowywaniu
    public int gcd(int a, int b) {
        if (a == 0)
            return b;

        while (b != 0) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }
        int tab[]=new int[2];
        tab[0]=a;
        tab[1]=b;
        return tab;
    }
///ta kurwa jeszcze wymaga troche pracy
///te inty na koniec przekonwertuje do byte - przy obliczeniach na bajtach wypierdalało mi błąd
    public byte[] decipher(byte[] ciphered, byte n, byte p, byte q){

        byte[] deciphered = new byte[ciphered.length];
        int mp1[]=new int[ciphered.length];
        int mp2[]=new int[ciphered.length];
        int mq1[]=new int[ciphered.length];
        int mq2[]=new int[ciphered.length];
        int yp[]=new int[ciphered.length];
        int yq[]=new int[ciphered.length];
        int tmp[]=new int[2];
        ///4 pierwiastki kwadratowe z c(mod n)
        ///tylko jeden z nich zawiera zaszyfrowaną wiadomość
        int x1[]=new int[ciphered.length];
        int x2[]=new int[ciphered.length];
        int x3[]=new int[ciphered.length];
        int x4[]=new int[ciphered.length];

        for(int i=0 ;i<ciphered.length ;i++)
        {
///zabawa zaczyna się od obliczenia pierwiastków kwadratowych liczby c(mod p) i c(mod q)
///tzn mp=c^((p+1)/4)(mod p) i mq=c^((q+1)/4)(mod q)
            mp1[i]=(Math.pow(ciphered[i], (p+1)/4))%p;
            mp2[i]=p-mp1[i];
            mq1[i]=(Math.pow(ciphered[i], (q+1)/4))%q;
            mq2[i]=q-mq1[i];
///wykorzystujemy algo euklidesa żeby wyznaczyć liczby spełniające warunek yp*p+yq*q=1
            tmp=gcd(p,q);
            yp[i]=tmp[0];
            yq[i]=tmp[1];

            x1[i]=(yp[i]*mq1[i]+yq[i]*mp1[i])%n;
            x2[i]=(yp[i]*mq2[i]+yq[i]*mp1[i])%n;
            x3[i]=(yp[i]*mq1[i]+yq[i]*mp2[i])%n;
            x4[i]=(yp[i]*mq2[i]+yq[i]*mp2[i])%n;
        }
        return deciphered;
    }



    public void saveToFile(byte[] cipheredText, String filePath, String filetoPath){
        Path path = Paths.get(filetoPath);
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
