package com.gdg.campus.korea.team1.util.google;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

public class GcpStorageUtil {
  private String projectId = null;
  private Storage storage = null;

  private static final int BUFF_SIZE = 4096;

  public GcpStorageUtil(String projectId, String credentialsPath) throws Exception {
    try (InputStream jsonIn = new FileInputStream(credentialsPath)) {
      GoogleCredentials credentials = GoogleCredentials.fromStream(jsonIn)
          .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
      this.projectId = projectId;
      this.storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build()
          .getService();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public GcpStorageUtil(ResourceLoader resourceLoader, String projectId, String credentialsPath) throws Exception {
    try (InputStream fis = resourceLoader.getResource(credentialsPath).getInputStream()) {
      GoogleCredentials credentials = GoogleCredentials.fromStream(fis)
          .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
      this.projectId = projectId;
      this.storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build()
          .getService();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Uploads a file to Google Cloud Storage to the bucket specified in the BUCKET_NAME
   * @param bucketName bucket name on Google Cloud Storage
   * @param fileName upload file name
   * @return file link
   */
  public String uploadFile(InputStream is, final String bucketName, final String fileName) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] readBuf = new byte[BUFF_SIZE];
    while (is.available() > 0) {
      int bytesRead = is.read(readBuf);
      os.write(readBuf, 0, bytesRead);
    }
    BlobInfo blobInfo = storage
        .create(BlobInfo.newBuilder(bucketName, fileName).setContentType("image/jpeg").build(),
            os.toByteArray());
    return blobInfo.getMediaLink();
  }

  /**
   * Write file stream
   *
   * @param bucketName
   * @param fileName
   */
  public byte[] download(final String bucketName, final String fileName) throws IOException {
    Blob blob = storage.get(BlobId.of(bucketName, fileName));
    if (blob == null || !blob.exists()) {
      return null;
    }

    try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
      blob.downloadTo(bout, Blob.BlobSourceOption.userProject(this.projectId));
      return bout.toByteArray();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * write file stream
   * @param bucketName
   * @param fileName
   * @param response
   * @throws IOException
   */
  public void downloadFileAndSend(final String bucketName, final String fileName, HttpServletResponse response, String contentType) throws IOException {
    Blob blob = storage.get(BlobId.of(bucketName, fileName));

    if(blob == null || !blob.exists()) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    if(StringUtils.hasText(contentType))
      response.setContentType(contentType);
    else
      response.setContentType("application/octet-stream");

    response.setContentLength((int)(long)blob.getSize());
    String nfileName = fileName.substring(fileName.lastIndexOf("/")+1);
    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", nfileName));

    blob.downloadTo(response.getOutputStream(), Blob.BlobSourceOption.userProject(this.projectId));
  }
}
