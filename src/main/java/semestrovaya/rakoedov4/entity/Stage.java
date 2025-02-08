package semestrovaya.rakoedov4.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Stage {
    private int stage_id;
    private String name;
    private String description;

    public Stage(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
