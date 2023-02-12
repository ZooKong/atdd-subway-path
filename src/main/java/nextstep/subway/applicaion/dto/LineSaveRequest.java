package nextstep.subway.applicaion.dto;

public class LineSaveRequest {
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private int distance;

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public boolean canAddSection() {

        if (notExistsUpStationId()) {
            return false;
        }

        if (notExistDownStationId()) {
            return false;
        }

        if (notExistsDistance()) {
            return false;
        }

        return true;
    }

    private boolean notExistsUpStationId() {
        return getUpStationId() == null;
    }

    private boolean notExistDownStationId() {
        return getDownStationId() == null;

    }

    private boolean notExistsDistance() {
        return getDistance() == 0;
    }

}