package com.codegenome.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectScanner {

    public List<Path> scanJavaFiles(Path projectPath) throws IOException {

        try (Stream<Path> paths = Files.walk(projectPath)) {

            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        }
    }
}