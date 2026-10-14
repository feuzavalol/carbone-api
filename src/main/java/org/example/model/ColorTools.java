package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ColorTools {
    public static List<String> staticColors = List.of(new String[]{
            "#000000", "#00FF00", "#0000FF", "#FF0000", "#01FFFE", "#FFA6FE", "#FFDB66",
            "#006401", "#010067", "#95003A", "#007DB5", "#FF00F6", "#FFEEE8", "#774D00",
            "#90FB92", "#0076FF", "#D5FF00", "#FF937E", "#6A826C", "#FF029D", "#FE8900",
            "#7A4782", "#7E2DD2", "#85A900", "#FF0056", "#A42400", "#00AE7E", "#683D3B",
            "#BDC6FF", "#263400", "#BDD393", "#00B917", "#9E008E", "#001544", "#C28C9F",
            "#FF74A3", "#01D0FF", "#004754", "#E56FFE", "#788231", "#0E4CA1", "#91D0CB",
            "#BE9970", "#968AE8", "#BB8800", "#43002C", "#DEFF74", "#00FFC6", "#FFE502",
            "#620E00", "#008F9C", "#98FF52", "#7544B1", "#B500FF", "#00FF78", "#FF6E41",
            "#005F39", "#6B6882", "#5FAD4E", "#A75740", "#A5FFD2", "#FFB167", "#009BFF", "#E85EBE"
    });

    public static java.awt.Color getRandomColor(float seed){
        float hue = (seed + 0.61803398875f) % 1f;
        return java.awt.Color.getHSBColor(hue, 0.85f, 0.95f);
    }

    public static List<String> staticRandomColors(int numberColors){
        int size = staticColors.size();
        if (numberColors > size){
            return staticColors;
        }
        int startingIndex = new java.util.Random().nextInt(0, size);
        if (startingIndex > (startingIndex + numberColors) % size){
            return staticColors.subList((startingIndex + numberColors) % size, (startingIndex + numberColors) % size + numberColors);
        }
        return staticColors.subList(startingIndex, (startingIndex + numberColors) % size);
    }

    public static List<String> getRandomColors(int numberColors) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < numberColors; i++) {
            java.awt.Color color = getRandomColor(new java.util.Random().nextFloat());
            String hex = String.format("#%06X", color.getRGB() & 0xFFFFFF);
            result.add(hex);
        }
        return result;
    }
}
