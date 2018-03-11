# AWS-EC2-Client
An AWS-EC2 client for performing various operations on EC2 using AWS SDK

## How to use this?
You need to add the [AWS-SDK](https://aws.amazon.com/sdk-for-java/) ```core``` and ```third-party``` libraries to the class path.

Update the path to your credentials file under ```CREDENTIALS_FILE```

## List of operations and corresponding methods:
 - Launch an EC2 Instance ```launchEC2Instance```
 - List EC2 Instances with a given status ```listInstances```
 - Start an EC2 Instance ```startEC2Instance```
 - Stop an EC2 Instance ```stopEC2Instance```
 - Reboot an EC2 Instance ```rebootEC2Instance```
 - Get the description of an EC2 Instance ```describeInstance```
 
 **NOTE:** A ```singleton``` design pattern is implemented to avoid opening multiple EC2Client connections

## Contributing
Raise a PR or open an issue for a new operation or any issues in the code
