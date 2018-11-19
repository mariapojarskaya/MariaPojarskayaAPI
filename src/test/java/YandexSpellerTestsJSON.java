import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import io.restassured.RestAssured;
import matchers.RegexMatcher;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static core.YandexSpellerConstants.*;
import static entity.CheckTextsTestData.NO_MISTAKES_TEXTS;
import static entity.CheckTextsTestData.TEXTS_WITH_MISTAKES;
import static entity.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class YandexSpellerTestsJSON {

    @DataProvider(name = "no mistakes")
    public Object[][] noMistakeData() {
        return new Object[][]{
                {"И"},
                {""},
                {"There were tomatoes, apples and cheese in the fridge",},
                {"Answer - 6",},
                {"",},
                {"This offer contains\n" + "line break",},
                {"Many        blanks      ",},
                {"I"},
                {"I am student",},
                {"I"},
                {"I am student",},
                {"It's summer time",},
                {"My number is 1",},
                {"This string contains \n special symbol ",},
                {"Much          spaces",},
                {"Разные languages в this предложении",},
        };
    }

    //1. Positive checkText test with data provider
    @Test(description = "Assert that api return empty body when there is no mistakes", dataProvider = "no mistakes")
    public void noMistakesTest(String text) {
        RestAssured
                .given(YandexSpellerApi.baseRequestConfiguration())
                .param(PARAM_TEXT, text)
                .log().all()
                .get().prettyPeek()
                .then().specification(YandexSpellerApi.successResponse())
                .assertThat()
                .body(RegexMatcher.matchesRegex(NO_MISTAKE_RESPONSE));
    }

    // 2.
    @Test(description = "Assert that api return right text when there is missed latter")
    public void missedLetterTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(MISSED_LETTER.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(MISSED_LETTER.wIndex).s.get(MISSED_LETTER.sIndex),
                equalTo(MISSED_LETTER.rightWords.get(MISSED_LETTER.wIndex)));
    }

    // 3.
    @Test(description = "Assert that api return right text when there is excess latter")
    public void excessLetterTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(EXCESS_LETTER.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(EXCESS_LETTER.wIndex).s.get(EXCESS_LETTER.sIndex),
                equalTo(EXCESS_LETTER.rightWords.get(EXCESS_LETTER.wIndex)));
    }

    // 4.
    @Test(description = "Wrong spelling of words depending on the context")
    public void wrongContextWordTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(WRONG_CONTEXT_WORD.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(WRONG_CONTEXT_WORD.wIndex).s.get(WRONG_CONTEXT_WORD.sIndex),
                equalTo(WRONG_CONTEXT_WORD.rightWords.get(WRONG_CONTEXT_WORD.wIndex)));
    }

    // 5.
    @Test(description = "Assert that api return right texts when there is several mistakes")
    public void mistakesInThreeWordsTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(MISTAKES_IN_THREE_WORDS.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        for (String rightWord : MISTAKES_IN_THREE_WORDS.rightWords) {
            assertThat(answers.get(MISTAKES_IN_THREE_WORDS.rightWords.indexOf(rightWord)).s.get(MISTAKES_IN_THREE_WORDS.sIndex),
                    equalTo(rightWord));
        }
    }

    // 6.
    @Test(description = "Assert that api return right text when there is invalid latter")
    public void invalidLetterTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(INVALID_LETTER.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(INVALID_LETTER.wIndex).s.get(INVALID_LETTER.sIndex),
                equalTo(INVALID_LETTER.rightWords.get(INVALID_LETTER.wIndex)));
    }

    // 7.
    @Test(description = "Correct words with digits when no options set")
    public void correctWordWithDigitsTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(WORD_WITH_DIGITS.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(WORD_WITH_DIGITS.wIndex).s.get(WORD_WITH_DIGITS.sIndex),
                equalTo(WORD_WITH_DIGITS.rightWords.get(WORD_WITH_DIGITS.wIndex)));
    }

    // 8.
    @Test(description = "Ignore words with digits when options equals 2")
    public void ignoreWordWithDigitsTest() {
        YandexSpellerApi.with().options(IGNORE_DIGITS).text(WORD_WITH_DIGITS.text).callCheckText().
                then().assertThat().body(RegexMatcher.matchesRegex(NO_MISTAKE_RESPONSE));
    }

    // 9. checkTexts method test
    @Test(description = "Checks spelling in several text fragments with no mistakes")
    public void noMistakesCheckTextsTest() {
        YandexSpellerApi.with().texts(NO_MISTAKES_TEXTS.texts).callCheckTexts().
                then().assertThat().body(RegexMatcher.matchesRegex(NO_MISTAKE_RESPONSE));
    }

    // 10. checkTexts method test
    @Test(description = "Checks spelling in several text fragments with mistakes")
    public void checkTextsWithMistakesTest() {
        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersCheckTexts(
                        YandexSpellerApi.with().language(Languages.RU).texts(TEXTS_WITH_MISTAKES.texts).callCheckTexts());
        for (int i = 0; i < TEXTS_WITH_MISTAKES.texts.length; i++) {
            assertThat(answers.get(i).get(TEXTS_WITH_MISTAKES.wIndex).s.get(TEXTS_WITH_MISTAKES.sIndex),
                    equalTo(TEXTS_WITH_MISTAKES.rightWords.get(i)));
        }
    }

    // Bug. Service doesn't find the incorrect use of uppercase and lowercase letters
    @Test(description = "Incorrect use of uppercase and lowercase letters")
    public void incorrectUppercaseLetterTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text(INCORRECT_UPPERCASE_LETTER.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(INCORRECT_UPPERCASE_LETTER.wIndex).s.get(INCORRECT_UPPERCASE_LETTER.sIndex),
                equalTo(INCORRECT_UPPERCASE_LETTER.rightWords.get(INCORRECT_UPPERCASE_LETTER.wIndex)));
    }

    // Bug. Service doesn't find repetition of words
    @Test(description = "Assert that api return right text when there is repetition of words")
    public void repetitionWordsTest() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().options(FIND_REPEAT_WORDS).text(REPETITION_WORDS.text).callCheckText());
        assertThat("expected number of answers is wrong.", answers.size(), Matchers.not(0));
        assertThat(answers.get(REPETITION_WORDS.wIndex).s.get(REPETITION_WORDS.sIndex),
                equalTo(REPETITION_WORDS.rightWords.get(REPETITION_WORDS.wIndex)));
    }
}
