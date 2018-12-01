import Library.Matma;
import java.lang.Byte;
import java.lang.Object;

public class Correct {

    Rabin szyfry;
    byte[] files;
    Matma[] zaszyfrowany;
    Matma N;

    Correct(Rabin szyfry, byte[] files, Matma[] zaszyfrowany, Matma N){
        this.szyfry=szyfry;
        this.files=files;
        this.zaszyfrowany=zaszyfrowany;
        this.N=N;
    }

    byte[] choose() {
        byte[] prawda = new byte[zaszyfrowany.length/3];

        /*for (int i = 0; i < szyfry.x1.length; i++) {// Prawdopodobnie ten for nie dziala
            byte tmp = 0;
            //byte tmp1 = (byte) 0xFF;
            short pom = szyfry.x1[i].shortValue();          // ma on za zadanie podzielic naszego longa na dwa inty i sprawdzic
            byte dwa = (byte) (pom & 0xFF);           // który z 4 znków jest poprawny ( a będzie to ten w którym jeden
            byte jeden = (byte) ((pom >> 8) & 0xFF);       // int bedzie samymi zerami tak jak przy szyfrowaniu ustalilismy)
            short pom1 = szyfry.x2[i].shortValue();
            byte dwa1 = (byte) (pom1 & 0xFF);
            byte jeden1 = (byte) ((pom1 >> 8) & 0xFF);
            short pom2 = szyfry.x3[i].shortValue();
            byte dwa2 = (byte) (pom2 & 0xFF);
            byte jeden2 = (byte) ((pom2 >> 8) & 0xFF);
            short pom3 = szyfry.x4[i].shortValue();
            byte dwa3 = (byte) (pom3 & 0xFF);
            byte jeden3 = (byte) ((pom3 >> 8) & 0xFF);
            if (dwa == 0x00) {
                //char a = (char) dwa;
                //if(szyfry.x1[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x1[i].shortValue();
                //if(Byte.valueOf(jeden).compareTo(Byte.valueOf(tmp)) == -1) jeden = (byte) ~jeden;
                if(Byte.compare(jeden,tmp) < 0) {
                    int tmp1 = jeden;
                    tmp1 = -tmp1;
                    prawda[i] = (byte) tmp1;
                    //prawda[i] = (byte)(jeden^tmp1);
                    System.out.println("elo1");
                }else prawda[i] = jeden;
                //System.out.print(a);
            } else if (dwa1 == 0x00) {
                //char b = (char) dwa1;
                //if(szyfry.x2[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x2[i].shortValue();
                if(Byte.compare(jeden1,tmp) < 0) {
                    int tmp1 = jeden1;
                    tmp1 = -tmp1;
                    prawda[i] = (byte) tmp1;
                    System.out.println("elo2");
                } else prawda[i] = jeden1;
                //System.out.print(b);
            } else if (dwa2 == 0x00) {
                //byte[] c = szyfry.intToByteArray(dwa2);
                //char c = (char) dwa2;
                //if(szyfry.x3[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x3[i].shortValue();
                if(Byte.valueOf(jeden2).compareTo(Byte.valueOf(tmp)) <0) {
                    int tmp1 = jeden2;
                    tmp1 = -tmp1;
                    prawda[i] = (byte) tmp1;
                    System.out.println("elo3");
                }else prawda[i] = jeden2;
                //System.out.print(c);
            } else {
                //byte[] d = szyfry.intToByteArray(dwa3);
                //char d = (char) dwa3;
                //if(szyfry.x4[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x4[i].shortValue();
                //System.out.print(d);
                if(Byte.valueOf(jeden3).compareTo(Byte.valueOf(tmp)) <0) {
                    int tmp1 = jeden3;
                    tmp1 = -tmp1;
                    prawda[i] = (byte) tmp1;
                    System.out.println("elo4");
                }
                else prawda[i] = jeden3;
            }*/
            /*if(szyfry.x1[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x1[i].shortValue();
            else if(szyfry.x2[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x2[i].shortValue();
            else if(szyfry.x3[i].compareTo(Matma.valueOf(65536)) == -1) prawda[i] = (byte) szyfry.x3[i].shortValue();
            else prawda[i] = (byte) szyfry.x4[i].shortValue();
            }*/
            //for(int j=0; j<zaszyfrowany.length; j++){

            Matma TWO = new Matma("2");
            int pom;
            JacobiSymbol jacobiSymbol = new JacobiSymbol();
                for(int i=0; i< zaszyfrowany.length; i+=3){
                    int pom1 = szyfry.x1[i/3].intValue();
                    System.out.println("X1: " + szyfry.x1[i/3]);
                    System.out.println("X1: " + pom1);
                    int pom2 = szyfry.x2[i/3].intValue();
                    System.out.println("X2: " + szyfry.x2[i/3]);
                    System.out.println("X2: " + pom2);
                    int pom3 = szyfry.x3[i/3].intValue();
                    System.out.println("X3: " + szyfry.x3[i/3]);
                    System.out.println("X3: " + pom3);
                    int pom4 = szyfry.x4[i/3].intValue();
                    System.out.println("X4: " + szyfry.x4[i/3]);
                    System.out.println("X4: " + pom4);
                    System.out.println("B0: " + zaszyfrowany[i+1]);
                    System.out.println("B1: " + zaszyfrowany[i+2]);
                    System.out.println("B0 dla Pom1: " + Matma.valueOf(pom1%2));
                    System.out.println("B0 dla Pom2: " + Matma.valueOf(pom2%2));
                    System.out.println("B0 dla Pom3: " + Matma.valueOf(pom3%2));
                    System.out.println("B0 dla Pom4: " + Matma.valueOf(pom4%2));
                    System.out.println("B1 dla Pom1: " + Matma.valueOf((1+jacobiSymbol.computeJacobiSymbol(Matma.valueOf(pom1),N))/2));
                    System.out.println("B1 dla Pom2: " + Matma.valueOf((1+jacobiSymbol.computeJacobiSymbol(Matma.valueOf(pom2),N))/2));
                    System.out.println("B1 dla Pom3: " + Matma.valueOf((1+jacobiSymbol.computeJacobiSymbol(Matma.valueOf(pom3),N))/2));
                    System.out.println("B1 dla Pom4: " + Matma.valueOf((1+jacobiSymbol.computeJacobiSymbol(Matma.valueOf(pom4),N))/2));
                    if(szyfry.x1[i/3].mod(TWO).equals(zaszyfrowany[i+1])){
                        pom = jacobiSymbol.computeJacobiSymbol(szyfry.x1[i/3],N);
                        if(Matma.valueOf((1+pom)/2).equals(zaszyfrowany[i+2])) {
                            prawda[i/3] = (byte) (pom1-128);
                            continue;
                        }
                    }
                    if(szyfry.x2[i/3].mod(TWO).equals(zaszyfrowany[i+1])){
                        pom = jacobiSymbol.computeJacobiSymbol(szyfry.x2[i/3],N);
                        if(Matma.valueOf((1+pom)/2).equals(zaszyfrowany[i+2])) {
                            prawda[i/3] = (byte) (pom2-128);
                            continue;
                        }
                    }
                    if(szyfry.x3[i/3].mod(TWO).equals(zaszyfrowany[i+1])){
                        pom = jacobiSymbol.computeJacobiSymbol(szyfry.x3[i/3],N);
                        if(Matma.valueOf((1+pom)/2).equals(zaszyfrowany[i+2])) {
                            prawda[i/3] = (byte) (pom3-128);
                            continue;
                        }
                    }
                    if(szyfry.x4[i/3].mod(TWO).equals(zaszyfrowany[i+1])){
                        pom = jacobiSymbol.computeJacobiSymbol(szyfry.x4[i/3],N);
                        if(Matma.valueOf((1+pom)/2).equals(zaszyfrowany[i+2])) {
                            prawda[i/3] = (byte) (pom4-128);
                            continue;
                        }
                    }
                }
        return prawda;
    }
}