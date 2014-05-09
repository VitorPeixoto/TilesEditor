package editordetiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peixoto
 */
public class DataReader {    
    private static volatile DataReader singleData = null;
    private EditorData dataValues;
    private File dataFile; 
    private ObjectInputStream objectIS;
    private ObjectOutputStream objectOS;
    
    private DataReader() throws IOException, ClassNotFoundException {                        
        try {
            //dataFile = new File(Constants.resourcePath+("data.ted"));
            dataValues = new EditorData("/", "/", "/", new String[] {"", "", "", "", "", "", "", "", "", ""});
            dataFile = new File("/home/Peixoto/data.ted");
            if(!dataFile.exists()) dataFile.createNewFile();
            write();
            //read();
        } catch (NotInitiatedYetException ex) {
            Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public static DataReader newDataReader() throws IOException, ClassNotFoundException {
        if(singleData == null) singleData = new DataReader();
        return singleData;
    }        
    
    public void write() throws NotInitiatedYetException, IOException {
        FileOutputStream oStream = new FileOutputStream(dataFile);
        objectOS = new ObjectOutputStream(oStream);
        objectOS.writeObject(dataValues);
        objectOS.flush();
        objectOS.close();
    }
    
    public void write(String pngsDir, String tilesDir, String commonSaveDir, String lastFiles[]) throws NotInitiatedYetException, IOException {
        dataValues = new EditorData(pngsDir, tilesDir, commonSaveDir, lastFiles);
        write();
    }
    
    public void read() throws ClassNotFoundException, IOException {        
        FileInputStream iStream = new FileInputStream(dataFile);        
        objectIS = new ObjectInputStream(iStream);
        dataValues = new EditorData(EditorData.class.cast(objectIS.readObject()));
        objectIS.close();
    }

    public String getPngsDir() {
        return dataValues.getPngsDir();
    }

    public void setPngsDir(String pngsDir) {
        dataValues.setPngsDir(pngsDir);
    }

    public String getTilesDir() {
        return dataValues.getTilesDir();
    }

    public void setTilesDir(String tilesDir) {
        dataValues.setTilesDir(tilesDir);
    }

    public String getCommonSaveDir() {
        return dataValues.getCommonSaveDir();
    }

    public void setCommonSaveDir(String commonSaveDir) {
        dataValues.setCommonSaveDir(commonSaveDir);
    }

    public String[] getLastFiles() {
        return dataValues.getLastFiles();
    }

    public void setLastFiles(String[] lastFiles) {
        dataValues.setLastFiles(lastFiles);
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }
    
    private class EditorData implements Serializable {
        private String pngsDir,
                       tilesDir,
                       commonSaveDir;

        private String lastFiles[];

        public EditorData(String pngsDir, String tilesDir, String commonSaveDir, String lastFiles[]) throws IOException {
            this.pngsDir = pngsDir;
            this.tilesDir = tilesDir;
            this.commonSaveDir = commonSaveDir;
            this.lastFiles = lastFiles;                       
        }       
        
        public EditorData(EditorData data) {
            this.setCommonSaveDir(data.getCommonSaveDir());
            this.setLastFiles(data.getLastFiles());
            this.setPngsDir(data.getPngsDir());
            this.setTilesDir(data.getTilesDir());
        }

        public String getPngsDir() {
            return pngsDir;
        }

        public void setPngsDir(String pngsDir) {
            this.pngsDir = (pngsDir == null ? "/" : pngsDir);
        }

        public String getTilesDir() {
            return tilesDir;
        }

        public void setTilesDir(String tilesDir) {
            this.tilesDir = (tilesDir == null ? "/" : tilesDir);
        }

        public String getCommonSaveDir() {
            return commonSaveDir;
        }

        public void setCommonSaveDir(String commonSaveDir) {
            this.commonSaveDir = (commonSaveDir == null ? "/" : commonSaveDir);
        }

        public String[] getLastFiles() {
            return lastFiles;
        }

        public void setLastFiles(String[] lastFiles) {
            this.lastFiles = (lastFiles == null ? new String[] {"", "", "", "", "", "", "", "", "", ""} : lastFiles);
        }
    }
}    
