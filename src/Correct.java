
public class Correct {

    Rabin szyfry;
    byte[] files;

    Correct(Rabin szyfry, byte[] files){
        this.szyfry=szyfry;
        this.files=files;
    }

    byte[] choose() {
        byte[] prawda = new byte[files.length];

        for (int i = 0; i < szyfry.x1.length; i++) {          // Prawdopodobnie ten for nie dziala
            short pom = szyfry.x1[i].shortValue();            // ma on za zadanie podzielic naszego longa na dwa inty i sprawdzic
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
            if (jeden == 0x00) {
                //char a = (char) dwa;
                prawda[i] = dwa;
                //System.out.print(a);
            } else if (jeden1 == 0x00) {
                //char b = (char) dwa1;
                prawda[i] = dwa1;
                //System.out.print(b);
            } else if (jeden2 == 0x00) {
                //byte[] c = szyfry.intToByteArray(dwa2);
                //char c = (char) dwa2;
                prawda[i] = dwa2;
                //System.out.print(c);
            } else if (jeden3 == 0x00) {
                //byte[] d = szyfry.intToByteArray(dwa3);
                //char d = (char) dwa3;
                prawda[i] = dwa3;
                //System.out.print(d);
            }
        }
        return prawda;
    }
}
