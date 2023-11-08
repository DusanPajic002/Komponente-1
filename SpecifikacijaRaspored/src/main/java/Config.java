import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
    private Integer index;
    private String custom;
    private String original;
    public Config(Integer index, String custom, String original) {
        this.index = index;
        this.custom = custom;
        this.original = original;
    }

    @Override
    public String toString() {
        return " index: " + index + " custom: " + custom + " original: " + original + "\n";
    }
}