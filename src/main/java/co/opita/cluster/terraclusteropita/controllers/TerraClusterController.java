package co.opita.cluster.terraclusteropita.controllers;

import co.opita.cluster.terraclusteropita.clients.GibsEarthDataClient;
import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gibs")
public class TerraClusterController {

    private final GibsEarthDataClient gibsClient;

    @GetMapping("/projection")
    public ResponseEntity<List<String>> getProjection(@RequestBody ProjectionDTO projection) {

        List<String> timelapse = repeatPetitionPerTimestamp(projection);
        List<String> response = new ArrayList<>();
        timelapse.stream().forEach(timestamp -> {
                    response.add(Base64.getEncoder().encodeToString(callGibsClient(projection, timestamp)));
                    System.out.println("Iteration " + response.size());
                }
        );

        System.out.println(response);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                response
        );
    }

    private List<String> repeatPetitionPerTimestamp(ProjectionDTO projection) {

        List<String> timestamps = new ArrayList<>();

        String[] startDateTrimmed = projection.getStartDate().split("-");
        String[] endDateTrimmed = projection.getEndDate().split("-");

        int startYear = Integer.parseInt(startDateTrimmed[0]);
        int endYear = Integer.parseInt(endDateTrimmed[0]);
        int startMonth = Integer.parseInt(startDateTrimmed[1]);
        int endMonth = Integer.parseInt(endDateTrimmed[1]);
        int day = Integer.parseInt(endDateTrimmed[2]);

        int currentYear = startYear;
        int currentMonth = startMonth;

        while (currentYear <= endYear) {
            while (currentMonth <= 12) {
                if (currentYear == endYear && currentMonth > endMonth) {
                    break;
                }
                String month = (currentMonth < 10) ? "0" + currentMonth : "" + currentMonth;
                timestamps.add(String.format("%d-%s-%d", currentYear, month, day));
                currentMonth++;
            }
            currentMonth = 1;
            currentYear++;
        }

        System.out.println(timestamps);
        return timestamps;
    }

    private byte[] callGibsClient(ProjectionDTO projection, String timestamp) {
        return gibsClient.executeGetGibsGeographicProjection(
                projection.getLayerIdentifiers(),
                projection.getFormat(),
                timestamp,
                projection.getBbox(),
                projection.getWidth(),
                projection.getHeight()
        );
    }

}