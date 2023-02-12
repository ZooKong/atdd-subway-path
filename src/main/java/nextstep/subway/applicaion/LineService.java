package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.*;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class LineService {
    private LineRepository lineRepository;
    private StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    public List<LineResponse> showLines() {
        return lineRepository.findAll().stream()
                .map(this::createLineResponse)
                .collect(Collectors.toList());
    }

    public LineResponse findById(Long lineId) {
        return createLineResponse(lineRepository.findById(lineId).orElseThrow(IllegalArgumentException::new));
    }

    @Transactional
    public LineResponse saveLine(LineSaveRequest request) {
        Line line = lineRepository.save(new Line(request.getName(), request.getColor()));

        if (request.canAddSection())
            addSection(line, request);

        return createLineResponse(line);
    }

    @Transactional
    public void updateLine(Long lineId, LineUpdateRequest request) {
        Line line = lineRepository.findById(lineId).orElseThrow(IllegalArgumentException::new);
        line.update(request);
    }

    @Transactional
    public void deleteLine(Long lineId) {
        lineRepository.deleteById(lineId);
    }

    @Transactional
    public void addSection(Long lineId, SectionAddRequest request) {
        Station upStation = stationService.findById(request.getUpStationId());
        Station downStation = stationService.findById(request.getDownStationId());
        Line line = lineRepository.findById(lineId).orElseThrow(IllegalArgumentException::new);
        line.addSection(
                new Section(line, upStation, downStation, request.getDistance())
        );
    }

    @Transactional
    public void deleteSection(Long lineId, Long stationId) {
        Line line = lineRepository.findById(lineId).orElseThrow(IllegalArgumentException::new);
        Station station = stationService.findById(stationId);
        line.removeStation(station);
    }

    private void addSection(Line line, LineSaveRequest request) {
        addSection(
                line.getId(),
                new SectionAddRequest(
                        request.getUpStationId(),
                        request.getDownStationId(),
                        request.getDistance()
                )
        );
    }

    private LineResponse createLineResponse(Line line) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                createStationResponses(line)
        );
    }

    private List<StationResponse> createStationResponses(Line line) {
        return line.getStations()
                .stream()
                .map(it -> stationService.createStationResponse(it))
                .collect(Collectors.toList());
    }

}