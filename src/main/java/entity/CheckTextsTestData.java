package entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class CheckTextsTestData {
    public int wIndex;
    public int sIndex;
    public int code;
    public String[] texts;
    public List<String> rightWords;

    public static final CheckTextsTestData NO_MISTAKES_TEXTS =
            new CheckTextsTestData(0, 0, 1, new String[]{"Every time you register for an online service",
                                                                            "send an email",
                                                                            "download a video or \n upload a photo - your digital footprint can be revealed"}, null);
    public static final CheckTextsTestData TEXTS_WITH_MISTAKES =
            new CheckTextsTestData(0, 0, 1, new String[]{"Every tiime you register for an online service",
                    "send an emil",
                    "download a video or \nuploed a photo - your digital footprint can be revealed"},
                    Arrays.asList("time", "email", "upload"));

}
