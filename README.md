# A-Deploy

This repository provides two [Jenkins pipeline custome steps](https://jenkins.io/doc/book/pipeline/shared-libraries/#defining-custom-steps)
for provisioning and deploying an AMI to a AWS auto scaling
group and another one to perform rollbacks. Rolling updates are performed by
the [a-roller](https://github.com/jaclar/a-roller) script.

## Dependencies

The steps assume that:

- [Packer](https://www.packer.io/) is installed at `/usr/bin/packer` on the agent.
- AWS credentials are provided by the pipeline as environment variables `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`
- node.js 8 is installed on the agent

## Usage

### Include library

``` groovy
@Library('a-deploy') _
```

### Use `awsDeploy` step

``` groovy
stage('Deploy') {
  steps {
    awsDeploy 'service-name', 'aws-region', 'auto-scaling-group-name'
  }
}
```

This will install the `a-roller` npm package, compress the node.js code of
the of the workspace as a `tar.gz` file and provision an AMI called
`service-name $build-number` with `packer` and `ansible`. Once the AMI is
ready, the `roller` script will be executed to update the auto scaling group
with the name `auto-scaling-group-name` in the region `aws-region` with the newly
generated AMI.

### Use `awsRollback` step

``` groovy
stage('Deploy') {
  steps {
    awsRollback 'service-name', 'aws-region', 'auto-scaling-group-name', 'build-number'
  }
}
```
This will install the `a-roller` npm package and execute the `roller` script
to update the auto scaling group with the AMI generated by the build number `build-number`.
