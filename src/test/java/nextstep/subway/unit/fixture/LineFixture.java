package nextstep.subway.unit.fixture;


public enum LineFixture {
    지하철3호선("3호선", "bg-yellow-500"),
    지하철2호선("2호선", "bg-green-500");

    LineFixture(String name, String color) {
        this.name = name;
        this.color = color;
    }

    private String name;
    private String color;

    public String 이름() {
        return this.name;
    }

    public String 색상() {
        return this.color;
    }

}