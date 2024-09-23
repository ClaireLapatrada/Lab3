package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private static Iterator<String> data;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

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
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        while (data.hasNext()) {
            // Access the next element
            String line = data.next();
            if (line.contains(code.toUpperCase())) {
                return line.split("\t")[0];
            }
        }
        return null;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        while (data.hasNext()) {
            // Access the next element
            String line = data.next();
            if (line.contains(country)) {
                return line.split("\t")[2];
            }
        }
        return null;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        int count = 0;
        while (data.hasNext()) {
            data.next();
            // Access the next element
            count++;

        }
        return count - 1;
    }
}
