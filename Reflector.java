/*
 Class that represents a reflector in the enigma.
 @author Eric Escobar
*/

package enigma;

class Reflector extends Rotor {

    /** Extending class has to have base constructor
     *  calling parent contrstr. */
    public Reflector() {
        super();
    }
    /** Reflector Contructor that takes in POS, INPUTNOTCH, PERMUTE
     *  and sets them according to parent constructor. */
    public Reflector(int pos, char[] inputNotch, char[] permute) {
        super(pos, inputNotch, permute);
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

    /** Reflectors do not advance. */
    @Override
    void advance() {
    }

}
