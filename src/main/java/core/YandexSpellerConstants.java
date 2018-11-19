package core;

public class YandexSpellerConstants {
    //useful constants for API under test
    public static final String YANDEX_SPELLER_API_URI = "https://speller.yandex.net/services/spellservice.json/checkText";
    public static final String YANDEX_SPELLER_CHECK_TEXTS_URI = "https://speller.yandex.net/services/spellservice.json/checkTexts";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String NO_MISTAKE_RESPONSE = "\\W+";
    public static final String IGNORE_DIGITS = "2";
    public static final String FIND_REPEAT_WORDS = "8";

    public enum Languages {
        RU("ru"), UK("uk"), EN("en");

        String languageCode;

        private Languages(String lang) {
            this.languageCode = lang;
        }
    }
}
