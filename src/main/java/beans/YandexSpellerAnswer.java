package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class YandexSpellerAnswer {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("pos")
    @Expose
    public Integer position;
    @SerializedName("row")
    @Expose
    public Integer row;
    @SerializedName("col")
    @Expose
    public Integer column;
    @SerializedName("len")
    @Expose
    public Integer length;
    @SerializedName("word")
    @Expose
    public String word;
    @SerializedName("s")
    @Expose
    public List<String> s = new ArrayList<String>();


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("position", position).append("row", row).append("column", column).append("length", length).append("word", word).append("suggestions", s).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(column).append(code).append(s).append(length).append(position).append(row).append(word).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof YandexSpellerAnswer)) {
            return false;
        }
        YandexSpellerAnswer rhs = ((YandexSpellerAnswer) other);
        return new EqualsBuilder().append(column, rhs.column).append(code, rhs.code).append(s, rhs.s).append(length, rhs.length).append(position, rhs.position).append(row, rhs.row).append(word, rhs.word).isEquals();
    }

}
