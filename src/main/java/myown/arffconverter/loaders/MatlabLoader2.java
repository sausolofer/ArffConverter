/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myown.arffconverter.loaders;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import static weka.core.converters.Loader.BATCH;
import static weka.core.converters.Loader.INCREMENTAL;
import weka.core.converters.MatlabLoader;

/**
 *
 * @author SSF
 */
public class MatlabLoader2 extends MatlabLoader {

    public MatlabLoader2() {
    }

    @Override
    public Instances getDataSet() throws IOException {
        Instances result;
        Vector<Double> row;
        double[] data;
        int i;
        int n;

        if (m_sourceReader == null) {
            throw new IOException("No source has been specified");
        }

        if (getRetrieval() == INCREMENTAL) {
            throw new IOException("Cannot mix getting Instances in both incremental and batch modes");
        }
        MatFileReader mr = new MatFileReader(m_sourceFile);
        Map<String, MLArray> content = mr.getContent();

        setRetrieval(BATCH);
        if (m_structure == null) {
            getStructure();
        }

        result = new Instances(m_structure, 0);

        // create instances from buffered data
        for (i = 0; i < m_Buffer.size(); i++) {
            row = m_Buffer.get(i);
            if (row.size() == 0) {
                continue;
            }
            data = new double[row.size()];
            for (n = 0; n < row.size(); n++) {
                data[n] = row.get(n);
            }

            result.add(new DenseInstance(1.0, data));
        }

        // close the stream
        try {
            m_sourceReader.close();
        } catch (Exception ex) {
            // ignored
        }

        return result;
    }

    /**
     * Determines and returns (if possible) the structure (internally the
     * header) of the data set as an empty set of instances.
     *
     * @return the structure of the data set as an empty set of Instances
     * @throws IOException if an error occurs
     */
    @Override
    public Instances getStructure() throws IOException {
        int numAtt;
        ArrayList<Attribute> atts;
        int i;
        String relName;
        Vector<Double> row;
        int c;
        char chr;
        StringBuffer str;
        boolean isComment;

        if (m_sourceReader == null) {
            throw new IOException("No source has been specified");
        }

        if (m_structure == null) {
            numAtt = 0;
            m_Buffer = new Vector<Vector<Double>>();
            row = new Vector<Double>();
            str = new StringBuffer();
            isComment = false;
            m_Buffer.add(row);
            try {
                // determine number of attributes
                while ((c = m_sourceReader.read()) != -1) {
                    chr = (char) c;

                    // comment found?
                    if (chr == '%') {
                        isComment = true;
                    }

                    // end of line reached
                    if ((chr == '\n') || (chr == '\r')) {
                        isComment = false;
                        if (str.length() > 0) {
                            row.add(new Double(str.toString()));
                        }
                        if (numAtt == 0) {
                            numAtt = row.size();
                        }
                        if (row.size() > 0) {
                            row = new Vector<Double>();
                            m_Buffer.add(row);
                        }
                        str = new StringBuffer();
                        continue;
                    }

                    // skip till end of comment line
                    if (isComment) {
                        continue;
                    }

                    // separator found?
                    if ((chr == '\t') || (chr == ' ')) {
                        if (str.length() > 0) {
                            row.add(new Double(str.toString()));
                            str = new StringBuffer();
                        }
                    } else {
                        str.append(chr);
                    }
                }

                // last number?
                if (str.length() > 0) {
                    row.add(new Double(str.toString()));
                }

                // generate header
                atts = new ArrayList<Attribute>(numAtt);
                for (i = 0; i < numAtt; i++) {
                    atts.add(new Attribute("att_" + (i + 1)));
                }

                if (!m_URL.equals("http://")) {
                    relName = m_URL;
                } else {
                    relName = m_File;
                }

                m_structure = new Instances(relName, atts, 0);
                m_structure.setClassIndex(m_structure.numAttributes() - 1);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new IOException("Unable to determine structure as Matlab ASCII file: " + ex);
            }
        }

        return new Instances(m_structure, 0);
    }

}
