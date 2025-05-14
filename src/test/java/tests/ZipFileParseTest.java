package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class ZipFileParseTest {
    ClassLoader cl = ZipFileParseTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/file_test_zip.zip"));
        ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("file_test_zip.zip"));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            try (InputStream inputStream = zf.getInputStream(entry)) {
                String entryName = entry.getName();
                if (entryName.contains(".pdf")) {
                    PDF pdf = new PDF(inputStream);
                    assertThat(pdf.text).contains("Lorem ipsum");
                } else if (entryName.contains(".csv")) {
                    try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                        List<String[]> content = reader.readAll();
                        String[] row = content.get(1);
                        assertThat(row[1]).isEqualTo("Dulce");
                        assertThat(row[3]).isEqualTo("Female");
                    }
                } else if (entryName.contains(".xls")) {
                    XLS xls = new XLS(inputStream);
                    assertThat(
                            xls.excel.getSheetAt(0)
                                    .getRow(2)
                                    .getCell(2)
                                    .getStringCellValue()
                    ).isEqualTo("Hashimoto");
                }
            }
        }
    }
}
