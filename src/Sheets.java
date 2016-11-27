import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.gdata.client.spreadsheet.SpreadsheetService;
//import com.google.gdata.data.spreadsheet.*;
//import com.google.gdata.util.ServiceException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class Sheets {
    private List<String> scopes = Arrays.asList("https://spreadsheets.google.com/feeds",
            "https://www.googleapis.com/auth/drive", "https://www.googleapis.com/auth/drive", "https://docs.google.com/feeds");
    private String jsonKeyPath = "/Users/Joseph/Documents/HIT/Altruism/Altruism-cf87e55ab3f8.json";


//    private List<ListEntry> rows;
//    private WorksheetEntry worksheet;
//    private SpreadsheetEntry spreadsheet;
//    private SpreadsheetService sheets;

    private Sheets() {
        try {
            GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(jsonKeyPath))
                    .createScoped(scopes);


//            sheets = new SpreadsheetService("Altruism-1308");
//            sheets.setOAuth2Credentials(credential);


            URL spreadsheetFeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
//            spreadsheet = sheets.getFeed(spreadsheetFeedUrl, SpreadsheetFeed.class).getEntries().get(0);


//            WorksheetFeed worksheetFeed = sheets.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
//            List<WorksheetEntry> worksheetEntries = worksheetFeed.getEntries();
//            worksheet = worksheetEntries.get(0);
//            worksheet.setColCount(50);
//            worksheet.setRowCount(100);
//            worksheet.update();
//
//            ListFeed listFeed = sheets.getFeed(worksheet.getListFeedUrl(), ListFeed.class);
//            rows = listFeed.getEntries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public Optional<CellEntry> getCell(String cellName) {
//        CellFeed cellFeed = null;
//        try {
//            URL cellFeedURI = new URI(getWorksheet().getCellFeedUrl().toString()  + "?max-row=20&max-col=3").toURL();
//            cellFeed = getSheets().getFeed(cellFeedURI, CellFeed.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        List<CellEntry> cells = cellFeed.getEntries();
//
//        for (CellEntry cell : cells) {
//            if (cell.getTitle().getPlainText().equals(cellName)) return Optional.of(cell);
//        }
//
//        return Optional.empty();
//    }

    private static Sheets instance = new Sheets();
    public static Sheets get() {
        return instance;
    }


//    public SpreadsheetService getSheets() {
//        return sheets;
//    }
//
//    public SpreadsheetEntry getSpreadsheet() {
//        return spreadsheet;
//    }
//
//    public WorksheetEntry getWorksheet() {
//        return worksheet;
//    }
//
//    public List<ListEntry> getRows() {
//        return rows;
//    }
}