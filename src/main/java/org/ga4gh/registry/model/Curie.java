package org.ga4gh.registry.model;

import java.text.ParseException;

public class Curie {

    private String prefix;

    private String id;

    public static Curie fromString(String curie) throws ParseException {
        
        String[] curieSplit = curie.split(":");
        if (curieSplit.length != 2) {
            throw new ParseException(curie + " is not in valid CURIE format", 0);
        }
        return new Curie(curieSplit[0], curieSplit[1]);
    }

    /* constructors */

    private Curie(String prefix, String id) {
        this.prefix = prefix;
        this.id = id;
    }

    /* setters and getters */

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
}
