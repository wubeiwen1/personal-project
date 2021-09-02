import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

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
            implements Comparator<Entry<String, Integer>>, Serializable {
        private static final long serialVersionUID = -8554858568197410078L;

        @Override
        public int compare(Entry<String, Integer> o1,
                Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Compare the value of {@code Map.Pair<String, Integer>} in decreasing
     * order.
     */
    private static class IntegerLT
            implements Comparator<Entry<String, Integer>>, Serializable {
        private static final long serialVersionUID = -8554858568197410078L;

        @Override
        public int compare(Entry<String, Integer> o1,
                Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Outputs the "opening" tags in the generated HTML file.
     *
     * @param outputFile
     *            the output stream
     * @param title
     *            the title of the page
     * @updates out.content
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHtmlHeader(PrintWriter outputFile, String title) {
        assert outputFile != null : "Violation of: out is not null";
//        assert outputFile.isOpen() : "Violation of: out.is_open";

        outputFile.println("<html>");
        outputFile.println("   <head>");
        outputFile.println("       <title>" + title + "</title>");
        outputFile.println(
                "       <link href=\"http://web.cse.ohio-state.edu/software/2231/"
                        + "web-sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        outputFile.println("   </head>");
        outputFile.println("   <body>");
        outputFile.println("       <h2>" + title + "</h2>");
    }

    /**
     * Outputs the "body" tags of the HTML file. In the body, there would be tag
     * cloud that displays the N words with the highest count from the input
     * file given by the user, where N is the a positive integer that the user
     * input.
     *
     * @param outputFile
     *            the output stream
     * @param wordCountArray
     *            a sorting machine with N pairs of
     *            {@code Map.Pair<String, Integer>}, sorted in alphabetical
     *            order
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "body" tags]
     */
    private static void outputHtmlBody(PrintWriter outputFile,
            ArrayList<Entry<String, Integer>> wordCountArray) {
        assert outputFile != null : "Violation of: out is not null";
//        assert outputFile.isOpen() : "Violation of: out.is_open";

        outputFile.println("       <hr>");
        outputFile.println("       <div class=\"cdiv\">");
        outputFile.println("           <p class=\"cbox\">");

        //prints out the tag cloud
        for (Entry<String, Integer> pair : wordCountArray) {
            outputFile.println("               <span style=\"cursor:default\" "
                    + "class=\"f" + wordFontSize(pair.getValue())
                    + "\" title=\"count: " + pair.getValue() + "\">"
                    + pair.getKey() + "</span>");
        }
    }

    /**
     * Outputs the "closing" tags in the generated HTML file.
     *
     * @param outputFile
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputHtmlFooter(PrintWriter outputFile) {
        assert outputFile != null : "Violation of: out is not null";
//        assert outputFile.isOpen() : "Violation of: out.is_open";

        outputFile.println("           </p>");
        outputFile.println("       </div>");
        outputFile.println("   </body>");
        outputFile.println("</html>");
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param separatorStr
     *            the given {@code String}
     * @param separators
     *            the {@code Set} to be replaced
     * @replaces separator
     * @ensures separator = entries(separatorStr)
     */
    private static void generateSeparator(String separatorStr,
            HashSet<Character> separators) {
        assert separatorStr != null : "Violation of: str is not null";
        assert separators != null : "Violation of: strSet is not null";

        //checks if separatorStr is empty
        if (separatorStr.length() > 0) {
            Character tmp = separatorStr.charAt(0);
            /*
             * adds the separator from separatorStr into separator, if it is not
             * inside
             */
            if (!separators.contains(tmp)) {
                separators.add(tmp);
            }
            //removes the first character in separatorStr
            String str2 = separatorStr.substring(1, separatorStr.length());
            generateSeparator(str2, separators);
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
            HashSet<Character> separators) {
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
    private static void processLine(String line, HashSet<Character> separators,
            HashMap<String, Integer> wordCount) {
        assert line.length() > 0 : "Line can not be empty";
        assert separators.size() > 0 : "The set of separators cannot be empty";

        String tmpLine = line;
        String word = nextWordOrSeparator(tmpLine, 0, separators).toLowerCase();

        /*
         * checks if word is a separator. if it is not a separator, it would be
         * added as a key to wordCount. If wordCount already has that key, it
         * would add 1 to the value of that key instead.
         */
        if (!separators.contains(word.charAt(0))
                && !wordCount.containsKey(word)) {
            wordCount.put(word, 1);
        } else if (wordCount.containsKey(word)) {
            int val = wordCount.get(word);
            val++;
            wordCount.replace(word, val);
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
     * Sorts the values in {@code wordCount} from the largest to smallest, then
     * reduce the size of {@code wordCount} to {@code numOfWords}, before
     * sorting the remaining keys in {@code wordCount} alphabetically. It would
     * then return a sorted ArrayList that contains what is in
     * {@code wordCount}.
     *
     * @param wordCount
     *            a word->count map that contains a word as the key and the
     *            count of the word as the value
     * @param numOfWords
     *            the number of words to be included in {@code wordCount}
     * @updates wordCount
     * @return ArrayList<Entry<String, Integer>> = [contains the top
     *         {@code numOfWords} of words with the highest count, sorted by
     *         alphabetical order]
     */
    private static ArrayList<Entry<String, Integer>> getSortedWords(
            HashMap<String, Integer> wordCount, int numOfWords) {

        Set<Entry<String, Integer>> wordCountSet = wordCount.entrySet();
        Comparator<Entry<String, Integer>> compareValue = new IntegerLT();
        ArrayList<Entry<String, Integer>> wordCountArray;
        wordCountArray = new ArrayList<Entry<String, Integer>>();

        /*
         * sorts value in decreasing order
         */
        for (Entry<String, Integer> pair : wordCountSet) {
            wordCountArray.add(pair);
        }
        wordCountArray.sort(compareValue);
        while (wordCountArray.size() > numOfWords) {
            wordCountArray.remove(wordCountArray.size() - 1);
        }

        /*
         * sorts key in alphabetical order
         */
        Comparator<Entry<String, Integer>> compareKey = new StringLT();
        wordCountArray.sort(compareKey);

        return wordCountArray;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader inputFileReader;
        PrintWriter outputFile;
        Scanner in = new Scanner(System.in);
        HashMap<String, Integer> wordCount = new HashMap<String, Integer>();

        /*
         * asks for the name of input file and output file
         */
        System.out.print("Please enter the name of an input file: ");
        String inputFileName = in.nextLine();
        System.out.print("Please enter the name of an output file: ");
        String outputFileName = in.nextLine();

        /*
         * creates a set of separators
         */
        String separatorString = " \t\n\r,-.!?';:/\\()[]{}<>@#$%^&*_+=~`\"";
        HashSet<Character> separators = new HashSet<Character>();
        generateSeparator(separatorString, separators);

        /*
         * create file reader and file writer
         */
        try {
            outputFile = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFileName)));
        } catch (IOException e) {
            System.err.println("Error opening output file.");
            in.close();
            return;
        }
        try {
            inputFileReader = new BufferedReader(new FileReader(inputFileName));
        } catch (IOException e) {
            System.err.println("Error opening input file.");
            outputFile.close();
            in.close();
            return;
        }

        /*
         * counts the number of times each word appears in inputFileReader and
         * store them in wordCount with the word as the key and its count as the
         * value
         */

        String line;
        try {
            line = inputFileReader.readLine();
            while (line != null) {
                if (line.length() > 0) {
                    processLine(line, separators, wordCount);
                }
                line = inputFileReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading input file.");
        }

        /*
         * ask for number of words to be included in the generated tag cloud
         */
        System.out.print("Please enter the number of words to be "
                + "included in the generated tag cloud: ");
        int numOfWords = -1;
        while (numOfWords <= 0) {
            boolean isInt = true;
            //ensure input is a number
            try {
                numOfWords = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                isInt = false;
                System.err.println("Please enter a number.");
            }
            //checks for positive number
            if (isInt && numOfWords <= 0) {
                System.err
                        .println("Please enter a positive number more than 0.");
            }
            /*
             * ensure that number entered is lesser than the number of words in
             * the given file
             */
            if (numOfWords > wordCount.size()) {
                numOfWords = -1;
                System.err.println(
                        "Please enter a positive number lesser than or equals to "
                                + wordCount.size() + ".");
            }
        }

        ArrayList<Entry<String, Integer>> wordCountArray = getSortedWords(
                wordCount, numOfWords);

        /*
         * output the cloud tag file
         */
        String fileTitle = "Top " + numOfWords + " words in " + inputFileName;
        System.out.println("--- ### Generating Output File ### ---");
        outputHtmlHeader(outputFile, fileTitle);
        outputHtmlBody(outputFile, wordCountArray);
        outputHtmlFooter(outputFile);
        System.out.println("------------ ### Done ### ------------");

        try {
            inputFileReader.close();
        } catch (IOException e) {
            System.err.println("Unable to close inputFileReader.");
        }
        outputFile.close();
        in.close();
    }

}
