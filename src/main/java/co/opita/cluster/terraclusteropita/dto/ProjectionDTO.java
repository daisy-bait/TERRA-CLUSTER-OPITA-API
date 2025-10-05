package co.opita.cluster.terraclusteropita.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectionDTO {

    private String layerIdentifiers;
    private String format;
    private String startDate;
    private String endDate;
    private String bbox;
    private String width;
    private String height;

}