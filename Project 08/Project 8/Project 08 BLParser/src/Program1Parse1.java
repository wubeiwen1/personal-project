import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Zheng Ji Tan(tan.955) & Beiwen Wu(wu.4742) & ZheHui Li(li.8908)
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // TODO - fill in body

        //dequeue "INSTRUCTION"
        tokens.dequeue();
        String instr = tokens.dequeue();

        /*
         * check valid identifier: [id starts with a letter 'a'-'z', 'A'-'Z']
         * and [id contains only letters, digits '0'-'9', and '-'] and [id is
         * not one of the keywords or conditions in the BL language]
         */
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(instr),
                "Violation of: instruction name is a valid identifier");

        //check not primitive instruction name
        Reporter.assertElseFatalError(!instr.equals("move"),
                "Violation of: user defined instruction "
                        + "is not named after primitive instruction");
        Reporter.assertElseFatalError(!instr.equals("turnleft"),
                "Violation of: user defined instruction "
                        + "is not named after primitive instruction");
        Reporter.assertElseFatalError(!instr.equals("turnright"),
                "Violation of: user defined instruction "
                        + "is not named after primitive instruction");
        Reporter.assertElseFatalError(!instr.equals("infect"),
                "Violation of: user defined instruction "
                        + "is not named after primitive instruction");
        Reporter.assertElseFatalError(!instr.equals("skip"),
                "Violation of: user defined instruction "
                        + "is not named after primitive instruction");

        //check "IS" exist
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Violation of: no syntax error, \"IS\" expected after name");

        //parse body of the instruction
        body.parseBlock(tokens);

        //check "END" exist
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: no syntax error, \"END\" expected");

        //check same name
        Reporter.assertElseFatalError(tokens.dequeue().equals(instr),
                "Violation of: "
                        + "identifier at the end of instruction definition must be the "
                        + "same as the identifier at the beginning of the definition");

        return instr;

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body
        Map<String, Statement> pContext = this.newContext();
        Statement pBody = this.newBody();

        //check "PROGRAM" exists
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Violation of: no syntax error, \"PROGRAM\" expected");

        //check program name is valid identifier
        String programName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(programName),
                "Violation of: program name is a valid identifier");
        this.setName(programName);

        //check "IS" exists
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Violation of: no syntax error, \"IS\" expected after name");

        //check that next token is "INSTRUCTION" or "BEGIN"
        Reporter.assertElseFatalError(
                tokens.front().equals("INSTRUCTION")
                        || tokens.front().equals("BEGIN"),
                "Violation of: no syntax error, \"BEGIN\" or\"INSTRUCTION\" expected");

        //this.context
        while (tokens.front().equals("INSTRUCTION")) {
            Statement instructionBody = this.newBody();
            String identifier = parseInstruction(tokens, instructionBody);

            //check uniqueness of instruction identifier
            Reporter.assertElseFatalError(!pContext.hasKey(identifier),
                    "Violation of: name of each new user-defined instruction "
                            + "must be unique");
            pContext.add(identifier, instructionBody);
        }
        this.swapContext(pContext);

        //check "BEGIN" exists
        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Violation of: no syntax error, \"BEGIN\" expected");

        //this.body
        pBody.parseBlock(tokens);
        this.swapBody(pBody);

        //check "END" exists
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Violation of: no syntax error, \"END\" expected");

        //check program name is the same
        Reporter.assertElseFatalError(tokens.dequeue().equals(programName),
                "Violation of: "
                        + "identifier at the end of the program must be the same as "
                        + "the identifier at the beginning of the program. ");

        //syntax check
        Reporter.assertElseFatalError(
                tokens.front().equals(Tokenizer.END_OF_INPUT),
                "Violation of: end of the program");

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
