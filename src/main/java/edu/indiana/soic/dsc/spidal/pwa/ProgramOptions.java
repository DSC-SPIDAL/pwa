package edu.indiana.soic.dsc.spidal.pwa;

public class ProgramOptions {
    public static String sequenceFile;

    // The rows to be considered
    public static int rowStart;
    public static int rowEnd;
    public static boolean revComplementRows;

    // The cols to be considered
    public static int colStart;
    public static int colEnd;
    public static boolean revComplementCols;

    // Must be negative to indicate a penalty
    public static int gapOpen;
    // Must be negative to indicate a penalty
    public static int gapExt;


    public static enum AlignmentType{
        SWG,
        NW
    }

    public static enum MoleculeType{

    }
}
