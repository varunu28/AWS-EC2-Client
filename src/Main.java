public class Main {

    public static void main(String... args) {
        AwsEC2Client awsEC2Client = AwsEC2Client.getEC2Client();

        // Launch an EC2 instance
        awsEC2Client.
                launchEC2Instance("ami-97785bed","MyWebDMZ", "udemy_key", 2);

        // List all running instances
        awsEC2Client.findRunningInstances();
    }
}
