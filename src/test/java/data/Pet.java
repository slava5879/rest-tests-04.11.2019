package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Pet(long id, String categoryName, String name, String status) {
        this.id = id;
        this.category = new Category(0, categoryName);
        this.name = name;
        this.status = status;
        this.tags = new ArrayList<Tag>();
        this.photoUrls = new ArrayList<String>();
    }


}

