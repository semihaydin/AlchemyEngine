package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tests.NivoColorSchemeTests;

import java.util.List;

public class OrientationDetector {
    private static final Logger logger = LogManager.getLogger(OrientationDetector.class);
    public static Orientation detect(List<Integer> widths,
                                     List<Integer> heights,
                                     int tolerancePx) {
        if (widths == null || heights == null || widths.isEmpty() || heights.isEmpty()) {
            System.out.println("[orientation] Missing data -> UNKNOWN");
            return Orientation.UNKNOWN;
        }

        boolean widthsConstant  = isConstant(widths, tolerancePx);
        boolean heightsConstant = isConstant(heights, tolerancePx);

        if (!heightsConstant && widthsConstant) {
            System.out.println("[orientation] Only heights vary, widths constant -> VERTICAL");
            logger.info("[orientation] Only heights vary, widths constant -> VERTICAL");
            return Orientation.VERTICAL;
        }
        if (!widthsConstant && heightsConstant) {
            System.out.println("[orientation] Only widths vary, heights constant -> HORIZONTAL");
            logger.info("[orientation] Only widths vary, heights constant -> HORIZONTAL");
            return Orientation.HORIZONTAL;
        }

        double avgW = avg(widths);
        double avgH = avg(heights);
        System.out.printf("[orientation] Ambiguous, fallback by averages -> avgW=%.2f, avgH=%.2f%n", avgW, avgH);
        if (avgW > avgH) {
            System.out.println("[orientation] averageWidth > avgHeight -> HORIZONTAL");
            logger.info("[orientation] averageWidth > avgHeight -> HORIZONTAL");
            return Orientation.HORIZONTAL;
        } else if (avgH > avgW) {
            System.out.println("[orientation] avgHeight > avgWeight -> VERTICAL");
            logger.info("[orientation] avgHeight > avgWeight -> VERTICAL");
            return Orientation.VERTICAL;
        } else {
            return Orientation.UNKNOWN;
        }
    }

    private static boolean isConstant(List<Integer> values, int tol) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int v : values) { if (v < min) min = v; if (v > max) max = v; }
        return (max - min) <= tol;
    }

    private static double avg(List<Integer> values) {
        long sum = 0;
        for (int v : values) sum += v;
        return values.isEmpty() ? 0 : (sum * 1.0) / values.size();
    }
}