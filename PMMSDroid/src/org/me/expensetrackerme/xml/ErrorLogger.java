/*
 * Created on Dec 29, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.me.expensetrackerme.xml;

import android.os.Environment;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author C809532
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ErrorLogger {


	public static void logError(String error) {
            //Toast.makeText(this, "loading", Toast.LENGTH_LONG).show();
		//File metadata = new File("metadata.xml");
            File log = new File(Environment.getExternalStorageDirectory(),
                    "errorlogs.txt");
            if (!log.exists()) {
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(log);
                    fos.write("".getBytes());
                    fos.flush();
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(ErrorLogger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
                bw.write(error);
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(ErrorLogger.class.getName()).log(Level.SEVERE, null, ex);
            }

	}

}
