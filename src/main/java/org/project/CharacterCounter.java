package org.project;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;

public class CharacterCounter {

    private final JFrame mainFrame = new JFrame("Кількість символів");

    private final JPanel panel = new JPanel();
    private final SpringLayout layout = new SpringLayout();

    private final JLabel pathToFileLabel = new JLabel("Шлях до файлу:");
    private final JTextField pathToFileTextField = new JTextField(20);
    private final JButton pathToFileButton = new JButton("Обзор");

    private final JFileChooser fileChooser = new JFileChooser();
    private final FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Тестові файли", "txt", "text");

    private final JLabel countOfCharactersDescriptionLabel =
            new JLabel("Кількість всіх символів = ");
    private final JLabel countOfCharactersLabel = new JLabel("0");

    private final JLabel characterLabel = new JLabel("Вкажіть символ:");
    private final JTextField characterTextField = new JTextField(5);

    private final JLabel countOfCharacterDescriptionLabel =
            new JLabel("Кількість заданого символа в файлі:");
    private final JLabel countOfCharacterLabel = new JLabel("0");


    public void createAndShowGUI() {
        mainFrame.setSize(400, 240);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(layout);

        fileChooser.setFileFilter(filter);

        pathToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fileChooser.showOpenDialog(mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    // Вивести шлях до файлу
                    pathToFileTextField.setText(file.getAbsolutePath());

                    outputResult(null);
                }
            }
        });

        pathToFileTextField.setEditable(false);

        characterTextField.getDocument().addDocumentListener(new DocumentListener() {
            private void countChar() {
                if (!characterTextField.getText().isEmpty()) {
                    Character filterCh = characterTextField.getText().charAt(0);

                    outputResult(filterCh);
                } else {
                    countOfCharacterLabel.setText("0");
                }
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                countChar();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                countChar();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                countChar();
            }
        });

        panel.add(pathToFileLabel);
        panel.add(pathToFileTextField);
        panel.add(pathToFileButton);

        panel.add(countOfCharactersDescriptionLabel);
        panel.add(countOfCharactersLabel);

        panel.add(characterLabel);
        panel.add(characterTextField);

        panel.add(countOfCharacterDescriptionLabel);
        panel.add(countOfCharacterLabel);

        mainFrame.add(panel);

        layout.putConstraint(SpringLayout.WEST, pathToFileLabel, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, pathToFileLabel, 5, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, pathToFileTextField, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, pathToFileTextField, 5, SpringLayout.SOUTH, pathToFileLabel);

        layout.putConstraint(SpringLayout.WEST, pathToFileButton, 5, SpringLayout.EAST, pathToFileTextField);
        layout.putConstraint(SpringLayout.NORTH, pathToFileButton, 0, SpringLayout.NORTH, pathToFileTextField);

        layout.putConstraint(SpringLayout.WEST, countOfCharactersDescriptionLabel, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharactersDescriptionLabel, 10, SpringLayout.SOUTH, pathToFileTextField);

        layout.putConstraint(SpringLayout.WEST, countOfCharactersLabel, 5, SpringLayout.EAST, countOfCharactersDescriptionLabel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharactersLabel, 0, SpringLayout.NORTH, countOfCharactersDescriptionLabel);

        layout.putConstraint(SpringLayout.WEST, characterLabel, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, characterLabel, 10, SpringLayout.SOUTH, countOfCharactersDescriptionLabel);

        layout.putConstraint(SpringLayout.WEST, characterTextField, 5, SpringLayout.EAST, characterLabel);
        layout.putConstraint(SpringLayout.NORTH, characterTextField, 0, SpringLayout.NORTH, characterLabel);

        layout.putConstraint(SpringLayout.WEST, countOfCharacterDescriptionLabel, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharacterDescriptionLabel, 10, SpringLayout.SOUTH, characterTextField);

        layout.putConstraint(SpringLayout.WEST, countOfCharacterLabel, 5, SpringLayout.EAST, countOfCharacterDescriptionLabel);
        layout.putConstraint(SpringLayout.NORTH, countOfCharacterLabel, 0, SpringLayout.NORTH, countOfCharacterDescriptionLabel);

        mainFrame.setVisible(true);
    }

    public static Integer countCharactersInFile(File file, Character filterCh) throws IOException {
        int result = 0;

        Charset encoding = Charset.defaultCharset(); // можна уточнити кодування, якщо потрібно
        try (InputStream in = new FileInputStream(file);
             Reader reader = new InputStreamReader(in, encoding);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            int r;
            while ((r = bufferedReader.read()) != -1) {
                char ch = (char) r;

                if (filterCh == null) {
                    result++;
                } else if (filterCh == ch) {
                    result++;
                }
            }
        }

        return result;
    }

    private void outputResult(Character filterCh) {
        File file = fileChooser.getSelectedFile();
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Будь ласка, виберіть файл.");
            return;
        }

        Integer cnt = 0;
        Integer ccc= 0;
        try {
            cnt = countCharactersInFile(file, filterCh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        if (filterCh == null) {
            countOfCharactersLabel.setText(cnt.toString());
        } else {
            countOfCharacterLabel.setText(cnt.toString());
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CharacterCounter characterCounter = new CharacterCounter();
                characterCounter.createAndShowGUI();
            }
        });
    }
}
