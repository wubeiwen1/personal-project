import java.io.Serializable;
import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.FormatChecker;

/**
 * This program takes a text file given by the user and generates an HTML file
 * with the most frequently used words within the file.
 *
 * @author Zheng Ji Tan(tan.955) & Beiwen Wu(wu.4742) & ZheHui Li(li.8908)
 *
 */
public final class TagCloudGenerator {

    /**
     * Set up minimum frequency for the words.
     */
    private static int minFrequency = 0;

    /**
     * Set up maximum frequency for the words.
     */
    private static int maxFrequency = 0;

    /**
     * Set up maximum font size for a word.
     */
    private static final int MAX_FONT_SIZE = 48;

    /**
     * Set up font size for a word.
     */
    private static final int MIN_FONT_SIZE = 11;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Compare the key of {@code Map.Pair<String, Integer>} in alphabetical
     * order.
     */
    private static class StringLT
            implements Comparator<Map.Pair<String, Integer>>, Serializable {
        private static final long serialVersionUID = -8554858568197410078L;

        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o1.key().compareToIgnoreCase(o2.key());
        }
    }

    /**
     * Compare the value of {@code Map.Pair<String, Integer>} in decreasing
     * order.
     */
    private static class IntegerLT
            implements Comparator<Map.Pair<String, Integer>>, Serializable {
        private static final long serialVersionUID = -8554858568197410078L;

        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Outputs the "opening" tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     * @param title
     *            the title of the page
     * @updates out.content
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHtmlHeader(SimpleWriter out, String title) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("<html>");
        out.println("   <head>");
        out.println("       <title>" + title + "</title>");
        out.println(
                "       <link href=\"http://web.cse.ohio-state.edu/software/2231/"
                        + "web-sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("   </head>");
        out.println("   <body>");
        out.println("       <h2>" + title + "</h2>");
    }

    /**
     * Outputs the "body" tags of the HTML file. In the body, there would be tag
     * cloud that displays the N words with the highest count from the input
     * file given by the user, where N is the a positive integer that the user
     * input.
     *
     * @param out
     *            the output stream
     * @param sortString
     *            a sorting machine with N pairs of
     *            {@code Map.Pair<String, Integer>}, sorted in alphabetical
     *            order
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "body" tags]
     */
    private static void outputHtmlBody(SimpleWriter out,
            SortingMachine<Map.Pair<String, Integer>> sortString) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("       <hr>");
        out.println("       <div class=\"cdiv\">");
        out.println("           <p class=\"cbox\">");

        //prints out the tag cloud
        for (Map.Pair<String, Integer> pair : sortString) {
            out.println("               <span style=\"cursor:default\" "
                    + "class=\"f" + wordFontSize(pair.value())
                    + "\" title=\"count: " + pair.value() + "\">" + pair.key()
                    + "</span>");
        }
    }

    /**
     * Outputs the "closing" tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputHtmlFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("           </p>");
        out.println("       </div>");
        out.println("   </body>");
        out.println("</html>");
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param separatorStr
     *            the given {@code String}
     * @param separator
     *            the {@code Set} to be replaced
     * @replaces separator
     * @ensures separator = entries(separatorStr)
     */
    private static void generateSeparator(String separatorStr,
            Set<Character> separator) {
        assert separatorStr != null : "Violation of: str is not null";
        assert separator != null : "Violation of: strSet is not null";

        //checks if separatorStr is empty
        if (separatorStr.length() > 0) {
            Character tmp = separatorStr.charAt(0);
            /*
             * adds the separator from separatorStr into separator, if it is not
             * inside
             */
            if (!separator.contains(tmp)) {
                separator.add(tmp);
            }
            //removes the first character in separatorStr
            String str2 = separatorStr.substring(1, separatorStr.length());
            generateSeparator(str2, separator);
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        String result = "";

        //concatenate the character at position to result
        result += text.charAt(position);

        /*
         * checks if the index of the next character after position is over the
         * text length (to prevent index out of bounds).
         */
        if ((position + 1) < text.length()) {
            /*
             * checks if the current character is a separator
             */
            if (separators.contains(text.charAt(position))) {
                //checks if the next character is a separator
                if (separators.contains(text.charAt(position + 1))) {
                    result += nextWordOrSeparator(text, position + 1,
                            separators);
                }
            } else {
                //checks if the next character is a word
                if (!separators.contains(text.charAt(position + 1))) {
                    result += nextWordOrSeparator(text, position + 1,
                            separators);
                }
            }
        }
        return result;
    }

    /**
     * Takes in a line of words and separators, and adds only words to
     * {@code  Map<String, Integer>}. If {@code  Map<String, Integer>} already
     * has that word as a key, it would add 1 to the value of that key instead.
     *
     * @param line
     *            a line of words and separators
     * @param separators
     *            a set of separators
     * @param wordCount
     *            a word -> count map
     * @requires |line| > 0 && |separators| > 0
     * @updates wordCount
     * @ensures {@code Map<String, Integer>} adds all unique words in
     *          {@code String} as keys, with the number of times the word
     *          appeared as the value.
     */
    private static void processLine(String line, Set<Character> separators,
            Map<String, Integer> wordCount) {
        assert line.length() > 0 : "Line can not be empty";
        assert separators.size() > 0 : "The set of separators cannot be empty";

        String tmpLine = line;
        String word = nextWordOrSeparator(tmpLine, 0, separators).toLowerCase();

        /*
         * checks if word is a separator. if it is not a separator, it would be
         * added as a key to wordCount. If wordCount already has that key, it
         * would add 1 to the value of that key instead.
         */
        if (!separators.contains(word.charAt(0)) && !wordCount.hasKey(word)) {
            wordCount.add(word, 1);
        } else if (wordCount.hasKey(word)) {
            int val = wordCount.value(word);
            val++;
            wordCount.replaceValue(word, val);
        }
        tmpLine = tmpLine.substring(word.length(), line.length());

        //checks if tmpLine is empty
        if (tmpLine.length() > 0) {
            processLine(tmpLine, separators, wordCount);
        }
    }

    /**
     * Calculate the font size for given word.
     *
     * @param wordFrequency
     *            the frequency of word appear in the document
     *
     * @return the font size of the word
     */
    private static int wordFontSize(int wordFrequency) {
        int fontSize = MIN_FONT_SIZE;

        if (wordFrequency == minFrequency) {
            fontSize = MIN_FONT_SIZE;
        } else if (wordFrequency == maxFrequency) {
            fontSize = MAX_FONT_SIZE;
        } else {
            fontSize = (int) Math
                    .floor(MIN_FONT_SIZE + (0.0 + wordFrequency - minFrequency)
                            / (maxFrequency - minFrequency)
                            * (MAX_FONT_SIZE - MIN_FONT_SIZE));
        }
        return fontSize;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //asks for the name of input and output file
        out.print("Please enter the name of an input file: ");
        String inputFileName = in.nextLine();
        out.print("Please enter the name of an output file: ");
        String outputFileName = in.nextLine();

        //ask for number of words to be included in the generated tag cloud
        out.print("Please enter the number of words to be "
                + "included in the generated tag cloud: ");
        String numOfWordsStr = in.nextLine();
        int numOfWords = -1;
        if (FormatChecker.canParseInt(numOfWordsStr)) {
            numOfWords = Integer.parseInt(numOfWordsStr);
        }
        //check if numOfWords is a positive integer
        while (!FormatChecker.canParseInt(numOfWordsStr) || numOfWords < 0) {
            out.println("Please enter a positive integer.");
            out.print("Please enter the number of words to be "
                    + "included in the generated tag cloud: ");
            numOfWordsStr = in.nextLine();
            if (FormatChecker.canParseInt(numOfWordsStr)) {
                numOfWords = Integer.parseInt(numOfWordsStr);
            }
        }

        SimpleReader inputFileReader = new SimpleReader1L(inputFileName);
        SimpleWriter outputFile = new SimpleWriter1L(outputFileName);

        /*
         * creates a set of separators
         */
        String separatorString = " \t\n\r,-.!?';:/\\()[]{}<>@#$%^&*_+=~`\"";
        Set<Character> separators = new Set1L<>();
        generateSeparator(separatorString, separators);

        /*
         * counts the number of times each word appears in inputFileReader and
         * store them in wordCount with the word as the key and its count as the
         * value
         */
        Map<String, Integer> wordCount = new Map1L<>();
        while (!inputFileReader.atEOS()) {
            String line = inputFileReader.nextLine();
            if (line.length() > 0) {
                processLine(line, separators, wordCount);
            }
        }

        /*
         * sorts value in decreasing order
         */
        Comparator<Map.Pair<String, Integer>> comparatorInt = new IntegerLT();
        SortingMachine<Map.Pair<String, Integer>> sortInt = new SortingMachine1L<>(
                comparatorInt);
        while (wordCount.size() > 0) {
            sortInt.add(wordCount.removeAny());
        }
        sortInt.changeToExtractionMode();

        /*
         * sorts key in alphabetical order
         */
        Comparator<Map.Pair<String, Integer>> comparatorString = new StringLT();
        SortingMachine<Map.Pair<String, Integer>> sortString = new SortingMachine1L<>(
                comparatorString);
        //add only N(numOfWords) words
        while (sortString.size() < numOfWords) {
            Pair<String, Integer> pair = sortInt.removeFirst();
            sortString.add(pair);

            if (sortString.size() == 1) {
                maxFrequency = pair.value();
            }
            if (sortString.size() == numOfWords) {
                minFrequency = pair.value();
            }
        }
        sortString.changeToExtractionMode();

        /*
         * output the cloud tag file
         */
        String fileTitle = "Top " + numOfWords + " words in " + inputFileName;
        out.println("--- ### Generating Output File ### ---");
        outputHtmlHeader(outputFile, fileTitle);
        outputHtmlBody(outputFile, sortString);
        outputHtmlFooter(outputFile);
        out.println("------------ ### Done ### ------------");

        inputFileReader.close();
        outputFile.close();
        in.close();
        out.close();
    }

}
