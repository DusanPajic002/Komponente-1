import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    public boolean vratiOrginalIndex(Integer index){
        if(index == this.index)
            return true;
        return false;
    }
    public boolean vratiOrginalCustom(String custom){
        if(Objects.equals(this.custom, custom))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return " " + index + " " + custom + " " + original + "\n";
    }
    @Override
    public int hashCode() {
        return Objects.hash(index, custom, original);
    }
}