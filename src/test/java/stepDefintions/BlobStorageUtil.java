package stepDefintions;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlobStorageUtil {

    private static final String CONNECTION_STRING = getString();
    private static final String ACCOUNTNAME = "seleniumartifact";

    private static String getString() {
        String secret = System.getenv("MY_SECRET_KEY");

        return "DefaultEndpointsProtocol=https;AccountName="+ACCOUNTNAME+";AccountKey=" + secret + ";EndpointSuffix=core.windows.net";
    }

    private static final String CONTAINER_NAME = "selenium"; // Replace with your actual container name

    private final BlobServiceClient blobServiceClient;


    public BlobStorageUtil() {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildClient();
    }

    private String getTimestamp() {
        // Format the current date and time as yyyyMMdd-HHmmss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        return sdf.format(new Date());
    }

    public void uploadFile(String filePath, String blobName) {
        File file = new File(filePath);
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME).getBlobClient(blobName);

        try (FileInputStream fis = new FileInputStream(file)) {
            blobClient.upload(fis, file.length(), true);
            System.out.println("File uploaded successfully: " + blobName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to upload file: " + blobName);
        }
    }

    public void uploadDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        // Generate blob name with timestamp
                        String timestamp = getTimestamp();
                        String blobName = file.getName() + "-" + timestamp; // Prefix the file name with the timestamp
                        uploadFile(file.getAbsolutePath(), blobName);
                    } else if (file.isDirectory()) {
                        // Recursively handle subdirectories
                        uploadDirectory(file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("Provided path is not a directory.");
        }
    }

}
