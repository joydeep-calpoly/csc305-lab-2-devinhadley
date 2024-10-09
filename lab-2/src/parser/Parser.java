package parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static InputStream getFile(String path) {
        InputStream inputStream = Main.class.getResourceAsStream(path);
        if (inputStream != null) {
            return inputStream;
        } else {
            System.out.println("File not found!");
            return null;
        }
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }
        return jsonContent.toString();
    }


    public static List<Person> getPeopleFromFilesOrgJson(String[] paths) throws JSONException, IOException {

        List<Person> people = new ArrayList<>();

        for (String path : paths) {
            InputStream stream = getFile(path);

            String jsonData = convertStreamToString(stream);

            JSONObject jsonObject = new JSONObject(jsonData);

            String name = jsonObject.getString("name");

            JSONArray knownForArray = jsonObject.getJSONArray("knownFor");
            List<String> knownFor = new ArrayList<>();
            for (int i = 0; i < knownForArray.length(); i++) {
                knownFor.add(knownForArray.getString(i));
            }

            JSONArray awardsArray = jsonObject.getJSONArray("awards");
            List<Award> awards = new ArrayList<>();
            for (int i = 0; i < awardsArray.length(); i++) {
                JSONObject awardObject = awardsArray.getJSONObject(i);
                String awardName = awardObject.getString("name");
                int awardYear = awardObject.getInt("year");
                awards.add(new Award(awardName, awardYear));
            }
            people.add(new Person(name, knownFor, awards));

        }
        return people;
    }

    public static List<Person> getPeopleFromFilesJacksonDatabind(String[] paths) throws IOException {

        List<Person> people = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String path : paths) {
            InputStream stream = getFile(path);
            if (stream == null) {
                continue;
            }

            String jsonData = convertStreamToString(stream);

            try {
                Person person = objectMapper.readValue(jsonData, Person.class);
                people.add(person);
            } catch (JsonMappingException e) {
                System.out.println("Error mapping JSON to Person: " + e.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println("Error processing JSON: " + e.getMessage());
            }
        }

        return people;
    }
}