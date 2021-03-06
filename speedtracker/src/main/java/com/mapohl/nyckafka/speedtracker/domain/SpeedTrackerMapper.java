package com.mapohl.nyckafka.speedtracker.domain;

import com.google.common.collect.Lists;
import com.mapohl.nyckafka.commonproducer.domain.EntityMapper;

import java.util.List;

public class SpeedTrackerMapper implements EntityMapper<String, SpeedTracker> {

    public static List<LinkPoint> parseLinkPoints(String linkPointStr) {
        int index;
        int nextIndex = -1;

        List<LinkPoint> linkPoints = Lists.newArrayList();
        while (true) {
            index = nextIndex + 1;
            nextIndex = linkPointStr.indexOf(',', index + 1);

            if (nextIndex < 0) {
                break;
            }

            double lat;
            try {
                lat = Double.parseDouble(linkPointStr.substring(index, nextIndex));
            } catch (NumberFormatException e) {
                break;
            }

            index = nextIndex + 1;
            nextIndex = linkPointStr.indexOf(' ', index + 1);

            if (nextIndex < 0) {
                nextIndex = linkPointStr.length();
            }

            double lon;
            try {
                lon = Double.parseDouble(linkPointStr.substring(index, nextIndex));
            } catch (NumberFormatException e) {
                break;
            }

            linkPoints.add(new LinkPoint(lat, lon));
        }

        return linkPoints;
    }

    @Override
    public List<SpeedTracker> map(String line) {
        int firstQuote = line.indexOf('"');
        int secondQuote = line.indexOf('"', firstQuote + 1);

        List<LinkPoint> linkPoints = parseLinkPoints(line.substring(firstQuote + 1, secondQuote));
        String[] precedingValues = line.substring(0, firstQuote - 1).split(",");
        String[] succeedingValues = line.substring(secondQuote + 1).split(",");

        return Lists.newArrayList(SpeedTracker.builder()
                .speed(Double.parseDouble(precedingValues[1]))
                .travelTimeInSeconds(Integer.parseInt(precedingValues[2]))
                .creationTimeStr(precedingValues[4])
                .linkId(Integer.parseInt(precedingValues[5]))
                .linkPoints(linkPoints)
                .borough(succeedingValues[5])
                .description(succeedingValues[6])
                .build());
    }
}
