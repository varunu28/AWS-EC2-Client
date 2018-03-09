import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

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

    public void findRunningInstances() {
        List<Reservation> reservations = client.describeInstances().getReservations();

        for (Reservation reservation : reservations) {
            List<Instance> instances = reservation.getInstances();
            System.out.println("Here is a list of running EC2 instances: ");
            for (Instance instance : instances) {
                if("running".equals(instance.getState().getName())) {
                    System.out.printf("Instance Id: %s || Instance Public IP: %s || Instance Tags: %s%n",
                            instance.getInstanceId(), instance.getPublicIpAddress(), instance.getTags());
                }
            }
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