import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AwsEC2Client {

    private static AwsEC2Client awsEC2Client = null;
    private static AmazonEC2 client = null;
    private BasicAWSCredentials credentials = null;
    private static final String CREDENTIALS_FILE = "src/credentials.txt";

    private AwsEC2Client() {
        loadCredentials();
        client = AmazonEC2ClientBuilder.
                    standard().
                    withCredentials(new AWSStaticCredentialsProvider(credentials)).
                    withRegion(Regions.US_EAST_1).
                    build();
    }

    /**
     * This method lists the instance in a given state
     *
     * @param state The state of the instance
     * Prints the instance id, instance public IP & tags of the instance in a given state
     **/
    public void listInstances(String state) {
        List<Reservation> reservations = client.describeInstances().getReservations();

        System.out.println("Here is a list of EC2 instances in " + state + " state: ");
        for (Reservation reservation : reservations) {
            List<Instance> instances = reservation.getInstances();
            for (Instance instance : instances) {
                if(state.equalsIgnoreCase(instance.getState().getName())) {
                    System.out.printf("Instance Id: %s || Instance Public IP: %s || Instance Tags: %s%n",
                            instance.getInstanceId(), instance.getPublicIpAddress(), instance.getTags());
                }
            }
        }
    }

    /**
     * This method launches an EC2 instance
     *
     * @param imageId The ID of the AMI
     * @param securityGroup Security Group of AMI
     * @param key_name Name of EC2 key pair
     * @param numOfInstances Maximum number of instances to be launched
     * Launches an EC2 instance with given specification
     **/
    public void launchEC2Instance(String imageId, String securityGroup, String key_name, int numOfInstances) {
        try {
            RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

            runInstancesRequest.
                    withImageId(imageId).
                    withInstanceType(InstanceType.T2Micro).
                    withMinCount(1).
                    withMaxCount(numOfInstances).
                    withKeyName(key_name).
                    withSecurityGroups(securityGroup);

            RunInstancesResult runInstancesResult = client.runInstances(runInstancesRequest);

            System.out.println(numOfInstances + " EC2 instance(s) created successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static AwsEC2Client getEC2Client() {
        if (awsEC2Client == null) {
            awsEC2Client = new AwsEC2Client();
        }

        return awsEC2Client;
    }

    private void loadCredentials() {
        try {
            File file = new File(CREDENTIALS_FILE);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            List<String> items = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                items.add(line);
            }

            String accessKey = items.get(0);
            String secretKey = items.get(1);

            credentials = new BasicAWSCredentials(accessKey, secretKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}