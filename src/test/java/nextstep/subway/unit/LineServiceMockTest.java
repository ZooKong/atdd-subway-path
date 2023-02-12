package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.StationService;
import nextstep.subway.applicaion.dto.SectionAddRequest;
import nextstep.subway.domain.LineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static nextstep.subway.unit.fixture.LineFixture.지하철3호선;
import static nextstep.subway.unit.fixture.StationFixture.연신내역;
import static nextstep.subway.unit.fixture.StationFixture.충무로역;
import static nextstep.subway.unit.util.LineTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

/**
 * Mock을 사용한 노선 서비스 테스트
 * {@link LineService}
 */
@ExtendWith(MockitoExtension.class)
public class LineServiceMockTest {
    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationService stationService;

    private LineService lineService;

    @BeforeEach
    void setUp() {
        lineService = new LineService(lineRepository, stationService);
    }

    /**
     * Given 지하철역과 노선을 등록한다.
     * When 노선(Line) 서비스를 통해 구간 등록을 호출한다.
     * Then 노선(Line) 서비스를 통해 조회 시, 등록된 구간을 확인할 수 있다.
     */
    @Test
    void addSection() {
        // given
        var 상행_연신내역 = 지하철역_생성(1l, 연신내역);
        var 하행_충무로역 = 지하철역_생성(2l, 충무로역);
        var 지하철_3호선 = 지하철_노선_생성(1l, 지하철3호선);

        given(stationService.findById(상행_연신내역.getId())).willReturn(상행_연신내역);
        given(stationService.findById(하행_충무로역.getId())).willReturn(하행_충무로역);
        given(lineRepository.findById(지하철_3호선.getId())).willReturn(
                Optional.of(지하철_3호선)
        );

        SectionAddRequest 구간_저장_요청 = new SectionAddRequest(상행_연신내역.getId(), 하행_충무로역.getId(), 10);

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