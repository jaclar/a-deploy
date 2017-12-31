def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    // now build, based on the configuration provided
    node {
        sh "npm install a-roller"
        sh "/usr/bin/packer build -var 'name=${config.name}' -var 'region=${config.region}' -var 'build_number=${env.BUILD_NUMBER}' node_modules/a-rollerpacker/ami.json"
        sh "node-modules/.bin/roller --region ${config.region} --group ${config.group} --ami '${config.name} ${env.Build_number}'"
    }
}
