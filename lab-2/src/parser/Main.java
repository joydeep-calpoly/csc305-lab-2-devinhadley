

package parser;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] paths = {"/parser/input1.json", "/parser/input2.json", "/parser/input3.json"};

        List<Person> people = Parser.getPeopleFromFilesOrgJson(paths);

        for (Person person : people) {
            System.out.println(person);
        }
    }
}
