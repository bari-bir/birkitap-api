package kz.baribir.birkitap.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class DocumentUtil {

    private DocumentUtil() {}

    public static boolean html2pdfMultiPage(String font_path, List<FreemarkerTemplateContent> contents, String pdfFile) {
        try {

            if (contents == null || contents.isEmpty())
                return false;
            OutputStream os = new FileOutputStream(pdfFile);
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(font_path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            SharedContext sharedContext = renderer.getSharedContext();
            B64ImgReplacedElementFactory b64ImgReplacedElementFactory = new B64ImgReplacedElementFactory();
            b64ImgReplacedElementFactory.getBaseURLMap().putAll(contents.get(0).getUrlMap());
            sharedContext.setReplacedElementFactory(b64ImgReplacedElementFactory);

            renderer.setDocumentFromString(contents.get(0).getContent());
            renderer.layout();
            renderer.createPDF(os, false);

            for (int i = 1; i < contents.size(); ++i) {
                b64ImgReplacedElementFactory = new B64ImgReplacedElementFactory();
                b64ImgReplacedElementFactory.getBaseURLMap().putAll(contents.get(i).getUrlMap());
                sharedContext.setReplacedElementFactory(b64ImgReplacedElementFactory);
                renderer.setDocumentFromString(contents.get(i).getContent());
                renderer.layout();
                renderer.writeNextDocument();
            }
            renderer.finishPDF();
            os.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean html2pdf(String font_path, String htmlFile, String pdfFile) {
        try {
            String url = new File(htmlFile).toURI().toURL().toString();
            OutputStream os = new FileOutputStream(pdfFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(font_path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
            os.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean html2pdf(String font_path, String htmlString, OutputStream os, Map<String, Object> baseURLMap) {
        try {

            ITextRenderer renderer = new ITextRenderer();

            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(font_path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            SharedContext sharedContext = renderer.getSharedContext();
            B64ImgReplacedElementFactory b64ImgReplacedElementFactory = new B64ImgReplacedElementFactory();
            b64ImgReplacedElementFactory.getBaseURLMap().putAll(baseURLMap);
            sharedContext.setReplacedElementFactory(b64ImgReplacedElementFactory);
            renderer.setDocumentFromString(htmlString);
            renderer.layout();
            renderer.createPDF(os);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean create_barcode(String content, String file_path) {

        try {

            final int barcode_width = 400;
            final int barcode_height = 110;
            final int canvas_width = 400;
            final int canvas_height = 150;
            final int fontSize = 24;


            Code128Writer barcodeWriter = new Code128Writer();
            BitMatrix bitMatrix = barcodeWriter.encode(content, BarcodeFormat.CODE_128, barcode_width, barcode_height);
            BufferedImage img_barcode = MatrixToImageWriter.toBufferedImage(bitMatrix);
            BufferedImage canvas = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = canvas.getGraphics();

            Font font = new Font(graphics.getFont().toString(), Font.BOLD, fontSize);
            graphics.setFont(font);

            graphics.setColor(new Color(255, 255, 255, 255));
            graphics.fillRect(0, 0, canvas_width, canvas_height);
            graphics.drawImage(img_barcode, 0, 0, barcode_width, barcode_height, null);
            graphics.setColor(new Color(0, 0, 0));
            int strWidth = graphics.getFontMetrics().stringWidth(content);
            graphics.drawString(content, (canvas_width - strWidth) / 2, canvas_height - 10);
            graphics.dispose();

            ImageIO.write(canvas, "png", new File(file_path));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getBarcodeBufferedImage(String content) {
        try {

            final int barcode_width = 400;
            final int barcode_height = 110;
            final int canvas_width = 400;
            final int canvas_height = 150;
            final int fontSize = 24;


            Code128Writer barcodeWriter = new Code128Writer();
            BitMatrix bitMatrix = barcodeWriter.encode(content, BarcodeFormat.CODE_128, barcode_width, barcode_height);
            BufferedImage img_barcode = MatrixToImageWriter.toBufferedImage(bitMatrix);
            BufferedImage canvas = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = canvas.getGraphics();

            Font font = new Font(graphics.getFont().toString(), Font.BOLD, fontSize);
            graphics.setFont(font);

            graphics.setColor(new Color(255, 255, 255, 255));
            graphics.fillRect(0, 0, canvas_width, canvas_height);
            graphics.drawImage(img_barcode, 0, 0, barcode_width, barcode_height, null);
            graphics.setColor(new Color(0, 0, 0));
            int strWidth = graphics.getFontMetrics().stringWidth(content);
            graphics.drawString(content, (canvas_width - strWidth) / 2, canvas_height - 10);
            graphics.dispose();

            return canvas;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
