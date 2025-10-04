package co.opita.cluster.terraclusteropita.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gibs-client", url = "https://gibs.earthdata.nasa.gov/wmts/epsg4326/best")
public interface GibsEarthDataClient {

    @GetMapping("/{layerIdentifier}/default/{time}/{tileMatrixSet}/{tileMatrix}/{tileRow}/{tileCol}.{format}")
    byte[] executeGetGibsGeographicProjection(
            @PathVariable String layerIdentifier,
            @PathVariable String time,
            @PathVariable String tileMatrixSet,
            @PathVariable String tileMatrix,
            @PathVariable String tileRow,
            @PathVariable String tileCol,
            @PathVariable String format
    );

}