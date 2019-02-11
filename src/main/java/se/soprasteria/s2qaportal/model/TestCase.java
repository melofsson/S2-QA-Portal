package se.soprasteria.s2qaportal.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class TestCase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_id")
    private Long id;
    private String shortName;
    private String logMessage;
    private String platform;
    private String fullName;

    public TestCase(String name, String logMessage, String platform){
        this.shortName = name;
        this.logMessage = logMessage;
        this.platform = platform;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return Objects.equals(id, testCase.id);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


}
