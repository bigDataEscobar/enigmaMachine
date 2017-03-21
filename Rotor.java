/*
 Class that represents a rotor in the enigma machine.
 @author Eric Escobar
*/

package enigma;

class Rotor {

    /** Instance variable array to catch notches. */
    private char[] notch;
    /** Basis for permuations. */
    private char[] permuteBase = {'A', 'B', 'C', 'D', 'E', 'F',
                                  'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                  'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                                  'U', 'V', 'W', 'X', 'Y', 'Z'};
    /** Instance variable to store permutations. */
    private char[] permutation;
    /** Constructor. */
    public Rotor() {
        setPosition(0);
        char[] noNotch = new char[1];
        noNotch[0] = 'A';
        setNotch(noNotch);
        char[] noPermute = new char[1];
        noPermute[0] = ' ';
        permutation = noPermute;
    }
    /** Constructor that takes POS, INPUTNOTCH, PERMUTE. */
    public Rotor(int pos, char[] inputNotch, char[] permute) {
        setPosition(pos);
        setNotch(inputNotch);
        permutation = permute;
    }
    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    char toLetter(int p) {
        char convertLetter = ' ';
        char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                           'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                           'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        int toUnicode = p + 10;
        for (char letter : alphabet) {
            if (toUnicode == Character.getNumericValue(letter)) {
                convertLetter = letter;
            }
        }
        return convertLetter;
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    int toIndex(char c) {
        int toNum = Character.getNumericValue(c) - 10;
        return toNum;
    }

    /** Return my current rotational position as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getPosition() {
        return position;
    }

    /** Set getPosition() to POSN.  */
    void setPosition(int posn) {
        position = posn;
    }

    /** Set N to NOTCH. */
    void setNotch(char[] n) {
        if (n == null) {
            notch = new char[1];
            notch[0] = ' ';
        } else {
            notch = new char[n.length];
            for (int i = 0; i < n.length; i++) {
                notch[i] = n[i];
            }
        }
    }
    /** Return NOTCH. */
    char[] getNotch() {
        return notch;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        int toconv = p + position;
        if (toconv > permuteBase.length - 1) {
            toconv %= permuteBase.length;
        }
        char letter = toLetter(toconv);
        int index = 0;
        for (int i = 0; i < permuteBase.length; i++) {
            if (letter == permuteBase[i]) {
                index = i;
            }
        }
        char pLetter = permutation[index];
        int converterLetter = toIndex(pLetter) - position;
        if (converterLetter < 0) {
            converterLetter = permutation.length + converterLetter;
        }
        return converterLetter;
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int toconv = e + position;
        if (toconv > permuteBase.length - 1) {
            toconv %= permuteBase.length;
        }
        char letter = toLetter(toconv);
        int index = 0;
        for (int i = 0; i < permutation.length; i++) {
            if (letter == permutation[i]) {
                index = i;
            }
        }
        char pLetter = permuteBase[index];
        int rConvertedLetter = toIndex(pLetter) - position;
        if (rConvertedLetter < 0) {
            rConvertedLetter = permutation.length + rConvertedLetter;
        }
        return rConvertedLetter;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        for (char n : getNotch()) {
            if (getPosition() == toIndex(n)) {
                return true;
            }
        }
        return false;
    }

    /** Advance me one position. */
    void advance() {
        if (position != permuteBase.length - 1) {
            position = position + 1;
        } else {
            position = 0;
        }
    }

    /** My current position (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int position;

}
