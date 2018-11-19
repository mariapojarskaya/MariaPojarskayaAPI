package entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class TestData {
    public int wIndex;
    public int sIndex;
    public int code;
    public String text;
    public List<String> rightWords;

    public static final TestData INVALID_LETTER = new TestData(0, 0, 1, "Country", Collections.singletonList("Coantry"));
    public static final TestData MISSED_LETTER = new TestData(0, 0, 1, "forver", Collections.singletonList("forever"));
    public static final TestData EXCESS_LETTER = new TestData(0, 0, 1, "hiistory", Collections.singletonList("history"));
    public static final TestData INCORRECT_UPPERCASE_LETTER = new TestData(0, 0, 3, "aUSTRIA", Collections.singletonList("Austria"));
    public static final TestData REPETITION_WORDS =
            new TestData(0, 0, 2, "Tomorrow I go to the gym gym", Collections.singletonList("Tomorrow I would go to the gym"));
    public static final TestData WRONG_CONTEXT_WORD = new TestData(0, 0, 1, "We write ode", Collections.singletonList("code"));
    public static final TestData MISTAKES_IN_THREE_WORDS =
            new TestData(0, 0, 1, "Londan is the cpital of Great Briitain", Arrays.asList("London", "capital", "Britain"));
    public static final TestData WORD_WITH_DIGITS = new TestData(0, 0, 1, "Hel4lo", Collections.singletonList("Hello"));

}
