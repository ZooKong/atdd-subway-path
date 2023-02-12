package nextstep.subway.unit.fixture;

import java.util.HashMap;
import java.util.Map;

public enum StationFixture {
    연신내역,
    충무로역,
    교대역,
    ;

    public Map toMap() {
        Map map = new HashMap();
        map.put("name", this.name());
        return map;
    }

}