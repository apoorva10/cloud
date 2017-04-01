import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
//import com.jcraft.jsch.*;
public class Ec2Commands extends Thread {
	static AmazonEC2 amazonEc2Client;
	static AmazonS3 s3client;
	static String keyName="cloudpi";
	static String sgName="default";
@SuppressWarnings("deprecation")
public static void main(String[]args)
{
	amazonEc2Client= AmazonEC2ClientBuilder.standard().withRegion("us-west-2").build();
	s3client =new AmazonS3Client();
        //System.out.println(val);
	readValues();
}
static void readValues()
{
	List<S3ObjectSummary> s3objects = s3client.listObjects("3480-project").getObjectSummaries();
	for(int i=0;i<s3objects.size();i++)
	{
		//System.out.println(s3objects.get(i).getKey());
		String val=s3objects.get(i).getKey();
		System.out.println(val);
		read(val);
	}
}

static void read(String obj)
{
	//System.out.println("Downloading an object");
	//System.out.println(obj);
	
    S3Object object = s3client.getObject(new GetObjectRequest("3480-project",obj));
    System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
    displayTextInputStream(object.getObjectContent());
}
private static void displayTextInputStream(InputStream input) {
	// TODO Auto-generated method stub
	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    while (true) {
        String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (line == null) break;

        System.out.println("    " + line);
    }
    System.out.println();
}
static void listBuckets()
{
List<Bucket> buckets=s3client.listBuckets();
	for(Bucket b:buckets)
	{
	System.out.println(b.getName());
	}
}
static void putObject()
{
String bucket="bucket_name";
String key="test-object";
s3client.putObject(bucket, key, "content");

}
static void createInstance()
{
	//String userdata="#!/bin/bash\n mkdir newdir";
	RunInstancesRequest run=new RunInstancesRequest();
	//String formatted=Base64.encodeBase64String(userdata.getBytes());
	run.withImageId("ami-f6199796").withInstanceType("t2.micro").withMinCount(1).withMaxCount(1).withKeyName(keyName).withSecurityGroups(sgName);
	RunInstancesResult result=amazonEc2Client.runInstances(run);
	System.out.println("Instance description"+ result.toString());
}

static void stopInstance()
{
	StopInstancesRequest stop=new StopInstancesRequest();
	stop.withInstanceIds("");
	amazonEc2Client.stopInstances(stop);
	StopInstancesResult res=amazonEc2Client.stopInstances(stop);
	System.out.println(res.toString());
	
	
}
// follow the same for start and terminate the instance
static void terminateInstance()
{
	TerminateInstancesRequest stop=new TerminateInstancesRequest();
	stop.withInstanceIds("i-0f445b210872ecc71");
	TerminateInstancesResult res=amazonEc2Client.terminateInstances(stop);
	System.out.println(res.toString());
		
}
}
