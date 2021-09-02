import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.program.Program;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Zheng Ji Tan(tan.955) & Beiwen Wu(wu.4742) & ZheHui Li(li.8908)
 *
 */
public abstract class ProgramTest {

    /**
     * The names of a files containing a valid BL programs.
     */
    private static final String FILE_NAME_1 = "test/Program/program_Valid_Example.bl",
            PROG_VALID_NO_INSTRUCTION = "test/Program/program_Valid_NoInstruction.bl",
            PROG_VALID_NO_BODY = "test/Program/program_Valid_NoBody.bl",
            PROG_VALID_IDENTIFIERS = "test/Program/program_Valid_Identifiers.bl";

    /**
     * The names of a files containing an invalid BL instructions.
     */
    private static final String INSTR_INVALID_IDENTIFIER_1 = "test/Program/"
            + "program_instr_Invalid_Identifier1.bl",
            INSTR_INVALID_IDENTIFIER_2 = "test/Program/"
                    + "program_instr_Invalid_Identifier2.bl",
            INSTR_INVALID_IDENTIFIER_3 = "test/Program/"
                    + "program_instr_Invalid_Identifier3.bl",
            INSTR_INVALID_IDENTIFIER_4 = "test/Program/"
                    + "program_instr_Invalid_Identifier4.bl",
            INSTR_INVALID_INFECT = "test/Program/program_instr_Invalid_infect.bl",
            INSTR_INVALID_MOVE = "test/Program/program_instr_Invalid_Move.bl",
            INSTR_INVALID_SKIP = "test/Program/program_instr_Invalid_skip.bl",
            INSTR_INVALID_TURNLEFT = "test/Program/program_instr_Invalid_turnleft.bl",
            INSTR_INVALID_TURNRIGHT = "test/Program/program_instr_Invalid_turnright.bl",
            INSTR_INVALID_NON_UNIQUE_NAME = "test/Program/"
                    + "program_instr_Invalid_Non-UniqueName.bl",
            INSTR_INVALID_NOT_SAME_NAME = "test/Program/"
                    + "program_instr_Invalid_NonSameName.bl",
            INSTR_INVALID_MISSING_IS = "test/Program/program_instr_Invalid_MissingIS.bl",
            INSTR_INVALID_MISSING_END = "test/Program/"
                    + "program_instr_Invalid_MissingEND.bl",
            INSTR_INVALID_SMALL_END = "test/Program/program_instr_Invalid_SmallEND.bl";

    /**
     * The names of a files containing an invalid BL program.
     */
    private static final String PROG_INVALID_MISSING_PROGRAM = "test/Program/"
            + "program_Invalid_MissingProgram.bl",
            PROG_INVALID_IDENTIFIER_1 = "test/Program/program_Invalid_Identifier1.bl",
            PROG_INVALID_IDENTIFIER_2 = "test/Program/program_Invalid_Identifier2.bl",
            PROG_INVALID_IDENTIFIER_3 = "test/Program/program_Invalid_Identifier3.bl",
            PROG_INVALID_IDENTIFIER_4 = "test/Program/program_Invalid_Identifier4.bl",
            PROG_INVALID_MISSING_IS = "test/Program/program_Invalid_MissingIS.bl",
            PROG_INVALID_NOT_INSTRUCTION_OR_BEGIN = "test/Program/"
                    + "program_Invalid_NotInstructionOrBegin.bl",
            PROG_INVALID_MISSING_BEGIN = "test/Program/program_Invalid_MissingBEGIN.bl",
            PROG_INVALID_MISSING_END = "test/Program/program_Invalid_MissingEND.bl",
            PROG_INVALID_NOT_SAME_NAME = "test/Program/program_Invalid_NotSameName.bl",
            PROG_INVALID_THINGS_AFTER_END = "test/Program/"
                    + "program_Invalid_ThingsAfterEnd.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructorTest = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructorRef = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     ************** Test of parse on syntactically valid input. **************
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    @Test
    public final void testParseValidNoInstruction() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PROG_VALID_NO_INSTRUCTION);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(PROG_VALID_NO_INSTRUCTION);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    @Test
    public final void testParseValidNoBegin() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PROG_VALID_NO_BODY);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(PROG_VALID_NO_BODY);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    @Test
    public final void testParseValidIdentifiers() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(PROG_VALID_IDENTIFIERS);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(PROG_VALID_IDENTIFIERS);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     ************** Test of parse on syntactically invalid input. **************
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier1_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_IDENTIFIER_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier2_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_IDENTIFIER_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier3_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_IDENTIFIER_3);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier4_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_IDENTIFIER_4);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInfect_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_INFECT);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMove_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_MOVE);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorSkip_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_SKIP);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorTurnleft_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_TURNLEFT);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorTurnright_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_TURNRIGHT);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorNonUniqueName_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_NON_UNIQUE_NAME);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorNotSameName_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_NOT_SAME_NAME);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMissingIS_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_MISSING_IS);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMissingEND_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_MISSING_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorSmallEND_Instruction() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(INSTR_INVALID_SMALL_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMissingProgram_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_MISSING_PROGRAM);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier1_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_IDENTIFIER_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier2_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_IDENTIFIER_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier3_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_IDENTIFIER_3);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorInvalidIdentifier4_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_IDENTIFIER_4);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMissingIS_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_MISSING_IS);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorNotInstructionOrBegin_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(
                PROG_INVALID_NOT_INSTRUCTION_OR_BEGIN);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMissingBEGIN_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_MISSING_BEGIN);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorMissingEND_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_MISSING_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorNotSameName_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_NOT_SAME_NAME);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public final void testParseErrorThingsAfterEnd_Prog() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(PROG_INVALID_THINGS_AFTER_END);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }
}
