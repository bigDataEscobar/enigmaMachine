/*
 Class that represents a complete enigma machine.
 @author Eric Escobar
 */

package enigma;


class Machine {
    /** Instance variable REFLECT. */
    private Reflector reflect;
    /** Instance variables of rotors. */
    private Rotor l, m, r;
    /** Instance variable for the initial positions of rotors. */
    private String set;

    /** Set my rotors to (from left to right), REFLECTOR, LEFT,
     *  MIDDLE, and RIGHT, and SETTING.
     *  Initially, their positions are all 'A'. */
    void setRotors(Reflector reflector,
                   Rotor left, Rotor middle, Rotor right, String setting) {
        reflect = reflector;
        l = left;
        m = middle;
        r = right;
        set = setting;
        setPositions(set);

    }

    /** Set the positions of my rotors according to SETTING, which
     *  must be a string of 4 upper-case letters. The first letter
     *  refers to the reflector position, and the rest to the rotor
     *  positions, left to right. */
    void setPositions(String setting) {
        if (setting.length() != 4) {
            System.out.println("Incorrect setting - too many/little");
            System.exit(1);
        }
        Character reflector = setting.charAt(0);
        Character rotorL = setting.charAt(1);
        Character rotorM = setting.charAt(2);
        Character rotorR = setting.charAt(3);
        if (!(Character.isUpperCase(reflector))
            || !(Character.isUpperCase(rotorL))
            || !(Character.isUpperCase(rotorM))
            || !(Character.isUpperCase(rotorR))) {
            System.out.println("Setting was not all uppercase letters");
            System.exit(1);
        }
        reflect.setPosition(Character.getNumericValue(reflector) - 10);
        l.setPosition(Character.getNumericValue(rotorL) - 10);
        m.setPosition(Character.getNumericValue(rotorM) - 10);
        r.setPosition(Character.getNumericValue(rotorR) - 10);

    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String retEncoded = "";
        char finalChar;
        for (int i = 0; i < msg.length(); i++) {
            char oneChar = msg.charAt(i);
            if (r.atNotch() && m.atNotch()) {
                r.advance();
                m.advance();
                l.advance();
            } else if (r.atNotch()) {
                m.advance();
                r.advance();
            } else if (m.atNotch()) {
                r.advance();
                l.advance();
                m.advance();
            } else {
                r.advance();
            }
            int charToIndex = r.toIndex(oneChar);
            charToIndex = r.convertForward(charToIndex);
            charToIndex = m.convertForward(charToIndex);
            charToIndex = l.convertForward(charToIndex);
            charToIndex = reflect.convertForward(charToIndex);
            charToIndex = l.convertBackward(charToIndex);
            charToIndex = m.convertBackward(charToIndex);
            charToIndex = r.convertBackward(charToIndex);
            finalChar = r.toLetter(charToIndex);
            String encoded = Character.toString(finalChar);
            retEncoded += encoded;
        }
        return retEncoded;

    }
}
