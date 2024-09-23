package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */

    private static Iterator<String> data;

    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     *
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            data = lines.iterator();
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     *
     * @param code the language code
     * @return the name of the language corresponding to the code
     */

    public String fromLanguageCode(String code) {
        // Traverse and access each element using the Iterator
        while (data.hasNext()) {
            // Access the next element
            String line = data.next();
            String[] parts = line.strip().split("\t");
            for (String part : parts) {
                if (part.equals(code)) {
                    return parts[0];
                }
            }

        }
        return null;
    }

    /**
     * Returns the code of the language for the given language name.
     *
     * @param language the name of the language
     * @return the 2-letter code of the language
     */

    public String fromLanguage(String language) {
        while (data.hasNext()) {
            // Access the next element
            String line = data.next();
            if (line.contains(language)) {
                return line.split("\t")[1];
            }
        }
        return null;
    }

    /**
     * Returns how many languages are included in this code converter.
     *
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        int count = 0;
        while (data.hasNext()) {
            data.next();
            // Access the next element
            count++;
        }
        return count - 1;
    }
}
