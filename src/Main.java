public class Main {
    //No se perquè he de tornar a especificar a arraylist que serà de strings
    static String[] primers = {"u", "dos", "tres", "quatre", "cinc", "sis", "set", "vuit", "nou"};

    //Aquesta següent és l'única que té 10 elements
    static String[] superiorDeu = {"deu", "onze", "dotze", "tretze", "catorze", "quinze", "setze", "diset", "divuit", "dinou"};
    static String[] desenes = {"vint-i-", "trenta-", "quaranta-", "cinquanta-", "seixanta-", "setanta-", "vuitanta-", "noranta-"};
    static String[] centenars = {"cent", "dos-cents", "tres-cents", "quatre-cents", "cinc-cents", "sis-cents", "set-cents", "vuit-cents", "nou-cents"};

    public static void main(String[] args) {

        System.out.println(comptarCaracters(999));

    }

    public static Integer comptarCaracters(Integer numero) {
        Integer quantitat = 0;

        if (numero <= 99) {

            quantitat = comptarFins99(numero) + 4; //Del "zero"

        } else if (numero >= 100 && numero < 1000) {

            quantitat = comptarFins999(numero) + 4; //Del "zero"

        }
        
        return quantitat;
    }

    private static Integer comptarFins999(Integer numero){
        Integer quantitat = 0;

        String[] partsString = numero.toString().split("");
        Integer[] parts = new Integer[partsString.length];

        //Converteixo les parts en un Integer per poder utilitzar-les millor després
        for (int i = 0; i < parts.length - 1; i++) {
            parts[i] = Integer.parseInt(partsString[i]);
        }

        for (int i = 0; i < parts[0]; i++) {
            quantitat = quantitat + comptarFins99(99);
        }

        //Sumar els últims números
        quantitat = quantitat + comptarFins99(numero - parts[0] * 100);

        //Sumar tots els cents
        for (int i = 1; i < parts[0]; i++) { // Comencem amb 1 perquè del 0-99 no hi ha el cent, després ja si
            if (i == (parts[0] - 1)){
                quantitat = quantitat + centenars[i].length() * (numero - parts[0] * 100);
            } else {
                quantitat = quantitat + centenars[i].length() * 99;
            }
        }

        return quantitat;
    }

    private static Integer comptarFins99(Integer numero){
        Integer quantitat = 0;
        //Divideixo el número en una array per tenir les seves parts
        //Exemple 93 ---> [9, 3]
        String[] partsString = numero.toString().split("");
        Integer[] parts = new Integer[partsString.length];

        //Converteixo les parts en un Integer per poder utilitzar-les millor després
        for (int i = 0; i < parts.length; i++) {
            parts[i] = Integer.parseInt(partsString[i]);
        }

        Integer mesGran = parts[0];

        if (parts.length == 1) {
            //Compta primers
            quantitat = comptarPrimers(mesGran);

        }
        else if (parts.length == 2) {

            if (mesGran == 1) {

                quantitat = quantitat + comptarPrimers(9);
                quantitat = quantitat + comptarSuperiorDeu(parts[parts.length - 1]);

            } else if (mesGran >= 2) {
                quantitat = quantitat + comptarPrimers(9); //Comptar del 1-9
                quantitat = quantitat + comptarSuperiorDeu(9); //Comptar del 10-19
                //Mirar si el num és igual a 20 que s'ha de sumar només 4 i ja podem retornar

                if(mesGran == 2 && parts[1] == 0){
                    quantitat = quantitat + 4;
                    return quantitat;
                } else {

                    // Procedim a restar 3 a la quantitat perquè posteriorment, ja es comptarà el número 20, però
                    //com que en l'array de desenes tinc vint-i-, només ha de comptar 4 de vint
                    quantitat = quantitat - 3;
                    //Comencem a partir del 2 perquè estem vint-i-u
                    for (int i = 2; i <= mesGran; i++) { // Poso i = 2 perquè ja estem a partir del 20

                        //Comprovar si és una desena rodona com potser 30, no pot procedir més
                        if (i == mesGran && parts[1] == 0) {
                            quantitat = quantitat + desenes[i - 2].length();
                            break;
                        }
                        else if (i == mesGran) {
                            //Només vull que entri en aquest else if quan ja hagi comptat fins la desena del numero
                            //És a dir, si el número és 35, vull que entri a partir del 30

                            //I només ens falta comptar els primers números
                            //és a dir, si el num és 35, ja hauríem arribat a 30 i només faltarà els 5
                            quantitat = quantitat + comptarPrimers(parts[parts.length - 1]);

                            //Si el número és 35
                            // Necessitaré comptar 6 vegades trenta-, perquè primer és trenta- a soles i després trenta-u
                            quantitat = quantitat + (parts[parts.length - 1] + 1) * desenes[i - 2].length();

                            if(mesGran >= 3){
                                //Restem el "-" de les desenes del primer trenta-, del primer quaranta-...
                                //Aqui no pot entrar quan el número és 2X perquè ja li hem restat 3 del primer vint-i-
                                // Abans d'entrar al for
                                quantitat = quantitat - 1;
                            }


                        } else {
                            //Si el número és 35
                            // Necessitaré comptar 9 vegades vint-i- i després tornem al següent cicle for
                            quantitat = quantitat + (9 * desenes[i - 2].length());

                            //Restem el "-" de les desenes del primer trenta-, del primer quaranta-...
                            quantitat = quantitat - 1;

                            quantitat = quantitat + comptarPrimers(9);
                        }

                    }
                }

            }

        }

        return quantitat;
    }

    private static Integer comptarSuperiorDeu(Integer index){
        Integer quantitat = 0;

        for (int i = 0; i <= index; i++) {
            quantitat = quantitat + superiorDeu[i].length();
        }
        return quantitat;
    }
    
    private static Integer comptarPrimers(Integer index){
        Integer quantitat = 0;

        for (int i = 0; i < index; i++) {
            quantitat = quantitat + primers[i].length();

        }
        return quantitat;
    }


}