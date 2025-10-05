package co.opita.cluster.terraclusteropita.services.impl;

import co.opita.cluster.terraclusteropita.clients.GibsEarthDataClient;
import co.opita.cluster.terraclusteropita.dto.ProjectionDTO;
import co.opita.cluster.terraclusteropita.entities.GibsEntity;
import co.opita.cluster.terraclusteropita.repository.GibsRepository;
import co.opita.cluster.terraclusteropita.services.GibsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GibsServiceImpl implements GibsService {

    private final GibsEarthDataClient gibClient;
    private final GibsRepository gibsRepository;

    @Override
    public byte[] fetchProjection(ProjectionDTO projection) {
        GibsEntity log = new GibsEntity();
        log.setEndpoint("/projection");
        log.setDate(LocalDateTime.now());
        log.setFormat(projection.getFormat());
        log.setResolution(projection.getBbox());
        gibsRepository.save(log);

        return callGibsClient(projection, projection.getStartDate());
    }

    @Override
    public List<String> fetchProjections(ProjectionDTO projection) {
        GibsEntity log = new GibsEntity();
        log.setEndpoint("/projection");
        log.setDate(LocalDateTime.now());
        log.setFormat(projection.getFormat());
        log.setResolution(projection.getBbox());
        gibsRepository.save(log);

        List<String> timelapse = repeatPetitionPerTimestamp(projection);
        List<String> response = new ArrayList<>();
        timelapse.stream().forEach(timestamp -> {
                    response.add(Base64.getEncoder().encodeToString(callGibsClient(projection, timestamp)));
                    System.out.println("Iteration " + response.size());
                }
        );
        return response;
    }

    @Override
    public Optional<GibsEntity> getById(Long id) {
        return gibsRepository.findById(id);
    }

    @Override
    public Long countRequests() {
        return gibsRepository.count();
    }

    public byte[] callGibsClient(ProjectionDTO projection, String timestamp) {
        return gibClient.executeGetGibsGeographicProjection(
                projection.getLayerIdentifiers(),
                projection.getFormat(),
                timestamp,
                projection.getBbox(),
                projection.getWidth(),
                projection.getHeight()
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
                String actualDay = (day < 10) ? "0" + day : "" + day;
                timestamps.add(String.format("%d-%s-%s", currentYear, month, actualDay));
                currentMonth++;
            }
            currentMonth = 1;
            currentYear++;
        }
        System.out.println(timestamps);
        return timestamps;
    }

}