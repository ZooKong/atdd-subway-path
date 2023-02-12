package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.subway.unit.util.LineTestHelper.*;
import static nextstep.subway.unit.fixture.LineFixture.*;
import static nextstep.subway.unit.fixture.StationFixture.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class LineServiceTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineService lineService;

    @BeforeEach
    void setUp() {
        // 지하철_3호선 = 지하철_노선_및_구간_동시_생성(지하철_3호선_연신내_충무로);
    }

    /**
     * Given repository를 통해 지하철역, 노선을 등록한다.
     * When 노선(Line) 서비스를 통해 구간 등록을 호출한다.
     * Then 노선(Line) 서비스를 통해 조회 시, 등록된 구간을 확인할 수 있다.
     */
    @Test
    void addSection() {
        // given
        var 상행_연신내역 = stationRepository.save(지하철역_생성(연신내역));
        var 하행_충무로역 = stationRepository.save(지하철역_생성(충무로역));
        var 지하철_3호선 = lineRepository.save(지하철_노선_생성(지하철3호선));

        SectionRequest 구간_저장_요청 = new SectionRequest(상행_연신내역.getId(), 하행_충무로역.getId(), 10);

        // when
        lineService.addSection(지하철_3호선.getId(), 구간_저장_요청);

        // then
        var 노선_조회_응답 = lineRepository.findById(지하철_3호선.getId()).get();
        var 구간_목록 = 노선_조회_응답.getSections();
        var 지하철역_목록 = 노선_조회_응답.getStations();

        assertAll(
                () -> assertThat(구간_목록).hasSize(1),
                () -> assertThat(지하철역_목록).hasSize(2),
                () -> assertThat(지하철역_목록).contains(상행_연신내역, 하행_충무로역)
        );
    }

}