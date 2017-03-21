/*
 Enigma simulator.
 @author Eric Escobar
 */

package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public final class Main {
    /** Rotors initialized. */
    private static Rotor rI, rII, rIII, rIV, rV, rVI, rVII, rVIII;
    /** Reflectors initialized. */
    private static Reflector reflectB, reflectC;
    /** A listing of all possible rotors. */
    private static ArrayList<Rotor> rotorChoice = new ArrayList<Rotor>();

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {
        Machine M = new Machine();
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));

        buildRotors();

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    configure(M, line);
                } else {
                    try {
                        printMessageLine(M.convert(standardize(line)));
                    } catch (NullPointerException excp) {
                        System.exit(1);
                    }
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    static boolean isConfigurationLine(String line) {
        String[] rotorC = {" ", "I", "II", "III", "IV", "V",
                           "VI", "VII", "VIII"};
        ArrayList<String> rotorCheck =
            new ArrayList<String>(Arrays.asList(rotorC));
        String[] toSplit = line.split(" ");
        if (toSplit.length == 6) {
            if (toSplit[0].equals("*")) {
                if (toSplit[1].equals("B") || toSplit[1].equals("C")) {
                    if (rotorCheck.indexOf(toSplit[2]) > 0
                        && rotorCheck.indexOf(toSplit[3]) > 0
                        && rotorCheck.indexOf(toSplit[4]) > 0) {
                        if (toSplit[5].length() == 4) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the configuration specified in the
     *  assignment. */
    static void configure(Machine M, String config) {
        String[] input = config.split(" ");
        String[] rotorC = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
        String validRotors = "I II III IV V VI VII VIII";
        Rotor lRot, mRot, rRot;
        Reflector reflect;

        if (input[1].equals("B")) {
            reflect = reflectB;
        } else if (input[1].equals("C")) {
            reflect = reflectC;
        } else {
            reflect = reflectB;
            System.exit(1);
        }

        if (input[2].equals(input[3]) || input[2].equals(input[4])
            || input[3].equals(input[4])) {
            System.exit(1);
        }

        lRot = rI;
        mRot = rII;
        rRot = rIII;

        for (int i = 0; i < rotorC.length; i++) {
            if (input[2].equals(rotorC[i])) {
                lRot = rotorChoice.get(i);
            }
            if (input[3].equals(rotorC[i])) {
                mRot = rotorChoice.get(i);
            }
            if (input[4].equals(rotorC[i])) {
                rRot = rotorChoice.get(i);
            }
        }
        if (lRot == mRot || lRot == rRot || mRot == rRot) {
            System.exit(1);
        }

        M.setRotors(reflect, lRot, mRot, rRot, input[5]);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    static String standardize(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' '
                && !(Character.isLetter(line.charAt(i)))) {
                System.exit(1);
            }
        }
        line = line.replaceAll("\\s", "");
        line = line.toUpperCase();
        line = line.trim();
        return line;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    static void printMessageLine(String msg) {
        while (msg != null) {
            if (msg.length() < 5) {
                System.out.print(msg.substring(0) + "\n");
                msg = null;
            } else {
                System.out.print(msg.substring(0, 5) + " ");
                msg = msg.substring(5);
            }
        }
    }

    /** Create all the necessary rotors. */
    static void buildRotors() {
        char[] rOne = {'E', 'K', 'M', 'F', 'L', 'G', 'D', 'Q', 'V',
                       'Z', 'N', 'T', 'O', 'W', 'Y', 'H', 'X', 'U',
                       'S', 'P', 'A', 'I', 'B', 'R', 'C', 'J'};
        char[] rTwo = {'A', 'J', 'D', 'K', 'S', 'I', 'R', 'U', 'X', 'B',
                       'L', 'H', 'W', 'T', 'M', 'C', 'Q', 'G', 'Z', 'N',
                       'P', 'Y', 'F', 'V', 'O', 'E'};
        char[] rThree = {'B', 'D', 'F', 'H', 'J', 'L', 'C', 'P', 'R', 'T',
                         'X', 'V', 'Z', 'N', 'Y', 'E', 'I', 'W', 'G', 'A',
                         'K', 'M', 'U', 'S', 'Q', 'O'};
        char[] rFour = {'E', 'S', 'O', 'V', 'P', 'Z', 'J', 'A', 'Y', 'Q',
                        'U', 'I', 'R', 'H', 'X', 'L', 'N', 'F', 'T', 'G',
                        'K', 'D', 'C', 'M', 'W', 'B'};
        char[] rFive = {'V', 'Z', 'B', 'R', 'G', 'I', 'T', 'Y', 'U', 'P', 'S',
                        'D', 'N', 'H', 'L', 'X', 'A', 'W', 'M', 'J', 'Q', 'O',
                        'F', 'E', 'C', 'K'};
        char[] rSix = {'J', 'P', 'G', 'V', 'O', 'U', 'M', 'F', 'Y', 'Q', 'B',
                       'E', 'N', 'H', 'Z', 'R', 'D', 'K', 'A', 'S', 'X', 'L',
                       'I', 'C', 'T', 'W'};
        char[] rSeven = {'N', 'Z', 'J', 'H', 'G', 'R', 'C', 'X', 'M', 'Y',
                         'S', 'W', 'B', 'O', 'U', 'F', 'A', 'I', 'V', 'L',
                         'P', 'E', 'K', 'Q', 'D', 'T'};
        char[] rEight = {'F', 'K', 'Q', 'H', 'T', 'L', 'X', 'O', 'C', 'B', 'J',
                         'S', 'P', 'D', 'Z', 'R', 'A', 'M', 'E', 'W', 'N', 'I',
                         'U', 'Y', 'G', 'V'};
        char[] rB = {'Y', 'R', 'U', 'H', 'Q', 'S', 'L', 'D', 'P', 'X', 'N', 'G',
                     'O', 'K', 'M', 'I', 'E', 'B', 'F', 'Z', 'C', 'W', 'V', 'J',
                     'A', 'T'};
        char[] rC = {'F', 'V', 'P', 'J', 'I', 'A', 'O', 'Y', 'E', 'D', 'R', 'Z',
                     'X', 'W', 'G', 'C', 'T', 'K', 'U', 'Q', 'S', 'B', 'N', 'M',
                     'H', 'L'};
        char[] notchOne = {'Q'};
        char[] notchTwo = {'E'};
        char[] notchThree = {'V'};
        char[] notchFour = {'J'};
        char[] notchFive = {'Z'};
        char[] notch = {'Z', 'M'};
        char[] notchReflect = {' '};
        reflectB = new Reflector(0, notchReflect, rB);
        reflectC = new Reflector(0, notchReflect, rC);
        rI = new Rotor(0, notchOne, rOne);
        rII = new Rotor(0, notchTwo, rTwo);
        rIII = new Rotor(0, notchThree, rThree);
        rIV = new Rotor(0, notchFour, rFour);
        rV = new Rotor(0, notchFive, rFive);
        rVI = new Rotor(0, notch, rSix);
        rVII = new Rotor(0, notch, rSeven);
        rVIII = new Rotor(0, notch, rEight);
        rotorChoice.add(rI);
        rotorChoice.add(rII);
        rotorChoice.add(rIII);
        rotorChoice.add(rIV);
        rotorChoice.add(rV);
        rotorChoice.add(rVI);
        rotorChoice.add(rVII);
        rotorChoice.add(rVIII);

    }

}
