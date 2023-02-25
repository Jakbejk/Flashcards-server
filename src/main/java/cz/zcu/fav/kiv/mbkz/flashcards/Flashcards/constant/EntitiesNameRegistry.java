package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant;

public final class EntitiesNameRegistry {
    public static final String ID_COLUMN = "ID";
    // ----------------------------------------------
    // ----------- FLASHCARD TABLE ------------------
    // ----------------------------------------------
    public static final String FLASHCARD_TABLE = "FLASHCARD";
    public static final String QUESTION_COLUMN = "_QUESTION_";
    public static final String ANSWER_COLUMN = "_ANSWER_";
    public static final String IMAGE_COLUMN = "_IMAGE_";
    public static final String EMAIL_COLUMN = "_EMAIL_";
    // ----------------------------------------------
    // ---------------- USER TABLE ------------------
    // ----------------------------------------------
    public static final String USER_TABLE = "TUSER";
    public static final String USERNAME_COLUMN = "_USERNAME_";
    public static final String UUID_COLUMN = "_UUID_";
    public static final String PROVIDER_COLUMN = "_PROVIDER_";
    public static final String EMAIL_VERIFIED_COLUMN = "_EMAIL_VERIFIED_";

    // ----------------------------------------------
    // ----------------- SET TABLE ------------------
    // ----------------------------------------------
    public static final String SET_TABLE = "TSET";
    public static final String SET_NAME_COLUMN = "_SET_NAME_";
    public static final String FLASHCARD_FK = "FLASHCARD_FK";
    // ----------------------------------------------
    // --------------- SET-USER TABLE ---------------
    // ----------------------------------------------
    public static final String SET_USER_JOIN_TABLE = "SET_USER_JOIN_T";
    public static final String USER_SET_FK = "USER_SET_FK";
    public static final String SET_USER_FK = "SET_USER_FK";
}
