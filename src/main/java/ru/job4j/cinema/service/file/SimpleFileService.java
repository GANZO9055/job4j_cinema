package ru.job4j.cinema.service.file;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.file.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public Optional<FileDto> findById(int id) {
        var file = fileRepository.findById(id);
        if (file.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFile(file.get().getPath());
        return Optional.of(new FileDto(file.get().getName(), content));
    }

    public byte[] readFile(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
