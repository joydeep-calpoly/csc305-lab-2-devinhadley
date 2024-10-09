package parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class Person {

    private final String name;
    private final List<String> knownFor;
    private final List<Award> awards;

    @JsonCreator
    public Person(@JsonProperty("name") String name,
                  @JsonProperty("knownFor") List<String> knownFor,
                  @JsonProperty("awards") List<Award> awards) {
        this.name = name;
        this.knownFor = new ArrayList<>(knownFor);
        this.awards = new ArrayList<>(awards);
    }

    public String getName() {
        return name;
    }

    public List<String> getKnownFor() {
        return new ArrayList<>(knownFor);
    }

    public List<Award> getAwards() {
        return new ArrayList<>(awards);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");

        sb.append("Known For:\n");
        for (String known : knownFor) {
            sb.append("\t").append(known).append("\n");
        }

        sb.append("Awards:\n");
        for (Award award : awards) {
            sb.append("\t").append(award).append("\n");
        }

        return sb.toString();
    }
}