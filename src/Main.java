/**
 *
 * @author Varun Upadhyay (https://github.com/varunu28)
 *
 */

public class Main {

    public static void main(String... args) {

        // Get the EC2Client
        AwsEC2Client awsEC2Client = AwsEC2Client.getEC2Client();

        // Launch an EC2 instance
        awsEC2Client.
                launchEC2Instance("ami-97785bed","MyWebDMZ", "udemy_key", 1);

        // List all running instances
        awsEC2Client.listInstances("running");

        // Start a stopped instance
        awsEC2Client.startEC2Instance("i-0544139c1ce31507c");

        // Start a stopped instance
        awsEC2Client.stopEC2Instance("i-0544139c1ce31507c");

        // Reboot an EC2 instance
        awsEC2Client.rebootEC2Instance("i-0544139c1ce31507c");

        // Get complete desciption about an instance
        awsEC2Client.describeInstance("i-0544139c1ce31507c");

        // Enables detailed monitoring on an instance
        awsEC2Client.startMonitoringAnInstance("i-0507954d2c6376136");

        // Disables detailed monitoring on an instance
        awsEC2Client.stopMonitoringAnInstance("i-0507954d2c6376136");
    }
}
