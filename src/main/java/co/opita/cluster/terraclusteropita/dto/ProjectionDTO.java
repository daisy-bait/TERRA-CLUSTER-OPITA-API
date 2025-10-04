package co.opita.cluster.terraclusteropita.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectionDTO {

    private String layerIdentifier;
    private String time;
    private String tileMatrixSet;
    private String tileMatrix;
    private String tileRow;
    private String tileCol;
    private String format;

}