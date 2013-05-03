
package pdfcreator;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import management.*;
import java.util.LinkedList;
import java.util.List;

public class SimplePdfCreator
{
    /**
     * Constructor.
     */
    public SimplePdfCreator()
    {
        super();
    }

    /**
     * create the second sample document from the PDF file format specification.
     *
     * @param file The file to write the PDF to.
     * @param message The message to write in the file.
     *
     * @throws IOException If there is an error writing the data.
     * @throws COSVisitorException If there is an error writing the PDF.
     */
    public void createModulePdf( String file, Module module) throws IOException, COSVisitorException
    {
        // the document
        PDDocument doc = null;
        try
        {
            doc = new PDDocument();

            PDPage page = new PDPage();
            doc.addPage( page );
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            
            contentStream.beginText();
            contentStream.setFont( font, 16 );
            contentStream.moveTextPositionByAmount( 100, 700 );
            contentStream.drawString( module.getName() );
            contentStream.endText();
            
            List<Entry> entrylist = module.getEntryList();
            int x = 120;
            int y = 600;
            for(int i = 0; i < entrylist.size(); i ++){
                contentStream.beginText();
                contentStream.setFont( font, 12 );
                contentStream.moveTextPositionByAmount( x, y );
                contentStream.drawString( entrylist.get(i).toString() );
                contentStream.endText();
                y -= 50;
            }
            
            contentStream.close();
            doc.save( file );
        }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }
    }

}

