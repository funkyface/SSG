package kaptainwutax.ssg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FixInput {

    public final List<StructureSeedGroup> STRUCTURE_SEED_GROUPS = new ArrayList<>();

    public void readFromFile(String filePath) {
        getInputLines(filePath)
                .forEach(lineString -> {
                    String[] line = lineString.split(",");
                    addWorldSeed(line[1], line[0]);
                });
    }

    public void addSsgInputLines(String filePath) {
        getInputLines(filePath)
                .forEach(lineString -> {
                    String[] line = lineString.split(" ");
                    getStructureSeedGroup(line[0]).ifPresent(group -> group.inputLine = lineString);
                });
    }

    public void addWorldSeed(String structureSeed, String worldSeed) {
        getStructureSeedGroupOrCreateNew(structureSeed).worldSeeds.add(worldSeed);
    }

    public Optional<StructureSeedGroup> getStructureSeedGroup(String structureSeed) {
        return STRUCTURE_SEED_GROUPS.stream()
                .filter(group -> group.structureSeed.equals(structureSeed))
                .findFirst();
    }

    public StructureSeedGroup getStructureSeedGroupOrCreateNew(String structureSeed) {
        Optional<StructureSeedGroup> optionalStructureSeedGroup = getStructureSeedGroup(structureSeed);
        if (optionalStructureSeedGroup.isPresent()) {
            return optionalStructureSeedGroup.get();
        } else {
            StructureSeedGroup structureSeedGroup = new StructureSeedGroup(structureSeed);
            STRUCTURE_SEED_GROUPS.add(structureSeedGroup);
            return structureSeedGroup;
        }
    }

    public static class StructureSeedGroup {

        public String structureSeed;
        public String inputLine;
        public List<String> worldSeeds = new ArrayList<>();

        public StructureSeedGroup(String structureSeed) {
            this.structureSeed = structureSeed;
        }

    }

    public static Stream<String> getInputLines(String filePath) {
        try {
            return Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
