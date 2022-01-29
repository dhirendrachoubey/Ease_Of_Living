package in.nic.ease_of_living.supports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class WatermarkPageEvent extends PdfPageEventHelper {

    private String strWatermark = null;

    public WatermarkPageEvent(String strWatermark) {
        this.strWatermark = strWatermark;
    }

    Font FONT = new Font(Font.FontFamily.HELVETICA, 92, Font.BOLD, new GrayColor(0.85f));

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Phrase(strWatermark, FONT),
                297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : 45);
    }
}
