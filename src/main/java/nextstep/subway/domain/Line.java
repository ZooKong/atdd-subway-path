package nextstep.subway.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public Line() {
    }

    public Line(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Section addSection(Section section) {
        List<Section> sections = getSections();
        sections.add(section);
        return section;
    }

    public Section removeSection(Section section) {
        List<Section> sections = getSections();
        sections.remove(section);
        return section;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Station> getStations() {
        return getSections().stream().flatMap(
                section -> Stream.of(section.getUpStation(), section.getDownStation())
        ).distinct().collect(Collectors.toList());
    }

}
