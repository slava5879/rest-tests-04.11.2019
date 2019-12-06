package data;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag {
    @Builder.Default
    private long id = 0;
    @Builder.Default
    private String name = "animals";

}
