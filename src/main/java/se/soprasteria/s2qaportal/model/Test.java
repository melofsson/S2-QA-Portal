package se.soprasteria.s2qaportal.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    private Long id;
    private String name;
    private String logMessage;
    private String platform;


}
