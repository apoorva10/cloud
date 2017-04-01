import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
public class S3_create
{
static AmazonS3 s3client;
static String keyName="cloudpi";
public static void main(String[]args)
{
s3client =new AmazonS3Client();
createbucket();
}
static void createbucket()
{
	String bucketname="3480-project";
	Bucket buck=s3client.createBucket(bucketname);
	//System.out.println(buck.toString());
}
}
