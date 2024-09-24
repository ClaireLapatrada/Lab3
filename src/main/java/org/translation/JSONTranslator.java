package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final JSONArray sampleJsonArray;
    private final String alpha3 = "alpha3";

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            // this next line of code reads in a file from the resources folder as a String,
            // which we then create a new JSONArray object from.
            String jsonString = Files.readString(Paths
                    .get(getClass().getClassLoader()
                            .getResource(filename).toURI()));
            this.sampleJsonArray = new JSONArray(jsonString);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        CountryCodeConverter countryConverter = new CountryCodeConverter();
        String countryName = countryConverter.fromCountryCode(country);

        try {
            ArrayList<String> languages = new ArrayList<>();
            for (int i = 0; i < sampleJsonArray.length(); i++) {
                JSONObject line = sampleJsonArray.getJSONObject(i);
                ArrayList<String> keysList = new ArrayList<>();

                Iterator<String> keys = line.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!("id".equals(key) || "alpha2".equals(key) || alpha3.equals(key))) {
                        keysList.add(key);
                    }
                }
                if (line.getString("en").equals(countryName)) {
                    languages = keysList;
                }
            }
            return languages;

        }
        catch (JSONException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getCountries() {
        try {
            ArrayList<String> codes = new ArrayList<>();
            for (int i = 0; i < sampleJsonArray.length(); i++) {
                JSONObject line = sampleJsonArray.getJSONObject(i);
                codes.add(line.getString(alpha3));
            }
            return codes;

        }
        catch (JSONException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public String translate(String country, String language) {
        try {
            String translatedText = "Country not found";
            for (int i = 0; i < sampleJsonArray.length(); i++) {
                JSONObject line = sampleJsonArray.getJSONObject(i);
                if (line.getString(alpha3).equals(country.toLowerCase())) {
                    translatedText = line.getString(language);
                }
            }
            return translatedText;
        }
        catch (JSONException ex) {
            return "Country not found";
        }
    }
}
