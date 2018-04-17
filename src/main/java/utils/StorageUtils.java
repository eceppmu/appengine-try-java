package utils;

import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
//import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class StorageUtils {
	// private static Storage storage = null;
	//
	// static {
	// storage = StorageOptions.getDefaultInstance().getService();
	// }
	private static final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
			.initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());

	/**
	 * Used below to determine the size of chucks to read in. Should be > 1kb
	 * and < 10MB
	 */
	private static final int BUFFER_SIZE = 2 * 1024 * 1024;

	public static Reader readFile(String bucketName, String objectName) {
		GcsFilename fileName = new GcsFilename(bucketName, objectName);
//		if (SERVE_USING_BLOBSTORE_API) {
//			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
//			BlobKey blobKey = blobstoreService
//					.createGsBlobKey("/gs/" + fileName.getBucketName() + "/" + fileName.getObjectName());
//			blobstoreService.serve(blobKey, resp);
//		} else {
			return Channels.newReader(gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE), "UTF-8") ;
			//copy(Channels.newInputStream(readChannel), resp.getOutputStream());
//		}
	}

}
