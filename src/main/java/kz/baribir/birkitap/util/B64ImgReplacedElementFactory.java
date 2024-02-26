package kz.baribir.birkitap.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class B64ImgReplacedElementFactory implements ReplacedElementFactory {
    private Map<String, Object> baseURLMap = new HashMap<>();

    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {
        Element e = box.getElement();
        if (e == null) {
            return null;
        }
        String nodeName = e.getNodeName();
        if (nodeName.equals("img")) {
            String attribute = e.getAttribute("src");
            FSImage fsImage;
            try {
                fsImage = buildImage(attribute, uac);
            } catch (BadElementException e1) {
                fsImage = null;
            } catch (IOException e1) {
                fsImage = null;
            }
            if (fsImage != null) {
                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                }
                return new ITextImageElement(fsImage);
            }
        }
        return null;
    }

    protected FSImage buildImage(String srcAttr, UserAgentCallback uac) throws IOException, BadElementException {
        FSImage fsImage;
        if (srcAttr.startsWith("data-image:")) {
            String key = srcAttr.split("/")[0].split(":")[1].trim();

            int firstSlashIndex = srcAttr.indexOf("/");
            String filename = srcAttr.substring(firstSlashIndex + 1);
            InputStream file = new FileInputStream(baseURLMap.get(key).toString() + filename);
            fsImage = new ITextFSImage(Image.getInstance(file.readAllBytes()));
        } else if (srcAttr.startsWith("stream-image:")) {
            String key = srcAttr.split(":")[1].trim();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) (baseURLMap.get(key)), "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
            InputStream file = new ByteArrayInputStream(os.toByteArray());
            fsImage = new ITextFSImage(Image.getInstance(file.readAllBytes()));

        } else {
            fsImage = uac.getImageResource(srcAttr).getImage();
        }
        return fsImage;
    }

    public void remove(Element e) {
    }

    public void reset() {
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
    }

    public void addBaseURL(String key, String baseURL) {
        baseURLMap.put(key, baseURL);
    }

    public Map<String, Object> getBaseURLMap() {
        return baseURLMap;
    }
}
