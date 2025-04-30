import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;


public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("sample_input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("sample_output2.txt"))) {

            String line;
            int currentProblem = 0;
            StringBuilder problemInput = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.matches("\\d+")) {
                    if (currentProblem != 0) {
                        processProblem(currentProblem, problemInput.toString(), writer);
                        problemInput.setLength(0);
                    }
                    currentProblem = Integer.parseInt(line);
                } else if (line.equals("end")) {
                    processProblem(currentProblem, problemInput.toString(), writer);
                    problemInput.setLength(0);
                    currentProblem = 0;
                } else {
                    problemInput.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processProblem(int problemNumber, String input, BufferedWriter writer) throws IOException {
        String[] lines = input.split("\n");
        writer.write(problemNumber + "\n");

        switch (problemNumber) {
            case 1:
                solveProblem1(lines, writer);
                break;
            case 2:
                solveProblem2(lines, writer);
                break;
            case 3:
                solveProblem3(lines, writer);
                break;
            case 4:
                solveProblem4(lines, writer);
                break;
            case 5:
                solveProblem5(lines, writer);
                break;
            case 6:
                solveProblem6(lines, writer);
                break;
            case 7:
                solveProblem7(lines, writer);
                break;
            case 8:
                solveProblem8(lines, writer);
                break;
            case 9:
                solveProblem9(lines, writer);
                break;
            case 10:
                solveProblem10(lines, writer);
                break;
            case 11:
                solveProblem11(lines, writer);
                break;
            case 12:
                solveProblem12(lines, writer);
                break;
            case 13:
                solveProblem13(lines);
                break;
            default:
                break;
        }
        writer.write("x\n");
    }

    private static void solveProblem1(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^([0-9A-Fa-f]{2}[:-]){5}[0-9A-Fa-f]{2}$|^[0-9A-Fa-f]{12}$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem2(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^(b|ba)+b*$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem3(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^(0?[1-9]|[12][0-9]|3[01])[/-](0?[1-9]|1[0-2])[/-]\\d{4}$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem4(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem5(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem6(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "(?i)^([^b]*b[^b]*b[^b]*)*[^b]*b[^b]*$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem7(String[] lines, BufferedWriter writer) throws IOException {
        for (String line : lines) {
            line = line.toLowerCase(); // Ensure case-insensitivity
            Pattern pattern = Pattern.compile("(?=([ab]+))"); // Find overlapping substrings
            Matcher matcher = pattern.matcher(line);
            List<String> validSubstrings = new ArrayList<>();
            List<int[]> indices = new ArrayList<>();

            while (matcher.find()) {
                String candidate = matcher.group(1);
                // Check each candidate substring
                for (int start = 0; start < candidate.length(); start++) {
                    for (int end = start + 1; end <= candidate.length(); end++) {
                        String substr = candidate.substring(start, end);
                        long aCount = substr.chars().filter(c -> c == 'a').count();
                        long bCount = substr.chars().filter(c -> c == 'b').count();
                        if (aCount % 2 == 1 && bCount % 2 == 1) {
                            // Calculate original indices in the input line
                            int globalStart = matcher.start(1) + start;
                            int globalEnd = matcher.start(1) + end - 1;
                            validSubstrings.add(substr);
                            indices.add(new int[]{globalStart, globalEnd});
                        }
                    }
                }
            }

            // Remove duplicates (same substring at same indices)
            Set<String> uniqueEntries = new LinkedHashSet<>();
            for (int i = 0; i < validSubstrings.size(); i++) {
                uniqueEntries.add(validSubstrings.get(i) + " [" + indices.get(i)[0] + ", " + indices.get(i)[1] + "]");
            }

            // Write output
            writer.write("**" + line + "**\n");
            writer.write("number of matched substrings: " + uniqueEntries.size() + "\n");
            if (!uniqueEntries.isEmpty()) {
                for (String entry : uniqueEntries) {
                    writer.write(entry + "\n");
                }
            }
            writer.write("----------------------------\n");
        }
    }

    private static void solveProblem8(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "\\b\\w{5}(\\w{5})*\\b"; // Matches words with length = 5, 10, 15, etc.
        for (String line : lines) {
            writer.write("**" + line + "**\n"); // Wrap input line in ** **
            Matcher matcher = Pattern.compile(regex).matcher(line);
            int count = 0;
            StringBuilder matches = new StringBuilder();

            while (matcher.find()) {
                count++;
                // Format: "word [start, end]" (end index is exclusive, so subtract 1)
                matches.append(matcher.group())
                        .append(" [")
                        .append(matcher.start())
                        .append(", ")
                        .append(matcher.end()) // Adjusted end index
                        .append("]\n");
            }

            if (count > 0) {
                writer.write("Number of matched words: " + count + "\n");
                writer.write(matches.toString());
            } else {
                writer.write("No word matches\n"); // Skip count line if no matches
            }
            writer.write("---------------------------------------------\n"); // Separator
        }
    }

    private static void solveProblem9(String[] lines, BufferedWriter writer) throws IOException {
        Pattern logPattern = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] (.*)");
        for (String line : lines) {
            Matcher matcher = logPattern.matcher(line);
            if (matcher.find()) {
                writer.write("Timestamp: " + matcher.group(1) + ", Level: " + matcher.group(2) + ", Message: " + matcher.group(3) + "\n");
            }
        }
    }

    private static void solveProblem10(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^[^=]+=[^=]+$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem11(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "(?i).*\\bthe\\b.*";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem12(String[] lines, BufferedWriter writer) throws IOException {
        String regex = "^([A-Za-z]\\d)+[A-Za-z]?$|^(\\d[A-Za-z])+\\d?$";
        for (String line : lines) {
            writer.write(line.matches(regex) ? "valid\n" : "invalid\n");
        }
    }

    private static void solveProblem13(String[] inputLines) {
        if (inputLines.length < 1) return;
        String filePath = inputLines[0].trim();

        // Define regex patterns for sensitive data
        String emailRegex = "\\b[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}\\b";
        String phoneRegex = "\\b(?:\\(\\+20\\)|\\+20|0)?1[0125]\\d{8}\\b";
        String addressRegex = "\\b\\d+[A-Za-z]*(?:\\s+[A-Za-z]+)+(?:,\\s*[A-Za-z\\s-]+){2,},\\s*(?:\\d{5},\\s*)?Egypt\\b";
        String localAccountRegex = "\\b\\d{4}(?:\\s?\\d{4}){3}\\d{1}\\b";
        String ibanRegex = "\\bEG\\d{2}(?:\\s?\\d{4}){6}(?:\\s?\\d{1,2})?\\b";
        String swiftRegex = "\\b[A-Z]{6}[A-Z0-9]{2}(?:[A-Z0-9]{3})?\\b";
        String nationalIdRegex = "\\b\\d{14}\\b";

        // Combine all regex patterns
        String combinedRegex = String.join("|",
                emailRegex, phoneRegex, addressRegex,
                localAccountRegex, ibanRegex, swiftRegex, nationalIdRegex
        );

        Pattern pattern = Pattern.compile(combinedRegex, Pattern.CASE_INSENSITIVE);

        try {
            List<String> fileLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            List<String> sanitizedLines = new ArrayList<>();

            for (String line : fileLines) {
                Matcher matcher = pattern.matcher(line);
                StringBuffer sanitizedLine = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(sanitizedLine, "*****");
                }
                matcher.appendTail(sanitizedLine);
                sanitizedLines.add(sanitizedLine.toString());
            }

            // Overwrite the original file with sanitized content
            Files.write(Paths.get(filePath), sanitizedLines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}