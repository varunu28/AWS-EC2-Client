public class Main {

    public static void main(String... args) {
        AwsEC2Client awsEC2Client = AwsEC2Client.getEC2Client();

        // List all running instances
        awsEC2Client.findRunningInstances();
    }
}
