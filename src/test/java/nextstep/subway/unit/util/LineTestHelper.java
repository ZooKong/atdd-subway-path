package nextstep.subway.unit.util;

import nextstep.subway.domain.Line;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Station;
import nextstep.subway.unit.fixture.LineFixture;
import nextstep.subway.unit.fixture.SectionFixture;
import nextstep.subway.unit.fixture.StationFixture;

public class LineTestHelper {

    public static Line 지하철_노선_및_구간_동시_생성(SectionFixture 구간_픽처) {
        Line 노선 = new Line(구간_픽처.노선_이름(), 구간_픽처.노선_색상());
        노선.addSection(
                구간_생성(노선, 구간_픽처)
        );
        return 노선;
    }

    public static Line 지하철_노선_생성(LineFixture 노선_픽처) {
        return new Line(노선_픽처.이름(), 노선_픽처.색상());
    }

    public static Section 지하철_노선에_구간_추가(Line 노선, SectionFixture 구간_픽처) {
        var 구간 = 구간_생성(노선, 구간_픽처);
        노선.addSection(
                구간
        );
        return 구간;
    }

    public static Section 구간_생성(Line 노선, SectionFixture 구간_픽처) {
        return new Section(
                노선,
                new Station(구간_픽처.상행역().이름()),
                new Station(구간_픽처.하행역().이름()),
                구간_픽처.경유역_수()
        );
    }

    public static Station 지하철역_생성(StationFixture 지하철역_픽처) {
        return new Station(지하철역_픽처.이름());
    }

}