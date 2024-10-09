package parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Award {
    private final String name;
    private final int year;

    @JsonCreator
    public Award(@JsonProperty("name") String name,
                 @JsonProperty("year") int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return getName() + ", " + getYear();
    }
}