package co.opita.cluster.terraclusteropita.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gibs-client",
        url = "https://gibs.earthdata.nasa.gov/wms/epsg3857/best/wms.cgi"
                + "?SERVICE=WMS&REQUEST=GetMap&VERSION=1.1.1&SRS=EPSG:3857")
public interface GibsEarthDataClient {

    @GetMapping
    byte[] executeGetGibsGeographicProjection(
            @RequestParam(name = "LAYERS") String layerIdentifiers,
            @RequestParam(name = "FORMAT") String format,
            @RequestParam(name = "TIME") String time,
            @RequestParam(name = "BBOX") String bbox,
            @RequestParam(name = "WIDTH") String width,
            @RequestParam(name = "HEIGHT") String height
    );

}