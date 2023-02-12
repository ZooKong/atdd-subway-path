package nextstep.subway.unit;

import nextstep.subway.domain.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.unit.util.LineTestHelper.*;
import static nextstep.subway.unit.fixture.SectionFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 지하철 노선 클래스에 대한 단위 테스트
 * {@link nextstep.subway.domain.Line}
 */
class LineTest {

    Line 지하철_3호선;

    @BeforeEach
    void init() {
        지하철_3호선 = 지하철_노선_및_구간_동시_생성(지하철_3호선_연신내_충무로);
    }

    /**
     * When 지하철 노선에 구간을 추가하면
     * Then 노선에 추가한 구간이 존재한다.
     */
    @DisplayName("지하철 노선에 구간을 추가한다.")
    @Test
    void addSection() {
        // when
        var 추가한_구간 = 지하철_노선에_구간_추가(지하철_3호선, 지하철_3호선_충무로_교대);

        // then
        assertThat(지하철_3호선.getSections()).contains(추가한_구간);
    }

    /**
     * Given 새로운 지하철 역을 갖고 있는 구간을 노선에 추가하고
     * When 노선의 지하철 역 목록을 조회하면
     * Then 새롭게 추가한 지하철 역이 존재한다.
     */
    @DisplayName("지하철 노선에서 지하철 역을 조회한다.")
    @Test
    void getStations() {
        // given
        var 추가한_구간 = 지하철_노선에_구간_추가(지하철_3호선, 지하철_3호선_충무로_교대);

        // when
        var 지하철역_목록 = 지하철_3호선.getStations();

        assertAll("추가한 구간의 지하철 역에 대해 노선에 존재 여부 검증",
                () -> assertThat(지하철역_목록).contains(추가한_구간.getUpStation()),
                () -> assertThat(지하철역_목록).contains(추가한_구간.getDownStation())
        );
    }

    /**
     * Given 새로운 구간을 노선에 추가하고
     * When 추가한 노선을 삭제하면
     * Then 노선에 해당 구간이 존재하지 않는다.
     */
    @DisplayName("지하철 노선에 구간을 삭제한다.")
    @Test
    void removeSection() {
        // given
        var 추가한_구간 = 지하철_노선에_구간_추가(지하철_3호선, 지하철_3호선_충무로_교대);

        // when
        var 삭제한_구간 = 지하철_3호선.removeSection(추가한_구간);

        // then
        assertThat(지하철_3호선.getSections()).doesNotContain(삭제한_구간);
    }

}
