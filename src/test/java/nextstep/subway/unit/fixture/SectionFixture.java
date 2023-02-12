package nextstep.subway.unit.fixture;


import static nextstep.subway.unit.fixture.LineFixture.*;
import static nextstep.subway.unit.fixture.StationFixture.*;

public enum SectionFixture {
    지하철_3호선_연신내_충무로(지하철3호선, 연신내역, 충무로역, 10),
    지하철_3호선_충무로_교대(지하철3호선, 충무로역, 교대역, 9),
    ;

    SectionFixture(LineFixture 노선, StationFixture 상행역, StationFixture 하행역, int 경유역_수) {
        this.노선 = 노선;
        this.상행역 = 상행역;
        this.하행역 = 하행역;
        this.경유역_수 = 경유역_수;
    }

    private LineFixture 노선;
    private StationFixture 상행역;
    private StationFixture 하행역;
    private int 경유역_수;


    public String 노선_이름() {
        return 노선.이름();
    }

    public String 노선_색상() {
        return 노선.색상();
    }

    public StationFixture 상행역() {
        return 상행역;
    }

    public StationFixture 하행역() {
        return 하행역;
    }

    public int 경유역_수() {
        return 경유역_수;
    }

}