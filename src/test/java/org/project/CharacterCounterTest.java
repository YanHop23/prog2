package org.project;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCounterTest {

    @Test
    void testCountCharactersInFile_AllCharacters() throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Hello, World!");
        }

        int count = CharacterCounter.countCharactersInFile(tempFile, null);
        assertEquals(13, count, "Повинно бути 13 символів у файлі.");
    }

    @Test
    void testCountCharactersInFile_SpecificCharacter() throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Hello, World!");
        }

        int count = CharacterCounter.countCharactersInFile(tempFile, 'o');
        assertEquals(2, count, "Буква 'o' зустрічається двічі.");
    }

    @Test
    void testCountCharactersInFile_EmptyFile() throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit();

        int count = CharacterCounter.countCharactersInFile(tempFile, null);
        assertEquals(0, count, "Порожній файл повинен мати 0 символів.");
    }

    @Test
    void testCountCharactersInFile_FileNotFound() {
        File fakeFile = new File("non_existent_file.txt");

        Exception exception = assertThrows(IOException.class, () -> {
            CharacterCounter.countCharactersInFile(fakeFile, null);
        });

        assertTrue(exception.getMessage().contains("non_existent_file"));
    }
}
